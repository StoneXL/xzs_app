package com.yxld.xzs.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.OrderDetailBean;

import java.util.List;

/**
 * Created by William on 2017/11/16.
 */

public class RobtwoAdapter extends BaseQuickAdapter<OrderDetailBean, BaseViewHolder> {

    private ImageView imageView;
    private TextView tvname;
    private TextView tvnum;
    private TextView tvUnitprice;


    public RobtwoAdapter(List<OrderDetailBean> data) {
        super(R.layout.item_rob_lv1, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OrderDetailBean orderDetailBean) {
        imageView = baseViewHolder.getView(R.id.iv_goods_icon);
        tvname = baseViewHolder.getView(R.id.tv_name);
        tvnum = baseViewHolder.getView(R.id.tv_num);
        tvUnitprice = baseViewHolder.getView(R.id.tv_unit_price);

      /*  if (true) {
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.serviceheart));
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.goods));
        }*/
        int adapterPosition = baseViewHolder.getAdapterPosition();
        if (adapterPosition == 0) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }

        tvname.setText(orderDetailBean.getShangpinMing());
        tvnum.setText("X" + orderDetailBean.getShangpinShuliang());
        tvUnitprice.setText("¥ " + orderDetailBean.getShangpinZongjia());
    }
}
