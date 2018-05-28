package com.yxld.xzs.activity.patrol;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.DayEntity;
import com.yxld.xzs.entity.OrderBean;
import com.yxld.xzs.entity.XunGengXiangQing;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.OkHttpUtils;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.AlignLeftRightTextView;
import com.yxld.xzs.view.CalendarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static com.yxld.xzs.http.api.API.IP_XUNGENG;

/**
 * A simple {@link Fragment} subclass.
 * 计划任务
 */
public class PlanTaskFragment extends BasePatrolFragment {

    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    Unbinder unbinder;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    private PatrolInformationAdapter mAdapter;

    private Map<String, List<XunGengXiangQing>> mDataCache;
    private Call mCall;
    private String mCurrentDay;
    private boolean mHasLoadMonthDataSucceed;

    public PlanTaskFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataCache = new HashMap<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDataCache != null) {
            mDataCache.clear();
            mDataCache = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_task, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UIUtils.configSwipeRefreshLayoutColors(swipeLayout);
        scrollView.smoothScrollTo(0, 0);
        recyclerView.setNestedScrollingEnabled(false);
        swipeLayout.setOnRefreshListener(this);
        mCurrentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "";
        mAdapter = new PatrolInformationAdapter(new ArrayList<XunGengXiangQing>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_empty_data, (ViewGroup) recyclerView.getParent());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                XunGengXiangQing qing = mAdapter.getData().get(i);
                if (qing.jiluWancheng == -2) {
                    //巡检任务已取消
                    showDialog();
                } else if (qing.jiluWancheng == 2) {              //-1未开始, 1开始,2完成
                    Intent intent = new Intent(getActivity(), RemoteHistoryDetailActivity.class);
                    intent.putExtra(RemoteHistoryDetailActivity.KEY_JILU_ID, qing.jiluId);
                    intent.putExtra(RemoteHistoryDetailActivity.KEY_XIANLU_ID, qing.jiluXianluId);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), ConcreteCircuitActivity.class);
                    intent.putExtra(ConcreteCircuitActivity.KEY_JILU_ID, qing.jiluId);
                    intent.putExtra(ConcreteCircuitActivity.KEY_XIANLU_NAME, qing.jiluXianluName);
                    intent.putExtra(ConcreteCircuitActivity.KEY_XIANLU_ID, qing.jiluXianluId);
                    startActivity(intent);
                }
            }
        });

        calendarView.setOnDayClickListener(new CalendarView.OnDayClickListener() {
            @Override
            public void onDayClick(View view, DayEntity entity) {
                mCurrentDay = entity.day;
                if (entity.hasException || entity.hasHistoryTask || entity.hasFutureTask) {
                    recyclerView.setVisibility(View.GONE);
                    swipeLayout.setRefreshing(true);
                    getDataFromServer(entity.day, false);
                } else {
                    if (mAdapter.getData().size() == 0) {
                        return;
                    }
                    mAdapter.setNewData(new ArrayList<XunGengXiangQing>());
                }
            }
        });
    }

    private void showDialog() {
        new AlertView.Builder().setTitle("该巡检任务已取消！")
                .setStyle(AlertView.Style.Alert)
                .setContext(getActivity()).setCancelText("确定")
                .build()
                .show();
    }

    @Override
    public void fetchData() {

        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(true);
            }
        });
        Request request = new Request.Builder().url(IP_XUNGENG + "wygl_xungeng_app/plan/month?uuid=" + Contains.uuid).build();
        mCall = OkHttpUtils.getOkHttpClient().newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                toastOnUIThread("获取数据失败，请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                });

                try {
                    String result = response.body().string();
                    if (TextUtils.isEmpty(result)) {
                        toastOnUIThread("获取数据为空");
                        return;
                    }
                    if (!result.startsWith("{") || !result.endsWith("}")) {
                        toastOnUIThread("获取的数据格式有误");
                        return;
                    }
                    JSONObject object = new JSONObject(result);
                    final int status = object.optInt("status");
                    if (status != STATUS_CODE_OK) {
                        final String error = object.getString("error");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onError(status, error);
                            }
                        });

                        return;
                    }


                    final JSONObject dayDatas = object.getJSONObject("data");
                    if (dayDatas.length() == 0) {
                        toastOnUIThread("本月无任务");
                        return;
                    }
                    Observable.create(new ObservableOnSubscribe<List<DayEntity>>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<List<DayEntity>> e) throws Exception {
                            Iterator<String> iterator = dayDatas.keys();
                            List<DayEntity> entities = new ArrayList<>();
                            while (iterator.hasNext()) {
                                DayEntity day = new DayEntity();
                                String key = iterator.next();
                                day.setDay(key);
                                day.setPlan(dayDatas.getInt(key));
                                entities.add(day);
                            }
                            e.onNext(entities);
                            e.onComplete();
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<List<DayEntity>>() {
                                @Override
                                public void accept(@NonNull List<DayEntity> dayEntities) throws Exception {
                                    mHasLoadMonthDataSucceed = true;
                                    calendarView.setCalendarData(dayEntities);
                                    if (hasToday(dayEntities, mCurrentDay)) {
                                        swipeLayout.setRefreshing(true);
                                        getDataFromServer(mCurrentDay, true);
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(@NonNull Throwable throwable) throws Exception {
                                    Toast.makeText(getContext(), "网络请求出错", Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void toastOnUIThread(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean hasToday(List<DayEntity> entities, String today) {

        for (DayEntity entity : entities) {
            if (today.equals(entity.day)) {
                return true;
            }
        }
        return false;
    }

    private void getDataFromServer(final String day, boolean isRefresh) {
        if (mDataCache.containsKey(day) && !isRefresh) {
            mAdapter.setNewData(mDataCache.get(day));
            recyclerView.setVisibility(View.VISIBLE);
            swipeLayout.setRefreshing(false);
            return;
        }
        if (mDataCache.size() >= 8) {
            mDataCache.clear();
        }
        Calendar cld = Calendar.getInstance();
        StringBuilder sb = new StringBuilder().append(cld.get(Calendar.YEAR)).append("-")
                .append(cld.get(Calendar.MONTH) + 1)
                .append("-").append(day);

        Map<String, String> params = new HashMap<>();
        params.put("datetime", sb.toString());
        params.put("uuid", Contains.uuid);

        Disposable disposable = HttpAPIWrapper.getInstance().getOneXunJianPlans(params)
                .subscribe(new Consumer<XunGengXiangQing>() {
                    @Override
                    public void accept(@NonNull XunGengXiangQing xunGengXiangQing) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (xunGengXiangQing.status == STATUS_CODE_OK) {
                            handlerXunJianList(day, xunGengXiangQing.data);
                        } else {
                            Toast.makeText(getContext(), "获取列表失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        swipeLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "获取列表错误", Toast.LENGTH_SHORT).show();
                    }
                });
        disposables.add(disposable);
    }

    private void handlerXunJianList(String day, List<XunGengXiangQing> data) {
        if (data == null) {
            Toast.makeText(getContext(), "未获任何任务", Toast.LENGTH_SHORT).show();
            return;
        }
        mDataCache.put(day, data);
        mAdapter.setNewData(data);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        if (mHasLoadMonthDataSucceed) {
            DayEntity dayEntity = calendarView.getCurrentDayEntity(mCurrentDay);
            if (dayEntity == null || dayEntity.isPlaceHolder) {
                swipeLayout.setRefreshing(false);
                return;
            }
            if (dayEntity.hasException || dayEntity.hasHistoryTask || dayEntity.hasFutureTask) {
                getDataFromServer(mCurrentDay, true);
            } else {
                swipeLayout.setRefreshing(false);
            }
        } else {
            fetchData();
        }

    }


    private static class PatrolInformationAdapter extends BaseQuickAdapter<XunGengXiangQing, BaseViewHolder> {
        private SimpleDateFormat sdf;

        public PatrolInformationAdapter(@Nullable List<XunGengXiangQing> data) {
            super(R.layout.item_plan_task, data);
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, XunGengXiangQing s) {
            Drawable redDrawable = mContext.getResources().getDrawable(R.drawable.bg_dash_3lines_gray_exception_bg);
            Drawable whiteDrawable = mContext.getResources().getDrawable(R.drawable.bg_dash_3lines_gray);
            ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_xunjianshijian)).setRightText(sdf.format(new Date(s.jiluKaishiJihuaShijian))).setMyBackgroundDrawable(whiteDrawable, redDrawable, s.jiluWenti == -1);
            ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_xunjianluxian)).setRightText(s.jiluXianluName).setMyBackgroundDrawable(whiteDrawable, redDrawable, s.jiluWenti == -1);
            ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_kaishiquyu)).setRightText(s.kaishiaddress).setMyBackgroundDrawable(whiteDrawable, redDrawable, s.jiluWenti == -1);
            ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_jieshuquyu)).setRightText(s.endaddress).setMyBackgroundDrawable(whiteDrawable, redDrawable, s.jiluWenti == -1);
            ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_gongjixunjiandian)).setRightText(s.totaladdress + "").setMyBackgroundDrawable(whiteDrawable, redDrawable, s.jiluWenti == -1);
            ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_xunjianrenyuan)).setRightText(s.jiluXunjianXungengrenName).setMyBackgroundDrawable(whiteDrawable, redDrawable, s.jiluWenti == -1);
            ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_xunjianstatus)).setMyBackgroundDrawable(whiteDrawable, redDrawable, s.jiluWenti == -1);
            if (s.jiluWancheng == 2) {
                ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_xunjianstatus)).setRightText("巡检完成");
            } else if (s.jiluWancheng == 1) {
                ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_xunjianstatus)).setRightText("巡检开始");
            } else if (s.jiluWancheng == -2) {
                ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_xunjianstatus)).setRightText("巡检已取消");
            } else {
                ((AlignLeftRightTextView) baseViewHolder.getView(R.id.tv_xunjianstatus)).setRightText("巡检未开始");
            }
        }
    }
}
