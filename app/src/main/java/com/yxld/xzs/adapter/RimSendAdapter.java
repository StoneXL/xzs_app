package com.yxld.xzs.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.RimOrderBean;
import com.yxld.xzs.entity.RimOrderDetailBean;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 2018/1/5.
 */

public class RimSendAdapter extends BaseQuickAdapter<RimOrderBean, BaseViewHolder> {

    private RecyclerView recyclerView;
    private RimtwoAdapter douAdapter;
    private AutoLinearLayout autoLinearLayout;
    private ImageButton ibIsshow;

    public RimSendAdapter() {
        super(R.layout.item_rim_rob, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final RimOrderBean orderBean) {
        autoLinearLayout = baseViewHolder.getView(R.id.atll);
        ibIsshow = baseViewHolder.getView(R.id.ib_isshow);

        baseViewHolder.setText(R.id.tv_location, orderBean.getOrderUserAddress()).setText(R.id
                .tv_shop_name, orderBean.getOrderBusinessName()).setText(R.id.tv_time, orderBean
                .getOrderBespeakTime()).setText(R.id.tv_delivery_price, "配送费 ¥ " + orderBean
                .getOrderSendMoney());

        baseViewHolder.setVisible(R.id.autosend_rim, true);

        List<RimOrderDetailBean> list1 = orderBean.getDetailList();//一级数据
        List<RimOrderDetailBean> list2 = new ArrayList<>();//二级列表数据
        recyclerView = baseViewHolder.getView(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(baseViewHolder.itemView.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //控制是否显示二级列表开关
        if (list1.size() <= 2) {
            list2.clear();
            list2.addAll(list1);
            autoLinearLayout.setVisibility(View.GONE);
        } else {
            //控制是否完全显示二级列表
            if (!orderBean.isShow()) {
                list2.clear();
                list2.add(list1.get(0));
                list2.add(list1.get(1));
                ibIsshow.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.open_));
                autoLinearLayout.setVisibility(View.VISIBLE);
            } else {
                list2.clear();
                list2.addAll(list1);
                ibIsshow.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.close));
                autoLinearLayout.setVisibility(View.VISIBLE);
            }
        }

        int adapterPosition = baseViewHolder.getAdapterPosition();
        douAdapter = new RimtwoAdapter(list2,orderBean.getProduceType());
        recyclerView.setAdapter(douAdapter);

        ibIsshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderBean.setShow(!orderBean.isShow());
                notifyDataSetChanged();
            }
        });

        baseViewHolder.addOnClickListener(R.id.bt_phone_buy).addOnClickListener(R.id.bt_phone_sell);
    }
}
