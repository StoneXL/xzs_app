package com.yxld.xzs.activity.workbench;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.SuggestAdapter;
import com.yxld.xzs.base.BaseFragment;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.SuggestBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.view.ImageShowView;
import com.zaaach.toprightmenu.TopRightMenu;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author xlei
 * @Date 2018/1/24.
 */

public class SuggestFragment extends BaseFragment {
    private static final String LEI_XING = "lei_xing";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ly_empty_data)
    AutoLinearLayout mLyEmptyData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private List<SuggestBean.RowBean> mRowBeenList;
    private SuggestAdapter mAdapter;
    private TopRightMenu mTopRightMenu;
    private int mType;//类型0 其他 处理中 1已完成
    private String mXiangmuId = "";//项目id
    private int page = 1;
    private int row = 5;

    public static SuggestFragment newInstance(int param) {
        SuggestFragment fragment = new SuggestFragment();
        Bundle args = new Bundle();
        args.putInt(LEI_XING, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggest, null);
        unbinder = ButterKnife.bind(this, view);
        mRowBeenList = new ArrayList<>();
        mAdapter = new SuggestAdapter(mRowBeenList);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        mAdapter.setOnImageViewClick(new SuggestAdapter.OnImageViewClick() {
            @Override
            public void onImageViewClick(ImageView[] imageViews, String[] imageUrls, int position) {
                ImageShowView.startImageActivity(getActivity(), imageViews, imageUrls, position);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadDataFromServer(false, mXiangmuId);

            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mRefreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadDataFromServer(true, mXiangmuId);
//                        mAdapter.loadmore(initData());
//                        if (mAdapter.getItemCount() > 60) {
//                            Toast.makeText(getContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
//                            mRefreshLayout.finishLoadmoreWithNoMoreData();//将不会再次触发加载更多事件
//                        } else {
//                            mRefreshLayout.finishLoadmore();
//                        }
                    }
                }, 1000);
            }

        });
        loadDataFromServer(false, mXiangmuId);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt(LEI_XING, 0);
        }
    }

    public void loadDataFromServer(final boolean isLoadMore, String xmId) {
        if (isLoadMore) {
            page++;
        } else {
            page = 1;
        }
        //关闭下拉刷新
        mRefreshLayout.finishRefresh();
        //重新开启上拉加载更多
        mRefreshLayout.resetNoMoreData();
        mXiangmuId = xmId;
        KLog.i("type  " + mType + "xmId  " + mXiangmuId);
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        map.put("tousuStatus", mType + "");
        map.put("xmId", mXiangmuId);
        map.put("page", page + "");
        map.put("rows", row + "");
        Disposable disposable = HttpAPIWrapper.getInstance().getTousuList(map)
                .subscribe(new Consumer<SuggestBean>() {
                    @Override
                    public void accept(@NonNull SuggestBean dataBean) throws Exception {
                        if (dataBean.status == 1) {
                            KLog.i("mRowBeenList" + mRowBeenList.size());
                            KLog.i("dataBean.getRows().size()" + dataBean.getRows().size());
                            if (isLoadMore) {
                                KLog.i("加载更多" + "第" + page + "页");
                                mRowBeenList.addAll(dataBean.getRows());
                                mAdapter.setNewData(mRowBeenList);
                                mAdapter.notifyDataSetChanged();
                                if (dataBean.getRows().size() > 0) {
                                    mRefreshLayout.finishLoadmore();
                                } else {
                                    mRefreshLayout.finishLoadmoreWithNoMoreData();//将不会再次触发加载更多事件
                                }
                            } else {
                                if (dataBean.getRows().size() == 0) {
                                    mLyEmptyData.setVisibility(View.VISIBLE);
                                    mRecyclerView.setVisibility(View.GONE);
                                } else {
                                    mLyEmptyData.setVisibility(View.GONE);
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                }
                                mRowBeenList.clear();
                                mRowBeenList.addAll(dataBean.getRows());
                                mAdapter.setNewData(mRowBeenList);
                                mAdapter.notifyDataSetChanged();

                            }
                        } else {
                            onError(dataBean.status, dataBean.msg);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        KLog.i("onerror" + throwable.toString());

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
