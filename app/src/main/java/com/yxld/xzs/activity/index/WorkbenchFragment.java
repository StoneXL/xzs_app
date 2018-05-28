package com.yxld.xzs.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.Navigation.ProjectListActivity;
import com.yxld.xzs.activity.Navigation.QuardActivity;
import com.yxld.xzs.activity.Navigation.TicketApplyActivity;
import com.yxld.xzs.activity.Repair.RepairActivity;
import com.yxld.xzs.activity.camera.StationActivity;
import com.yxld.xzs.activity.camera.ZhuJiOrCameraActivity;
import com.yxld.xzs.activity.patrol.PatrolManagerActivity;
import com.yxld.xzs.activity.patrol.PatrolPotEnteringActivity;
import com.yxld.xzs.activity.workbench.DeliveryActivity;
import com.yxld.xzs.activity.workbench.NightOrderListActivity;
import com.yxld.xzs.activity.workbench.NightWarehouseListActivity;
import com.yxld.xzs.activity.workbench.PanDianActivity;
import com.yxld.xzs.activity.workbench.RimActivity;
import com.yxld.xzs.activity.workbench.RobActivity;
import com.yxld.xzs.activity.workbench.SendActivity;
import com.yxld.xzs.activity.workbench.SuggestActivity;
import com.yxld.xzs.base.BaseFragment;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;


/**
 * 工作台fragment
 * Created by William on 2017/11/14.
 */

public class WorkbenchFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.iv_work_rob)
    ImageView ivWorkRob;
    @BindView(R.id.count_1)
    TextView count1;
    @BindView(R.id.iv_work_take)
    ImageView ivWorkTake;
    @BindView(R.id.count_2)
    TextView count2;
    @BindView(R.id.iv_work_send)
    ImageView ivWorkSend;
    @BindView(R.id.count_3)
    TextView count3;
    @BindView(R.id.iv_check_off)
    ImageView ivCheckOff;
    @BindView(R.id.count_4)
    TextView count4;
    @BindView(R.id.iv_check_on)
    ImageView ivCheckOn;
    @BindView(R.id.count_5)
    TextView count5;
    @BindView(R.id.iv_safemanage)
    ImageView ivSafemanage;
    @BindView(R.id.count_6)
    TextView count6;
    @BindView(R.id.iv_work_wacth_area)
    ImageView ivWorkWacthArea;
    @BindView(R.id.count_7)
    TextView count7;
    @BindView(R.id.iv_work_wacth_room)
    ImageView ivWorkWacthRoom;
    @BindView(R.id.count_8)
    TextView count8;
    @BindView(R.id.iv_work_seek)
    ImageView ivWorkSeek;
    @BindView(R.id.count_9)
    TextView count9;
    @BindView(R.id.iv_entrancecard)
    ImageView ivEntrancecard;
    @BindView(R.id.count_10)
    TextView count10;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.iv_check_on1)
    ImageView ivCheckOn1;
    @BindView(R.id.iv_work_wuyebx)
    ImageView ivWorkWuyebx;
    @BindView(R.id.iv_rim)
    ImageView ivRim;
    @BindView(R.id.count_11)
    TextView count11;

    Unbinder unbinder;
    protected CompositeDisposable disposables = new CompositeDisposable();
    /**
     * 请求码
     */
    private final int REQUESTCODE_ROB = 100;
    private final int REQUESTCODE_DELIVERY = 101;
    private final int REQUESTCODE_SEND = 102;
    private final int REQUESTCODE_RIM = 103;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workbench, null);
        unbinder = ButterKnife.bind(this, view);
        swipeLayout.setOnRefreshListener(this);
        UIUtils.configSwipeRefreshLayoutColors(swipeLayout);
