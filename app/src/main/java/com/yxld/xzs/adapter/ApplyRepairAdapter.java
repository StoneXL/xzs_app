package com.yxld.xzs.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.ApplyRepairEntity;
import com.yxld.xzs.view.SlideItem;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;


public class ApplyRepairAdapter extends BaseQuickAdapter<ApplyRepairEntity,BaseViewHolder> {


    public ApplyRepairAdapter(List<ApplyRepairEntity> data) {
        super(R.layout.item_apply_repair,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyRepairEntity item) {
        helper.setTag(R.id.tv_delete,"delete")
                .addOnClickListener(R.id.tv_delete)
                .addOnClickListener(R.id.tv_show_material)
                .addOnClickListener(R.id.item);
    }
}
