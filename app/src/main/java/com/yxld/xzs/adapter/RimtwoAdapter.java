package com.yxld.xzs.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.RimOrderDetailBean;

import java.util.List;

/**
 * 欣助手 二级列表适配器
 * Created by William on 2018/1/9.
 */

public class RimtwoAdapter extends BaseQuickAdapter<RimOrderDetailBean, BaseViewHolder> {

    private int produceType = 1;
    private ImageView imageView;
    private TextView tvname;
    private TextView tvnum;
    private TextView tvUnitprice;

    public RimtwoAdapter(List<RimOrderDetailBean> data, int produceType) {
        super(R.layout.item_rob_lv1, data);
        this.produceType = produceType;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RimOrderDetailBean rimOrderDetailBean) {
        imageView = baseViewHolder.getView(R.id.iv_goods_icon);
        tvname = baseViewHolder.getView(R.id.tv_name);
        tvnum = baseViewHolder.getView(R.id.tv_num);
        tvUnitprice = baseViewHolder.getView(R.id.tv_unit_price);

        if (produceType == 2) {
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.serviceheart));
        } else if (produceType == 1) {
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.goods));
        }


        int adapterPosition = baseViewHolder.getAdapterPosition();
        if (adapterPosition == 0) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }

        tvname.setText(rimOrderDetailBean.getOrderDetailsProductName());
        tvnum.setText("x " + rimOrderDetailBean.getOrderDetailsProductNumber());
        tvUnitprice.setText("¥ " + rimOrderDetailBean.getOrderDetailsPreferentialPrice());
    }
}
