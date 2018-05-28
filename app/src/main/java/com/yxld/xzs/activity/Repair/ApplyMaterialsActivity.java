package com.yxld.xzs.activity.Repair;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.ApplyMaterialsAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.TestBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.datepicker.WheelView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author xlei
 * @Date 2017/11/27.
 */

public class ApplyMaterialsActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_zongjia)
    TextView mTvZongjia;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    private ApplyMaterialsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_materials);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //loadDataFromServer();
        mAdapter = new ApplyMaterialsAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        initData();
    }

    private void initData() {
        List<TestBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TestBean testBean = new TestBean(0, i + "", "15/个", "大号灯泡" + i);
            list.add(testBean);
        }
        mAdapter.setNewData(list);
        mAdapter.setOnItemClick(new ApplyMaterialsAdapter.onItemClick() {
            @Override
            public void setOnItemClick(int type,int position, TextView text,TextView text2) {
                int current=Integer.parseInt(text.getText().toString());
                int max=Integer.parseInt(text2.getText().toString());
                if (type==0){
                    if (current>=max){
                        ToastUtil.showInfo(ApplyMaterialsActivity.this,"不能再多了");
                        return;
                    }
                      current++;
                }else {
                    if (current==0){
                        ToastUtil.showInfo(ApplyMaterialsActivity.this,"不能再少了");
                        return;
                    }
                        current--;
                }
               mAdapter.getData().get(position).setImgid(current);
                mAdapter.notifyDataSetChanged();
            }

        });
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        showPopDialog();
        KLog.i("1111111111111111111111");
    }

    private void showPopDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_weixiufeiyong, null);
       // view.setFocusable(true);
       // view.setFocusableInTouchMode(true);
        AutoLinearLayout ll_content = (AutoLinearLayout) view.findViewById(R.id.ll_content);
     //   ll_content.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_manage_product_in));
        EditText editText= (EditText) view.findViewById(R.id.tv_qita);
//        if(editText!=null){
////设置可获得焦点
//            editText.setFocusable(true);
//            editText.setFocusableInTouchMode(true);
////请求获得焦点
//            editText.requestFocus();
////调用系统输入法
//            InputMethodManager inputManager = (InputMethodManager) editText
//                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputManager.showSoftInput(editText, 0);
//        }
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    //    popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setFocusable(true);
        //设置背景点击外部会消失  popupWindow.setOutsideTouchable(true)没用

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(mRecyclerView.getRootView(),Gravity.BOTTOM,0,mTvCommit.getHeight());

//        new CustomPopWindow.PopupWindowBuilder(this)
//                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//                .setFocusable(true)
//                .setView(view)
//                .setContenView(ll_content)
//                .size(UIUtils.getDisplayWidth(this), UIUtils.getDisplayHeigh(this))
//                .create()
//                .showAtLocation(mRecyclerView, Gravity.CENTER, 0, -mTvCommit.getHeight());
    }

    private void loadDataFromServer() {
        Map<String, String> map = new HashMap<>();
        map.put("proid", Contains.appLogin.getAdminXiangmuId() + "");
        HttpAPIWrapper.getInstance().applyMaterialsList(map).subscribe(new Consumer<BaseBack>() {
            @Override
            public void accept(@NonNull BaseBack baseBack) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        });
    }
}
