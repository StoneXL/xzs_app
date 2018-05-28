package com.yxld.xzs.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.OrderBean;
import com.yxld.xzs.entity.OrderDetailBean;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 2017/11/21.
 */

public class DeliveryAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {

    private TextView tvLocation;
    private TextView tvPrice;
    private TextView tvTime;
    private TextView tvDeliveryPrice;
    private RecyclerView recyclerView;
    private ImageButton ibIsshow;
    private Button button;
    private AutoLinearLayout autoLinearLayout;

    RobtwoAdapter douAdapter;

    public DeliveryAdapter() {
        super(R.layout.item_rob_lv0);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final OrderBean orderBean) {
        tvLocation = baseViewHolder.getView(R.id.tv_location);
        tvPrice = baseViewHolder.getView(R.id.tv_price);
        tvTime = baseViewHolder.getView(R.id.tv_time);
        tvDeliveryPrice = baseViewHolder.getView(R.id.tv_delivery_price);
        recyclerView = baseViewHolder.getView(R.id.rv);
        ibIsshow = baseViewHolder.getView(R.id.ib_isshow);
        button = baseViewHolder.getView(R.id.bt_rob);
        autoLinearLayout = baseViewHolder.getView(R.id.atll);

        button.setVisibility(View.VISIBLE);
        button.setText("确认取货");

        List<OrderDetailBean> list1 = orderBean.getOrderDetailList();//一级数据
        List<OrderDetailBean> list2 = new ArrayList<>();//二级列表数据
        recyclerView.setLayoutManager(new LinearLayoutManager(baseViewHolder.itemView.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //控制是否显示二级列表开关
        if (list1.size() <= 2) {
            list2.clear();
            list2.addAll(list1);
            autoLinearLayout.setVisibility(View.GONE);
        } else {
            //控制是否显示完全二级列表
            if (!orderBean.isShow()) {
                list2.clear();
                list2.add(list1.get(0));
                list2.add(list1.get(1));
                ibIsshow.setImageDrawable(mContext.getApplicationContext().getResources()
                        .getDrawable(R.mipmap.open_));
                autoLinearLayout.setVisibility(View.VISIBLE);
            } else {
                list2.clear();
                list2.addAll(list1);
                ibIsshow.setImageDrawable(mContext.getApplicationContext().getResources()
                        .getDrawable(R.mipmap.close));
                autoLinearLayout.setVisibility(View.VISIBLE);
            }
        }

        tvLocation.setText(orderBean.getShouhuoDizhi());
        tvPrice.setText("¥ " + orderBean.getZongjine());
        tvTime.setText(orderBean.getFukuanShijian());
        tvDeliveryPrice.setText("配送费 ¥ " + orderBean.getPeisongfei());

        int adapterPosition = baseViewHolder.getAdapterPosition();
        douAdapter = new RobtwoAdapter(list2);
        recyclerView.setAdapter(douAdapter);

        ibIsshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderBean.setShow(!orderBean.isShow());
                notifyDataSetChanged();
            }
        });

        baseViewHolder.addOnClickListener(R.id.bt_rob);
    }
}
