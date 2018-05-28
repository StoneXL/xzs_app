package com.yxld.xzs.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;

import java.util.List;

/**
 * Yuan.Y.Q
 * Date 2017/7/21.
 */

public class ApplyScrapAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public ApplyScrapAdapter(List<String> data) {
        super(R.layout.item_apply_scrap,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setTag(R.id.tv_delete,"delete")
                .addOnClickListener(R.id.item)
                .addOnClickListener(R.id.tv_delete);

    }
}
