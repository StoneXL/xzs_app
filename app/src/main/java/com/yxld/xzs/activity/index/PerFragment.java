package com.yxld.xzs.activity.index;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.socks.library.KLog;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.Income.IncomeActivity;
import com.yxld.xzs.activity.Navigation.SettingsActivity;
import com.yxld.xzs.adapter.MainMenuAdapter;
import com.yxld.xzs.base.BaseFragment;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.BaseBack1;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.UIUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ch.ielse.view.SwitchView;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import rx.functions.Action1;

/**
 * @author xlei
 * @Date 2017/11/14.
 */

public class PerFragment extends BaseFragment implements SwitchView.OnStateChangedListener {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_jiedanzhuangtai)
    TextView mTvJiedanzhuangtai;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.SwitchButton)
    SwitchView mSwitchButton;
    @BindView(R.id.recycler_menu)
    RecyclerView mRecyclerMenu;
    Unbinder unbinder;
    @BindView(R.id.auto_relative_layout)
    AutoRelativeLayout mAutoRelativeLayout;
    private MainMenuAdapter mMainMenuAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_per, null);
        unbinder = ButterKnife.bind(this, view);
        mRecyclerMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainMenuAdapter = new MainMenuAdapter(UIUtils.getHomeMenu());
        mRecyclerMenu.setAdapter(mMainMenuAdapter);
        mSwitchButton.setOnStateChangedListener(this);
        mTvName.setText(Contains.appLogin.getAdminNickName());
        setEvent();
        return view;
    }

    private void setEvent() {
        mMainMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent();
                switch (i) {
                    case 0:
                        //我的收入
                        intent.setClass(getActivity(), IncomeActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //联系客服
                        showCallDialog();
                        break;
                    case 2:
                        //通用设置
                        intent.setClass(getActivity(), SettingsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void showCallDialog() {
        new AlertView.Builder().setContext(getActivity())
                .setTitle("是否联系客服？")
                .setCancelText("取消")
                .setOthers(new String[]{"确定"})
                .setStyle(AlertView.Style.ActionSheet)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            Hotline();
                        }
                    }
                })
                .build().setCancelable(true).show();

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断 switchbutton开关状态
        if (Contains.appLogin != null) {
            if ("开启".equals(Contains.appLogin.getCxwyPeisongState())) {
                mAutoRelativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_blue_radius_8));
                mTvJiedanzhuangtai.setText("接单中");
                mSwitchButton.setOpened(true);
            } else {
                mAutoRelativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_gray_radius_8));
                mTvJiedanzhuangtai.setText("未开工");
                mSwitchButton.setOpened(false);
            }
        }
    }

    @Override
    public void toggleToOn(SwitchView view) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("cxwyPeisongId", Contains.appLogin.getAdminId() + "");
        map.put("cxwyPeisongState", "开启");
        Disposable subscribe = HttpAPIWrapper.getInstance().peisongState(map).subscribe(new Consumer<BaseBack>() {
            @Override
            public void accept(@NonNull BaseBack s) throws Exception {
                KLog.i(s);
                if (s.status == 0) {
                    Toast.makeText(getActivity(), "接单开启成功", Toast.LENGTH_SHORT).show();
                    mSwitchButton.toggleSwitch(true);
                    Contains.appLogin.setCxwyPeisongState("开启");
                    mAutoRelativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_blue_radius_8));
                    mTvJiedanzhuangtai.setText("接单中");
                } else {
                   onError(s.status,s.MSG);
                    mSwitchButton.toggleSwitch(false);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                KLog.i("onError" + throwable.toString());
                Toast.makeText(getActivity(), "接单开启失败，请稍后重新尝试", Toast.LENGTH_SHORT).show();
                mSwitchButton.toggleSwitch(false);
            }
        });
    }

    @Override
    public void toggleToOff(SwitchView view) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("cxwyPeisongId", Contains.appLogin.getAdminId() + "");
        map.put("cxwyPeisongState", "关闭");
        HttpAPIWrapper.getInstance().peisongState(map).subscribe(new Consumer<BaseBack>() {
            @Override
            public void accept(@NonNull BaseBack baseBack) throws Exception {
                if (baseBack.status == 0) {
                    Toast.makeText(getActivity(), "接单关闭成功", Toast.LENGTH_SHORT).show();
                    mSwitchButton.toggleSwitch(false);
                    Contains.appLogin.setCxwyPeisongState("关闭");
                    mAutoRelativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_gray_radius_8));
                    mTvJiedanzhuangtai.setText("未开工");
                } else {
                    onError(baseBack.status,baseBack.MSG);
                    mSwitchButton.toggleSwitch(true);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                KLog.i("onError" + throwable.toString());
                mSwitchButton.toggleSwitch(true);
                Toast.makeText(getActivity(), "接单关闭失败，请稍后重新尝试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取客服电话网络请求
    private void Hotline() {
        Map<String, String> map = new HashMap<>();
        map.put("xiaoqu", Contains.appLogin.getAdminXiangmuId().toString());
        HttpAPIWrapper.getInstance().getPhone(map).subscribe(new Consumer<BaseBack1>() {
            @Override
            public void accept(@NonNull BaseBack1 baseBack) throws Exception {
                if (baseBack.status == 0) {
                    final String hotline = baseBack.getRow();
                    RxPermissions rxPermissions = new RxPermissions(getActivity());
                    rxPermissions.setLogging(true);
                    rxPermissions.requestEach(Manifest.permission.CALL_PHONE)
                            .subscribe(new Action1<Permission>() {
                                @Override
                                public void call(Permission permission) {
                                    Logger.d("Permission result " + permission);
                                    if (permission.granted) {
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_CALL);
                                        //url:统一资源定位符
                                        //uri:统一资源标示符（更广）
                                        intent.setData(Uri.parse("tel:" + hotline));
                                        //开启系统拨号器
                                        startActivity(intent);
                                    } else if (permission.shouldShowRequestPermissionRationale) {
                                        // Denied permission without ask never again
                                        Toast.makeText(getActivity(), "没有访问也没有拒绝", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Denied permission with ask never again
                                        // Need to go to the settings
                                        Toast.makeText(getActivity(), "没有权限,您不能打电话", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    onError(baseBack.status,baseBack.MSG);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                KLog.i("onError" + throwable.toString());
            }
        });
    }
}
