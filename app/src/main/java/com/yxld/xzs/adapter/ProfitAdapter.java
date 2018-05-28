package com.yxld.xzs.adapter;

import com.yxld.xzs.entity.AppPeisongorder;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;

import java.util.List;

/**
 * 作者：yishangfei on 2017/1/6 0006 16:42
 * 邮箱：yishangfei@foxmail.com
 */
public class ProfitAdapter extends BaseQuickAdapter<AppPeisongorder,BaseViewHolder>{
    public ProfitAdapter(List<AppPeisongorder> data) {
        super(R.layout.activity_details_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AppPeisongorder appPeisongorder) {
        baseViewHolder.setText(R.id.details_address,appPeisongorder.getPeisongOrderAddress())
                .setText(R.id.details_danhao,appPeisongorder.getPeisongOrderOrderBianhao())
                .setText(R.id.details_time,appPeisongorder.getPeisongOrderWanchengTime())
                .setText(R.id.details_money,appPeisongorder.getPeisongOrderShouru());
    }
}
