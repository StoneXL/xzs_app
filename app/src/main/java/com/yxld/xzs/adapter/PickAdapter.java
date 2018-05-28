package com.yxld.xzs.adapter;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.AppOrder;
import com.yxld.xzs.entity.AppSaleList;

import java.util.List;

/**
 * 作者：yishangfei on 2017/1/5 0005 14:28
 * 邮箱：yishangfei@foxmail.com
 */
public class PickAdapter extends BaseQuickAdapter<AppOrder, BaseViewHolder> {
    private List<AppSaleList> appSaleLists;
    public PickAdapter(List<AppOrder> list,List<AppSaleList> saleLists) {
        super(R.layout.pick_item_layout,list);
        appSaleLists = saleLists;
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, AppOrder item) {
        String name ="";//商品名称
        String amount="";//商品数量
        String total="";//商品总价
        for(int i = 0; i <appSaleLists.size(); i++){
            if(item.getDingdanBianhao().equals(appSaleLists.get(i).getSaleOrderBianhao())){
                if (appSaleLists.get(i).getSaleShangpName().length() >=14) {
                    name +=appSaleLists.get(i).getSaleShangpName().substring(0, 13)+"...\n\n";
                } else {
                    name +=appSaleLists.get(i).getSaleShangpName()+"\n\n";
                }
                amount+="×"+appSaleLists.get(i).getSaleNum()+"\n\n";
                total+="¥"+appSaleLists.get(i).getSaleTotalRmb()+"\n\n";
            }
        }
        String str1 = "送:　";
        String str2 =item.getDingdanDizhi();
        SpannableStringBuilder address = new SpannableStringBuilder(str1 + str2);
        address.setSpan(new ForegroundColorSpan(Color.parseColor("#73C676")),
                0, str1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        viewHolder.setText(R.id.pick_address,address)
                .setText(R.id.pick_product,name.substring(0,name.length()-2))
                .setText(R.id.pick_amount,amount.substring(0,amount.length()-2))
                .setText(R.id.pick_total,total.substring(0,total.length()-2))
                .setText(R.id.pick_money, "¥ " + item.getDingdanTotalRmb())
                .setText(R.id.pick_time, item.getDingdanXiadanTime())
                .addOnClickListener(R.id.pick_button);
    }
}
