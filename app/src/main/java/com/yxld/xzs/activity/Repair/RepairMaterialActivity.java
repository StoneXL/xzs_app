package com.yxld.xzs.activity.Repair;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yxld.xzs.R;
import com.yxld.xzs.adapter.RepairFlowAdapter;
import com.yxld.xzs.adapter.RepairMaterialAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.RobBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.PopWindowUtil;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.view.CustomPopWindow;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 物资报修审批
 * Created by William on 2017/11/22.
 */

public class RepairMaterialActivity extends BaseActivity {
    @BindView(R.id.tv_repair_type)
    TextView tvRepairType;//维护类型
    @BindView(R.id.tv_program)
    TextView tvProgram;//项目
    @BindView(R.id.tv_apply_name)
    TextView tvApplyName;//申请人
    @BindView(R.id.tv_apply_department)
    TextView tvApplyDepartment;//申请部门
    @BindView(R.id.tv_remark)
    TextView tvRemark;//备注
    @BindView(R.id.rv_material)
    RecyclerView rvMaterial;//物资列表
    @BindView(R.id.rv_flow)
    RecyclerView rvFlow;//审批流程
    @BindView(R.id.bt_pass)
    Button btPass;
    @BindView(R.id.bt_nopass)
    Button btNopass;

    private RepairMaterialAdapter repairMaterialAdapter;
    private RepairFlowAdapter repairFlowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_material);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initAdapter();
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("sdfsd");
        list.add("sdfsd");
        list.add("sdfsd");
        list.add("sdfsd");
        list.add("sdfsd");
//        repairMaterialAdapter.setNewData(list);
        repairFlowAdapter.setNewData(list);
    }

    private void initAdapter() {
        repairMaterialAdapter = new RepairMaterialAdapter();
        repairFlowAdapter = new RepairFlowAdapter();
        rvMaterial.setAdapter(repairMaterialAdapter);
        rvFlow.setAdapter(repairFlowAdapter);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        rvMaterial.setLayoutManager(new LinearLayoutManager(this));
        rvFlow.setLayoutManager(new LinearLayoutManager(this));
        rvMaterial.setNestedScrollingEnabled(false);//设置嵌套滑动不能用
        rvFlow.setNestedScrollingEnabled(false);//设置嵌套滑动不能用
    }

    @OnClick({R.id.bt_pass, R.id.bt_nopass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_pass:
//                ToastUtil.show(this, "通过");
                showPickPop(true);
                break;
            case R.id.bt_nopass:
//                ToastUtil.show(this, "不通过");
                showPickPop(false);
                break;
        }
    }

    private void showPickPop(final boolean isPass) {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_pick_edit, null);
        final EditText editText = (EditText) view.findViewById(R.id.et_opinion);
        Button btConPass = (Button) view.findViewById(R.id.bt_confirm_pass);
        Button btConNoPass = (Button) view.findViewById(R.id.bt_confirm_nopass);

        btConPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (!TextUtils.isEmpty(editText.getText())) {

                } else {
                    ToastUtil.show(RepairMaterialActivity.this, "意见不能为空");
                }*/
                uploadOpinion(editText.getText().toString().trim(), isPass);
                CustomPopWindow.onBackPressed();
            }
        });
        btConNoPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
            }
        });
        AutoLinearLayout ll_popup = (AutoLinearLayout) view.findViewById(R.id.ll_popup);
        PopWindowUtil.showFullScreenPopWindow(this,btPass, view, ll_popup);

        /*ll_popup.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_manage_product_in));
        new CustomPopWindow.PopupWindowBuilder(this)
                .setClippingEnable(false)
                .setFocusable(true)
                .setView(view)
                .setContenView(ll_popup)
                .size(UIUtils.getDisplayWidth(this), UIUtils.getDisplayHeigh(this))
                .create()
                .showAtLocation(btPass, Gravity.CENTER, 0, 0);*/
    }

    /**
     * 提交审批
     *
     * @param opinion
     * @param isPass
     */
    private void uploadOpinion(String opinion, boolean isPass) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);

        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .robList(map)
                .subscribe(new Consumer<RobBean>() {
                    @Override
                    public void accept(@NonNull RobBean robBean) throws Exception {
                        if (robBean.status == 0) {

                            finish();
                        } else {

                            onError(robBean.status, robBean.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.show(RepairMaterialActivity.this, "加载失败");
                    }
                });
        disposables.add(disposable);
    }
}
