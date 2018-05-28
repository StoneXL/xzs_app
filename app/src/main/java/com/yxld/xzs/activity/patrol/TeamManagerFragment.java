package com.yxld.xzs.activity.patrol;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.TeamManagerListEntity;
import com.yxld.xzs.entity.TeamMember;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.TimeUtil;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamManagerFragment extends BasePatrolFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    Unbinder unbinder;
    @BindView(R.id.tv_staff_manager)
    TextView tvStaffManager;
    private TeamManagerAdapter teamManagerAdapter;
    private MyShiJianAdapter shiJianAdapter;
    private int partrolDatePosition = -1;

    public TeamManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_manager, container, false);
        unbinder = ButterKnife.bind(this, view);
        teamManagerAdapter = new TeamManagerAdapter(new ArrayList<TeamManagerListEntity.DataBean>());
        recyclerView.setAdapter(teamManagerAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UIUtils.configSwipeRefreshLayoutColors(swipeLayout);
        swipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void fetchData() {
        swipeLayout.setRefreshing(true);
        getTeam();
    }

    /**
     * 获取所有的巡更任务
     */
    private void getTeam() {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).getTeamManage(map)
                .subscribe(new Consumer<TeamManagerListEntity>() {
                    @Override
                    public void accept(@NonNull TeamManagerListEntity teamManage) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if(teamManage.status==STATUS_CODE_OK){
                            teamManagerAdapter.setNewData(teamManage.data);
                        }else {
                            teamManagerAdapter.setNewData(new ArrayList<TeamManagerListEntity.DataBean>());
                            onError(teamManage.status,teamManage.error);
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
     * 获取改组的所有的成员
     * @param position
     */
    private void getTeamMember(int position) {
        Map<String, String> map = new HashMap<>();
//        map.put("uuid", Contains.uuid);
        map.put("banzuid", teamManagerAdapter.getData().get(0).jilulist.get(0).jiluBanzuId + "");
        map.put("uuid", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).getTeamMember(map)
                .subscribe(new Consumer<TeamMember>() {
                    @Override
                    public void accept(@NonNull TeamMember entity) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if(entity.status==STATUS_CODE_OK){
                            shiJianAdapter.setNewData(entity.getData());
                        }else {
                            onError(entity.status,entity.error);
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

    @Override
    public void onRefresh() {
        getTeam();
//        swipeLayout.setRefreshing(false);
    }

    @OnClick(R.id.tv_staff_manager)
    public void onViewClicked() {
        if(teamManagerAdapter.getData().size() ==0){
            Toast.makeText(getContext(),"抱歉,您当前没有可管理的项目",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getActivity(),StaffPlacementActivity.class);
        intent.putExtra("banzuId", teamManagerAdapter.getData().get(0).jilulist.get(0).jiluBanzuId + "");
        startActivityForResult(intent,0x2002);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x2002){
            getTeam();
        }
    }


    private class TeamManagerAdapter extends BaseQuickAdapter<TeamManagerListEntity.DataBean, BaseViewHolder> {

    /**
     * 巡更任务的适配器
     */

        public TeamManagerAdapter(@Nullable List<TeamManagerListEntity.DataBean> data) {
            super(R.layout.item_team_manager, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, TeamManagerListEntity.DataBean dataBean) {
            baseViewHolder.setText(R.id.tv_date, TimeUtil.MMdd(Long.parseLong(dataBean.date)));
            RecyclerView rv = baseViewHolder.getView(R.id.recyclerView);
            rv.setNestedScrollingEnabled(false);
            List<XunJianJiLuEntity> datas = dataBean.jilulist;
            final TeamManagerChildAdapter teamManagerChildAdapter = new TeamManagerChildAdapter(datas);
            teamManagerChildAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    switch (view.getId()) {
                        case R.id.name:
                            partrolDatePosition = i;
                            showChangePatrolStaffDialog(i, teamManagerChildAdapter.getData().get(i));
                            KLog.i(i);
                            break;
                        case R.id.tv_circuit_detail:
                            XunJianJiLuEntity bean = teamManagerChildAdapter.getData().get(i);
                            Intent intent = new Intent(getActivity(), ConcreteCircuitActivity.class);
                            intent.putExtra(ConcreteCircuitActivity.KEY_XIANLU_ID,bean.jiluXianluId);
                            intent.putExtra(ConcreteCircuitActivity.KEY_XIANLU_NAME,bean.jiluXianluName);
                            intent.putExtra(ConcreteCircuitActivity.KEY_JILU_ID,bean.jiluId);
                            startActivity(intent);
                            KLog.i("路线详情");
                            break;
                    }
                }
            });
            rv.setAdapter(teamManagerChildAdapter);
        }
    }


    private static class TeamManagerChildAdapter extends BaseQuickAdapter<XunJianJiLuEntity, BaseViewHolder> {

    /**
     * 每天巡更任务的recycleview的适配器
     */


        public TeamManagerChildAdapter(@Nullable List<XunJianJiLuEntity> data) {
            super(R.layout.item_child_team_manager, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, XunJianJiLuEntity listBean) {
            baseViewHolder.addOnClickListener(R.id.name);
            baseViewHolder.addOnClickListener(R.id.tv_circuit_detail);
            baseViewHolder.setText(R.id.tv_time, TimeUtil.HHmm(listBean.jiluKaishiJihuaShijian) + "-" + TimeUtil.HHmm(listBean.jiluJieshuJihuaShijian));
            baseViewHolder.setText(R.id.tv_xunxian, listBean.jiluXianluName).setText(R.id.tv_xunjian_jihua,listBean.jiluJihuaName);
            baseViewHolder.setText(R.id.name, listBean.jiluXunjianXungengrenName);
            if(baseViewHolder.getLayoutPosition() == getData().size()-1){
                baseViewHolder.setVisible(R.id.line,false);
            }
        }
    }

    /**
     * 弹窗选择更换巡更人的适配器
     */
    private static class MyShiJianAdapter extends BaseQuickAdapter<TeamMember.DataBean,BaseViewHolder>{

        public int checkedPosition = -1;

        public MyShiJianAdapter(@Nullable List<TeamMember.DataBean> data) {
            super(R.layout.item_shijian_dialog,data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, TeamMember.DataBean dataBean) {
            baseViewHolder.setText(R.id.tv_desc,dataBean.getRenyuanName());
            if (baseViewHolder.getLayoutPosition() == checkedPosition) {
                baseViewHolder.setBackgroundColor(R.id.root_layout, mContext.getResources().getColor(R.color.gray));
            } else {
                baseViewHolder.setBackgroundColor(R.id.root_layout, mContext.getResources().getColor(R.color.white));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void showChangePatrolStaffDialog(final int position, final XunJianJiLuEntity listBean) {

    /**
     * 显示弹窗
     * @param position
     * @param listBean
     */

        getTeamMember(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog alertDialog = builder.create();

        View view = View.inflate(getActivity(), R.layout.layout_dialog_change_staff, null);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_xunxian = (TextView) view.findViewById(R.id.tv_xunxian);
        TextView tv_xun_name = (TextView) view.findViewById(R.id.tv_xun_name);
        TextView tv_xun_time = (TextView) view.findViewById(R.id.tv_xun_time);
        tv_xunxian.setText(listBean.jiluXianluName);
        tv_xun_name.setText(listBean.jiluXunjianXungengrenName);
        tv_xun_time.setText(TimeUtil.HHmm(listBean.jiluKaishiJihuaShijian) + "-" + TimeUtil.HHmm(listBean.jiluJieshuJihuaShijian));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        List<TeamMember.DataBean> nameList = new ArrayList<>();
        shiJianAdapter = new MyShiJianAdapter(nameList);
        recyclerView.setAdapter(shiJianAdapter);
        shiJianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                shiJianAdapter.checkedPosition = i;
                shiJianAdapter.notifyDataSetChanged();
                KLog.i(shiJianAdapter.checkedPosition);
            }
        });


        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shiJianAdapter.checkedPosition == -1) {
                    ToastUtil.show(getActivity(), "请选择需要更换的巡更人员");
                } else {
                    changePatrolMember(listBean.jiluId);
                    alertDialog.dismiss();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(view);
        alertDialog.show();
    }

    /**
     * 更换巡更人的网络请求
     * @param jiluId
     */
    private void changePatrolMember(int jiluId) {
        Map<String, String> map = new HashMap<>();
//        map.put("uuid", Contains.uuid);
        map.put("paibanrenid", shiJianAdapter.getData().get(shiJianAdapter.checkedPosition).getRenyuanAdminId() + "");
        map.put("jiluid", jiluId + "");
        map.put("paibanrenname", shiJianAdapter.getData().get(shiJianAdapter.checkedPosition).getRenyuanName() + "");
        map.put("uuid", Contains.uuid);
        KLog.i(map);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).updateTeamMemberOne(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack entity) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if(entity.status==STATUS_CODE_OK){
                            ToastUtil.show(getActivity(), entity.error);
                            getTeam();
                        }else {
                            onError(entity.status,entity.error);
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
}
