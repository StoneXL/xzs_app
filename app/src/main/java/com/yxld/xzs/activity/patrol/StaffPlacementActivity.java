package com.yxld.xzs.activity.patrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.TeamManagerListEntity;
import com.yxld.xzs.entity.TeamMember;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.TimeUtil;
import com.yxld.xzs.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 人员管理
 */
public class StaffPlacementActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private StaffPlacementAdapter mAdapter;
    protected CompositeDisposable disposables = new CompositeDisposable();
    private TeamManagerListEntity mTeamManage;
    private TeamMember mTeamMember;
    private MyShiJianAdapter shiJianAdapter;
    private int checkdXungengRenPosition = -1;
    private boolean mHasChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_placement);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeLayout.setOnRefreshListener(this);

        mAdapter = new StaffPlacementAdapter(new ArrayList<TeamMember.DataBean>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                checkdXungengRenPosition = i;
                showChangePatrolStaffDialog(i);
            }
        });
        mAdapter.notifyDataSetChanged();
        getTeamMember();
        getTeam();
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
    }

    private static class StaffPlacementAdapter extends BaseQuickAdapter<TeamMember.DataBean, BaseViewHolder> {

        public StaffPlacementAdapter(@Nullable List<TeamMember.DataBean> data) {
            super(R.layout.item_patrol_record, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, TeamMember.DataBean dataBean) {
            baseViewHolder.setVisible(R.id.tv_star, false)
                    .setVisible(R.id.tv_desc, false).setText(R.id.tv_title, dataBean.getRenyuanName());
        }
    }

    /**
     * 获取改组的所有巡更人员
     */
    private void getTeamMember() {
        Map<String, String> map = new HashMap<>();
//        map.put("uuid", Contains.uuid);
        map.put("banzuid", getIntent().getStringExtra("banzuId"));
        map.put("uuid", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).getTeamMember(map)
                .subscribe(new Consumer<TeamMember>() {
                    @Override
                    public void accept(@NonNull TeamMember entity) throws Exception {
                        mTeamMember = entity;
                        swipeLayout.setRefreshing(false);
                        if (entity.status == STATUS_CODE_OK) {
                            mAdapter.setNewData(entity.getData());
                        } else {
                            onError(entity.status, entity.msg);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        swipeLayout.setRefreshing(false);
                    }
                });
        disposables.add(disposable);
    }

    /**
     * 获取所有的巡更任务
     */
    private void getTeam() {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance().getTeamManage(map)
                .subscribe(new Consumer<TeamManagerListEntity>() {
                    @Override
                    public void accept(@NonNull TeamManagerListEntity teamManage) throws Exception {
                        progressDialog.hide();
                        if (teamManage.status == STATUS_CODE_OK) {
                            mTeamManage = teamManage;
                            swipeLayout.setRefreshing(false);
                            mHasChanged = true;
                        } else {
                            if (teamManage.status == -1 && "未查询到匹配记录".equals(teamManage.error)) {
                                mTeamManage.setData(new ArrayList<TeamManagerListEntity.DataBean>());
                            } else {
                                onError(teamManage.status, teamManage.error);
                            }

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        progressDialog.hide();
                        swipeLayout.setRefreshing(false);
                    }
                });
        disposables.add(disposable);
    }

    /**
     * 更换某人当月的巡更任务的弹窗
     *
     * @param position
     */
    private void showChangePatrolStaffDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();

        View view = View.inflate(this, R.layout.layout_dialog_change_banzu, null);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(mTeamMember.getData().get(position).getRenyuanName());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        List<XunJianJiLuEntity> nameList = new ArrayList<>();
        for (TeamManagerListEntity.DataBean dataBean : mTeamManage.getData()) {
            for (XunJianJiLuEntity listBean : dataBean.jilulist) {
                nameList.add(listBean);
            }
        }
        shiJianAdapter = new MyShiJianAdapter(nameList);
        recyclerView.setAdapter(shiJianAdapter);
        shiJianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                XunJianJiLuEntity entity = shiJianAdapter.getData().get(i);
                if (entity.isSelected == 1) {
                    entity.isSelected = 0;
                } else {
                    entity.isSelected = 1;
                }
                shiJianAdapter.notifyItemChanged(i);
                KLog.i(i);
            }
        });


        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasAnyTeamMemberSelected(shiJianAdapter.getData())) {
                    ToastUtil.show(StaffPlacementActivity.this, "请选择需要更换的巡更人员");
                } else {
                    changePatrolMember();
                    alertDialog.dismiss();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shiJianAdapter.getData().size() != 0) {
                    for (XunJianJiLuEntity entity : shiJianAdapter.getData()) {
                        if (entity.isSelected == 1) {
                            entity.isSelected = 0;
                        }
                    }
                }
                alertDialog.dismiss();

            }
        });

        alertDialog.setView(view);
        alertDialog.show();
    }

    private boolean hasAnyTeamMemberSelected(List<XunJianJiLuEntity> jianJiLuEntities) {
        for (XunJianJiLuEntity entity : jianJiLuEntities) {
            if (entity.isSelected == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更换某人当月的巡更任务的网络请求
     */
    private void changePatrolMember() {
        String allSelectedJiluIds = handlerSelectedTeamMembers();
        if (TextUtils.isEmpty(allSelectedJiluIds)) {
            Toast.makeText(this, "没有获取到任何选择的记录id", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("paibanrenid", mTeamMember.getData().get(checkdXungengRenPosition).getRenyuanAdminId() + "");
        map.put("jiluid", allSelectedJiluIds);
        map.put("paibanrenname", mTeamMember.getData().get(checkdXungengRenPosition).getRenyuanName() + "");
        map.put("uuid", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance().updateTeamMemberMonth(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack entity) throws Exception {
                        progressDialog.hide();
                        swipeLayout.setRefreshing(false);
                        if (entity.status == STATUS_CODE_OK) {
                            ToastUtil.show(StaffPlacementActivity.this, entity.msg);
                            getTeam();
                        } else {
                            onError(entity.status, entity.error);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        swipeLayout.setRefreshing(false);
                    }
                });
        disposables.add(disposable);

    }

    private String handlerSelectedTeamMembers() {
        StringBuilder sBuilder = new StringBuilder();
        for (XunJianJiLuEntity entity : shiJianAdapter.getData()) {
            if (entity.isSelected == 1) {
                sBuilder.append(entity.jiluId).append(",");
            }
        }
        if (sBuilder.length() == 0) {
            return null;
        }
        return sBuilder.toString().substring(0, sBuilder.toString().length() - 1);
    }

    @Override
    public void finish() {
        if (mHasChanged) {
            setResult(0x2002);
        }
        super.finish();

    }


    private static class MyShiJianAdapter extends BaseQuickAdapter<XunJianJiLuEntity, BaseViewHolder> {

        private SimpleDateFormat mFormat;
//        public int checkedPosition = -1;

        public MyShiJianAdapter(@Nullable List<XunJianJiLuEntity> data) {
            super(R.layout.item_banzu_change_dialog, data);
            mFormat = new SimpleDateFormat("MM月dd日", Locale.CHINA);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, XunJianJiLuEntity dataBean) {
            baseViewHolder.setText(R.id.tv_xunxian, dataBean.jiluXianluName);
            baseViewHolder.setText(R.id.tv_xun_name, mFormat.format(new Date(dataBean.getJiluKaishiJihuaShijian())));
            baseViewHolder.setText(R.id.tv_xun_time, TimeUtil.HHmm(dataBean.getJiluKaishiJihuaShijian()) + "-" + TimeUtil.HHmm(dataBean.getJiluJieshuJihuaShijian()));
//            if (baseViewHolder.getLayoutPosition() == checkedPosition) {
//                baseViewHolder.setBackgroundColor(R.id.root_layout, mContext.getResources().getColor(R.color.gray));
//            } else {
//                baseViewHolder.setBackgroundColor(R.id.root_layout, mContext.getResources().getColor(R.color.white));
//            }
            if (dataBean.isSelected == 1) {
                baseViewHolder.setBackgroundColor(R.id.root_layout, mContext.getResources().getColor(R.color.gray));
            } else {
                baseViewHolder.setBackgroundColor(R.id.root_layout, mContext.getResources().getColor(R.color.white));
            }
        }
    }

}
