package com.yxld.xzs.activity.Navigation;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.view.MyEditTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApplyScrapCheckActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.edit_market)
    MyEditTextView editMarket;
    @BindView(R.id.tv_pass)
    TextView tvPass;
    @BindView(R.id.tv_unpass)
    TextView tvUnpass;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_scrap_check);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scrollView.smoothScrollTo(0,0);

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("" + i);
        }
        ApplyScrapCheckAdapter adapter = new ApplyScrapCheckAdapter(datas);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

    }


    private static class ApplyScrapCheckAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ApplyScrapCheckAdapter(List<String> data) {
            super(R.layout.item_apply_scrap_product, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            View line = helper.getView(R.id.line);
            if (helper.getLayoutPosition() == getData().size() - 1) {
                line.setVisibility(View.INVISIBLE);
            }
        }
    }
}
