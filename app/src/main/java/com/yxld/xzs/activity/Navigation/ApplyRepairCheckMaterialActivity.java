package com.yxld.xzs.activity.Navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxld.xzs.R;
import com.yxld.xzs.adapter.ApplyRepairCheckMaterialAdapter;
import com.yxld.xzs.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApplyRepairCheckMaterialActivity extends BaseActivity {

    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.ll_btn_container)
    LinearLayout llBtnContainer;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_repair_check_material);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> datas = new ArrayList<>();
        for (int i = 0 ;i < 10 ;i++){
            datas.add(""+i);
        }
        ApplyRepairCheckMaterialAdapter adapter = new ApplyRepairCheckMaterialAdapter(datas);
        recyclerView.setAdapter(adapter);
    }
}
