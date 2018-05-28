package com.yxld.xzs.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.NightWarehouseBean;

/**
 * Created by William on 2017/11/28.
 */

public class NightWarehouseAdapter extends BaseQuickAdapter<NightWarehouseBean, BaseViewHolder> {

    private TextView tvWarehouseNum;//出库单号
    private TextView tvName;//领取人
    private TextView tvTime;//提交时间
    private TextView tvState;//出库单状态

    public NightWarehouseAdapter() {
        super(R.layout.item_night_warehouse);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NightWarehouseBean nightWarehouseBean) {
        tvWarehouseNum = baseViewHolder.getView(R.id.tv_warehouse_num);
        tvTime = baseViewHolder.getView(R.id.tv_time);
        tvName = baseViewHolder.getView(R.id.tv_name);
        tvState = baseViewHolder.getView(R.id.tv_state);

        tvWarehouseNum.setText(nightWarehouseBean.getChukudanBianhao());
        tvTime.setText(nightWarehouseBean.getTijiaoShijian());
        tvName.setText(nightWarehouseBean.getLinghuoren());
        int linghuoZhuangtai = nightWarehouseBean.getLinghuoZhuangtai();
        if (linghuoZhuangtai == 1) {
            tvState.setText("待备货");
        } else if (linghuoZhuangtai == 2) {
            tvState.setText("已备货");
        }

    }
}
