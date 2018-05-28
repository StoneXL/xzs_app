package com.yxld.xzs.activity.patrol;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.jph.takephoto.app.TakePhotoFragment;
import com.socks.library.KLog;
import com.yxld.xzs.activity.Login.LoginNewActivity;
import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.view.ProgressDialog;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BasePatrolFragment extends TakePhotoFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final int STATUS_CODE_OK = 1;
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    protected CompositeDisposable disposables = new CompositeDisposable();
    protected PatrolBadgeCallback mActivityCallback;
    protected ProgressDialog mProgressDialog;
    public BasePatrolFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivityCallback = (PatrolBadgeCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement PatrolBadgeCallback");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressDialog = new ProgressDialog(getContext());
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        if(mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.hide();
            mProgressDialog = null;
        }
    }

    protected void onError(int status,String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        if(status == 99 || status == -99 || (status ==-1 && !TextUtils.isEmpty(msg) && msg.contains("登录失效"))){
            JPushInterface.setAlias(DemoApplicationLike.getApp().getApplication(), "", new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    Log.d("geek", "JPushInterface clearData  setAlias  gotResult: "+i);
                }
            });
            PushServiceFactory.getCloudPushService().removeAlias("", new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    KLog.i("aliyunpush removeAlias  gotResult: " + s);
                }

                @Override
                public void onFailed(String s, String s1) {

                }
            });
            PushServiceFactory.getCloudPushService().unbindAccount( new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    KLog.i("aliyunpush unbindAccount  gotResult: " + s);
                }

                @Override
                public void onFailed(String s, String s1) {

                }
            });
            Contains.jilu = null;
            Contains.appLogin = null;
            Contains.cvoListBean.clear();
            Contains.cvoListBean = null;
            Contains.cxwyCommons.clear();
            Contains.cxwyCommons = null;
            Contains.uuid = "";
            SharedPreferences sp = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            sp.edit().putBoolean("AUTO_ISCHECK", false).apply();
            Intent intent = new Intent(getActivity(), LoginNewActivity.class);
            intent.putExtra("RELOGIN",1);
            getActivity().startActivity(intent);
        }
    }
}