//        swipeLayout.setRefreshing(true);//显示加载进度条.要在主线程中执行
        initData();
        return view;
    }

    /**
     * 请求数据
     */
    private void initData() {
        KLog.i("Contains.indexMessageList----->" + Contains.indexMessageList.size() + "valus" + Contains
                .indexMessageList.get(0) + "-" + Contains.indexMessageList.get(1) + "-" + Contains.indexMessageList
                .get(2));
        if (Contains.indexMessageList != null && Contains.indexMessageList.size() > 0) {
            if (Contains.indexMessageList.get(0) != 0) {
                count1.setVisibility(View.VISIBLE);
                count1.setText(Contains.indexMessageList.get(0) + "");
                setAnimation(count1);
            } else {
                count1.clearAnimation();
                count1.setVisibility(View.GONE);
            }

            if (Contains.indexMessageList.get(1) != 0) {
                count2.setVisibility(View.VISIBLE);
                count2.setText(Contains.indexMessageList.get(1) + "");
                setAnimation(count2);
            } else {
                count2.clearAnimation();
                count2.setVisibility(View.GONE);
            }

            if (Contains.indexMessageList.get(2) != 0) {
                count3.setVisibility(View.VISIBLE);
                count3.setText(Contains.indexMessageList.get(2) + "");
                setAnimation(count3);
            } else {
                count3.clearAnimation();
                count3.setVisibility(View.GONE);
            }

        }
        swipeLayout.setRefreshing(false);
    }

    private void setAnimation(TextView textView) {
        /** 设置缩放动画 */
        final ScaleAnimation animation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);//设置动画持续时间
        animation.setRepeatCount(-1);//设置重复次数
        animation.setRepeatMode(Animation.REVERSE);//重复 缩小和放大效果
        textView.startAnimation(animation);
    }

    @Override
    protected void lazyLoad() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    @OnClick({R.id.iv_work_rob, R.id.iv_work_take, R.id.iv_work_send, R.id.iv_check_off, R.id.iv_check_on, R.id
            .iv_safemanage, R.id.iv_work_wacth_area, R.id.iv_work_wacth_room, R.id.iv_work_seek, R.id
            .iv_entrancecard, R.id.iv_moon, R.id.iv_moonorder, R.id.iv_work_wuyebx, R.id.iv_xunjiandian, R.id
            .iv_check_on1, R.id.iv_rim, R.id.iv_work_tousu,R.id.iv_work_pandian})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_work_rob://待抢单
                intent = new Intent(getActivity(), RobActivity.class);
                startActivityForResult(intent, REQUESTCODE_ROB);
                break;
            case R.id.iv_work_take://待取货
                intent = new Intent(getActivity(), DeliveryActivity.class);
                startActivityForResult(intent, REQUESTCODE_DELIVERY);

                break;
            case R.id.iv_work_send://待送达
                intent = new Intent(getActivity(), SendActivity.class);
                startActivityForResult(intent, REQUESTCODE_SEND);
                break;
            case R.id.iv_check_off://未审批
             /*   intent = new Intent(getActivity(), ApproveActivity.class);
                startActivityForResult(intent,REQUESTCODE_APPROVE);*/
                ToastUtil.showInfo(getActivity(), "敬请期待");
                break;
            case R.id.iv_check_on://已审批
               /* intent = new Intent(getActivity(), ExaminedActivity.class);
                getActivity().startActivity(intent);*/
                ToastUtil.showInfo(getActivity(), "敬请期待");
                break;
            case R.id.iv_safemanage://居家安防
//                intent = new Intent(getActivity(), StationActivity.class);
//                intent.putExtra(StationActivity.ENTER_TYPE, 1);
//                getActivity().startActivity(intent);
                intent = new Intent(getActivity(), ZhuJiOrCameraActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_work_wacth_area://区域监控
                intent = new Intent(getActivity(), ProjectListActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_work_wacth_room://岗位监控
                intent = new Intent(getActivity(), StationActivity.class);
                intent.putExtra(StationActivity.ENTER_TYPE, 2);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_work_seek://巡检系统
                intent = new Intent(getActivity(), PatrolManagerActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_entrancecard://特殊门禁
                intent = new Intent(getActivity(), QuardActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_xunjiandian://新增巡检点
                intent = new Intent(getActivity(), PatrolPotEnteringActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_work_wuyebx://物业报修
                intent = new Intent(getActivity(), RepairActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_moon://夜间订单
                intent = new Intent(getActivity(), NightOrderListActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_moonorder://夜间出库单
//                intent = new Intent(getActivity(), NightActivity.class);
                intent = new Intent(getActivity(), NightWarehouseListActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_check_on1://电子券审批
                intent = new Intent(getActivity(), TicketApplyActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_work_tousu://投诉建议
                intent = new Intent(getActivity(), SuggestActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_work_pandian://商品盘点
//                ToastUtil.showInfo(getActivity(), "敬请期待");
                intent = new Intent(getActivity(), PanDianActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.iv_rim://周边商家
                //ToastUtil.showInfo(getActivity(), "敬请期待");
                intent = new Intent(getActivity(), RimActivity.class);
                getActivity().startActivity(intent);
//                startActivityForResult(intent, REQUESTCODE_RIM);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_ROB:
                onRefresh();
                break;
            case REQUESTCODE_DELIVERY:
                onRefresh();
                break;
            case REQUESTCODE_SEND:
                onRefresh();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onRefresh() {
        initData();
    }
}
