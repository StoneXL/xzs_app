package com.yxld.xzs.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.Login.LoginNewActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.utils.swipeback.SwipeBackHelper;
import com.yxld.xzs.view.ProgressDialog;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by yishangfei on 2016/7/8 0008.
 */

public abstract class BaseActivity extends AppCompatActivity implements ActivityDelegate, SwipeBackHelper.SlideBackManager {
    public static final int STATUS_CODE_OK = 1;
    public AutoRelativeLayout rootLayout;
    public Toolbar toolbar;
    /**
     * 公共的加载进度弹窗
     */
    public ProgressDialog progressDialog;
    protected CompositeDisposable disposables = new CompositeDisposable();

    //滑动后退
    public SwipeBackHelper mSwipeBackHelper;
    public SharedPreferences sp;

    public boolean needFront = false; //toolBar 是否需要显示在最上层的标识


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
        setStatusBar();
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        initToolbar();
        progressDialog = new ProgressDialog(this);
        DemoApplicationLike.getApp().mAppActivityManager.addActivity(this);
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //是因为这段代码的原因吗？？？
        AutoRelativeLayout.LayoutParams lp = new AutoRelativeLayout.LayoutParams(UIUtils.getDisplayWidth(this), (int) (UIUtils.getStatusBarHeight(this) * 3));
        toolbar.setLayoutParams(lp);
        toolbar.setPadding(0, (int) (UIUtils.getStatusBarHeight(this)*0.8), 0, 0);
        toolbar.setTitleMarginTop((int) (UIUtils.getStatusBarHeight(this) * 0.55));

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected void setStatusBar() {
//        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.toolbar_color), 112);
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (AutoRelativeLayout) findViewById(R.id.root_layout);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (rootLayout == null) {
            return;
        }
        if (needFront) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.color_00000000));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rootLayout.addView(view, params);
            toolbar.bringToFront();
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.BELOW, R.id.toolbar);
            rootLayout.addView(view, params);
        }
//        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            //overridePendingTransition(R.anim.translate_left_to_center, R.anim.translate_center_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mSwipeBackHelper == null) {
            mSwipeBackHelper = new SwipeBackHelper(this);
        }
        if (mSwipeBackHelper.mIsSlideAnimPlaying) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(@NonNull Class<?> targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
            progressDialog = null;
        }

        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        DemoApplicationLike.getApp().mAppActivityManager.removeActivity(this);
    }

    protected void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

    }

    protected void showProgressDialog(boolean isCancel) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
            if (isCancel) {
                progressDialog.setCancelable(true);
            } else {
                progressDialog.setCancelable(false);
            }
        }


    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }


    protected void onError(int status, String msg) {
        if (this.isFinishing()) {
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        if (status == 99 || status == -99 || (status == -1 && !TextUtils.isEmpty(msg) && msg.contains("登录失效"))) {
            JPushInterface.setAlias(DemoApplicationLike.getApp().getApplication(), "", new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    Log.d("geek", "JPushInterface clearData  setAlias  gotResult: " + i);
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
            PushServiceFactory.getCloudPushService().unbindAccount(new CommonCallback() {
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
            Contains.cvoListBean = null;
            Contains.cxwyCommons = null;
            Contains.uuid = "";
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("AUTO_ISCHECK", false).apply();
            edit.putString("NAME", "");
            edit.putString("PASSWORD", "");
            edit.commit();
            DemoApplicationLike.getApp().mAppActivityManager.finishAllActivity();
            Intent intent = new Intent(this, LoginNewActivity.class);
            this.startActivity(intent);
        }

    }


    public void exitLogin() {
        //清除阿里绑定的信息
        PushServiceFactory.getCloudPushService().removeAlias("", new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                KLog.i("阿里移除别名成功" + s);
            }

            @Override
            public void onFailed(String s, String s1) {

            }
        });
        PushServiceFactory.getCloudPushService().unbindAccount(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                KLog.i("阿里解除绑定账号成功" + s);
            }

            @Override
            public void onFailed(String s, String s1) {

            }
        });
        Contains.jilu = null;
        Contains.appLogin = null;
        Contains.cvoListBean = null;
        Contains.cxwyCommons = null;
        //清除SharedPreferences里的账号数据
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("NAME", "");
        editor.putString("PASSWORD", "");
        editor.putBoolean("ISCHECK", false);
        editor.putBoolean("AUTO_ISCHECK", false);
        editor.commit();
        DemoApplicationLike.getApp().mAppActivityManager.finishAllActivity();
        Intent mine = new Intent(this, LoginNewActivity.class);
        startActivity(mine);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mSwipeBackHelper == null) {
            mSwipeBackHelper = new SwipeBackHelper(this);
        }
        return mSwipeBackHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    public void finish() {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.finishSwipeImmediately();
            mSwipeBackHelper = null;
        }
        super.finish();
    }

    @Override
    public void destoryContainer() {
        finish();
    }

    @Override
    public BaseActivity getContainerActivity() {
        return this;
    }

    @Override
    public boolean isContainerDead() {
        if (Build.VERSION.SDK_INT > 16) {
            return this.isDestroyed();
        } else {
            return this.isFinishing();
        }
    }

    @Override
    public Activity getSlideActivity() {
        return this;
    }

    @Override
    public boolean supportSlideBack() {
        return true;
    }

    @Override
    public boolean canBeSlideBack() {
        return true;
    }
}
