package com.yxld.xzs.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.PanDianBean;

import java.util.List;

/**
 * Created by William on 2018/5/28.
 */

public class WeiPanDianAdapter extends BaseQuickAdapter<PanDianBean, BaseViewHolder> {

    private TextView tvLeibie;
    private TextView tvTiaoXingMa;
    private TextView tvMingCheng;
    private TextView tvGuiGe;
    private TextView tvDanWei;
    private TextView tvKunCunShiJian;
    private TextView tvShuLiang;
    private TextView tvDanJia;
    private TextView tvGuoQiShiJian;


    public WeiPanDianAdapter(List<PanDianBean> data) {
        super(R.layout.item_weipandian, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PanDianBean panDianBean) {
        tvLeibie = baseViewHolder.getView(R.id.tv_kucun_leibie);
        tvTiaoXingMa = baseViewHolder.getView(R.id.tv_tiaoxingma);
        tvMingCheng = baseViewHolder.getView(R.id.tv_mingcheng);
        tvGuiGe = baseViewHolder.getView(R.id.tv_guige);
        tvDanWei = baseViewHolder.getView(R.id.tv_danwei);
        tvKunCunShiJian = baseViewHolder.getView(R.id.tv_kucun_shijian);
        tvShuLiang = baseViewHolder.getView(R.id.tv_shuliang);
        tvDanJia = baseViewHolder.getView(R.id.tv_danjia);
        tvGuoQiShiJian = baseViewHolder.getView(R.id.tv_guoqi_shijian);

        //设置数据
        tvLeibie.setText(panDianBean.getKucunFenlei()+"");
        tvTiaoXingMa.setText(panDianBean.getWuziBianhao());
        tvMingCheng.setText(panDianBean.getWuziMingcheng());
        tvGuiGe.setText(panDianBean.getWuziGuige());
        tvDanWei.setText(panDianBean.getWuziDanwei());
        tvKunCunShiJian.setText(panDianBean.getDetailShengchanRiqi());
        tvShuLiang.setText(panDianBean.getDetailShuliang()+"");
        tvDanJia.setText(panDianBean.getWuziDanjia()+"");
        tvGuoQiShiJian.setText(panDianBean.getDetailGuoqiRiqi());

    }
}
