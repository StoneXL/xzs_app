package com.yxld.xzs.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;

import java.util.List;

/**
 * Yuan.Y.Q
 * Date 2017/7/21.
 */

public class ApplyRepairCheckMaterialAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public ApplyRepairCheckMaterialAdapter(List<String> data) {
        super(R.layout.item_apply_repair_check_material,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
