package com.yxld.xzs.adapter;

import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.PanDianYiChangBean;

import java.util.List;

/**
 * Created by William on 2018/5/28.
 */

public class PanDianYiChangAdapter extends BaseQuickAdapter<PanDianYiChangBean, BaseViewHolder> {

    private TextView tvLeibie;
    private TextView tvTiaoXingMa;
    private TextView tvMingCheng;
    private TextView tvGuiGe;
    private TextView tvDanWei;
    private TextView tvShuLiang;
    private TextView tvShuLiangAfter;
    private Button btChange;


    public PanDianYiChangAdapter(List<PanDianYiChangBean> data) {
        super(R.layout.item_pandian_yichang, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PanDianYiChangBean panDianYiChangBean) {
        tvLeibie = baseViewHolder.getView(R.id.tv_kucun_leibie);
        tvTiaoXingMa = baseViewHolder.getView(R.id.tv_tiaoxingma);
        tvMingCheng = baseViewHolder.getView(R.id.tv_mingcheng);
        tvGuiGe = baseViewHolder.getView(R.id.tv_guige);
        tvDanWei = baseViewHolder.getView(R.id.tv_danwei);
        tvShuLiang = baseViewHolder.getView(R.id.tv_shuliang);
        tvShuLiangAfter = baseViewHolder.getView(R.id.tv_shuliang_after);
        btChange = baseViewHolder.getView(R.id.bt_change);


        baseViewHolder.addOnClickListener(R.id.bt_change);
    }
}
