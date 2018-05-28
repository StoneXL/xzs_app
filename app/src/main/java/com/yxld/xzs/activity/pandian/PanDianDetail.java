package com.yxld.xzs.activity.pandian;

import android.os.Bundle;

import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * @author xlei
 * @Date 2018/5/9.
 */

public class PanDianDetail extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandian_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // initView();
    }
}
