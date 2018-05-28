package com.yxld.xzs.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.OrderDetailBean;

/**
 * Created by William on 2017/11/29.
 */

public class NightOrderDetailAdapter extends BaseQuickAdapter<OrderDetailBean, BaseViewHolder> {

    private TextView tvName;
    private TextView tvUnit;
    private TextView tvNum;
    public NightOrderDetailAdapter() {
        super(R.layout.item_repair_material1);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OrderDetailBean orderDetailBean) {
        tvName = baseViewHolder.getView(R.id.tv_goods_name);
        tvUnit = baseViewHolder.getView(R.id.tv_unit);
        tvNum = baseViewHolder.getView(R.id.tv_num);

        tvName.setText(orderDetailBean.getShangpinMing());
        tvUnit.setText(orderDetailBean.getShangpinGuige());
        tvNum.setText(orderDetailBean.getShangpinShuliang()+"");
    }
}
