package com.yxld.xzs.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.OrderBean;

/**
 * Created by William on 2017/11/27.
 */

public class NightOrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {
    private TextView tvWarehouseNum;
    private TextView tvTime;
    private TextView tvPhone;
    private TextView tvLocation;
    private TextView tvState;

    public NightOrderAdapter() {
        super(R.layout.item_night_order);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OrderBean orderBean) {
        tvWarehouseNum = baseViewHolder.getView(R.id.tv_warehouse_num);
        tvTime = baseViewHolder.getView(R.id.tv_time);
        tvPhone = baseViewHolder.getView(R.id.tv_phone);
        tvLocation = baseViewHolder.getView(R.id.tv_location);
        tvState = baseViewHolder.getView(R.id.tv_state);

        tvWarehouseNum.setText(orderBean.getBianhao());
        tvTime.setText(orderBean.getXiadanShijian());
        tvPhone.setText(orderBean.getShouhuoDianhua());
        tvLocation.setText(orderBean.getShouhuoDizhi());

        if (orderBean.getZhuangtai() == 2) {
            if (orderBean.getPeisongrenId() == 0) {
                tvState.setText("待指派");
            } else {
                tvState.setText("待取货");
            }
        } else if (orderBean.getZhuangtai() == 3) {
            tvState.setText("待送达");
        }
    }
}
