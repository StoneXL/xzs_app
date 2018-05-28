package com.yxld.xzs.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.FangQu;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

/**
 * 作者：Android on 2017/9/11
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class FangquAdapter extends BaseQuickAdapter<FangQu.DataBean, BaseViewHolder> {


    public FangquAdapter(@Nullable List<FangQu.DataBean> data) {
        super(R.layout.fangqu_list_item_new, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, FangQu.DataBean dataBean) {
        baseViewHolder.addOnLongClickListener(R.id.relative_yes);
//        baseViewHolder.setText(R.id.shebei_bianhao, dataBean.getPaianShebei().getShebeiFangquBianhao())
//                .setText(R.id.shebei_zhuangtai, dataBean.getIsStudy() == 0? "已学习":"未学习");
        //通过学习状态分配不同布局
        if (dataBean.getIsStudy() == 0) {
//            //已学习
            AutoRelativeLayout layout = baseViewHolder.getView(R.id.relative_yes);
            layout.setVisibility(View.VISIBLE);
            AutoRelativeLayout layout1 = baseViewHolder.getView(R.id.relative_no);
            layout1.setVisibility(View.GONE);
            //设置鸣笛状态
            // Glide.with(mContext).load(R.drawable.fangqu_md_on).into((ImageView) baseViewHolder.getView(R.id.img_md));
            ImageView view = (ImageView) baseViewHolder.getView(R.id.img_md);
            if (dataBean.getPaianShebei().getShebeiMingliKaiguan().equals("1")) {
                view.setImageResource(R.drawable.fangqu_md_on);
            } else {
                view.setImageResource(R.drawable.fangqu_md_off);
            }
            //设置防区编号
            baseViewHolder.setText(R.id.tv_fangqu_bianhao, dataBean.getPaianShebei().getShebeiFangquBianhao());
            //设置防区类型
            switch (dataBean.getPaianShebei().getShebeiFangquLeixin()) {
                case "0":
                    baseViewHolder.setText(R.id.tv_fangqu_leixing, "普通防区");
                    break;
                case "1":
                    baseViewHolder.setText(R.id.tv_fangqu_leixing, "紧急防区");
                    break;
                case "2":
                    baseViewHolder.setText(R.id.tv_fangqu_leixing, "留守防区");
                    break;
            }
            //设置设备名称
            baseViewHolder.setText(R.id.tv_shebei_mingcheng, dataBean.getPaianShebei().getShebeiName());
            //设置防区类型
            //s设置修改按钮点击事件
            baseViewHolder.addOnClickListener(R.id.tv_xiugai);

        } else {
            //未学习
            //已学习
            AutoRelativeLayout layout = baseViewHolder.getView(R.id.relative_yes);
            layout.setVisibility(View.GONE);
            AutoRelativeLayout layout1 = baseViewHolder.getView(R.id.relative_no);
            layout1.setVisibility(View.VISIBLE);
            //设置防区编号
            baseViewHolder.setText(R.id.tv_fangqu_bianhao1, dataBean.getPaianShebei().getShebeiFangquBianhao());
            //s设置修改按钮点击事件
            baseViewHolder.addOnClickListener(R.id.tv_xuexi);
        }
    }
}
