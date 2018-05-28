package com.yxld.xzs.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.AppPeisongorder;

import java.util.List;

/**
 * 作者：yishangfei on 2017/1/6 0006 17:13
 * 邮箱：yishangfei@foxmail.com
 */
public class SummaryAdapter extends BaseQuickAdapter<AppPeisongorder,BaseViewHolder>{
    public SummaryAdapter(List<AppPeisongorder> data) {
        super(R.layout.summary_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AppPeisongorder appPeisongorder) {
                baseViewHolder.setText(R.id.summary_address,"送："+appPeisongorder.getPeisongOrderAddress())
                .setText(R.id.summary_danhao,"订单号："+appPeisongorder.getPeisongOrderOrderBianhao())
                .setText(R.id.summary_money,"¥"+appPeisongorder.getPeisongOrderShouru())
                .setText(R.id.summary_time,"完成时间："+appPeisongorder.getPeisongOrderWanchengTime());
    }
}
