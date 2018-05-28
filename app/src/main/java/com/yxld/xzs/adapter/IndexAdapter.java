package com.yxld.xzs.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.yxld.xzs.R;
import com.yxld.xzs.entity.IndexMessageBean;
import com.yxld.xzs.utils.SPUtil;

import java.util.List;

/**
 * @author xlei
 * @Date 2017/11/14.
 */

public class IndexAdapter extends BaseQuickAdapter<IndexMessageBean.DataBean, BaseViewHolder> {


    public IndexAdapter(@Nullable List<IndexMessageBean.DataBean> data) {
        super(R.layout.item_index, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, IndexMessageBean.DataBean baseEntity) {


        final TextView tv = viewHolder.getView(R.id.tv_total);
        final TextView tv1 = viewHolder.getView(R.id.tv_total_no);
        final TextView tvAddress = viewHolder.getView(R.id.tv_address);
        final TextView tvSongzhi = viewHolder.getView(R.id.tv_songzhi);
        if (baseEntity.getType() == 1) {
            viewHolder.setText(R.id.tv_time, baseEntity.getFukuanShijian())
                    .setText(R.id.tv_address, baseEntity.getShouhuoDizhi());
            Glide.with(mContext)
                    .load(R.mipmap.index_rob)
                    .into((ImageView) viewHolder.getView(R.id.iv_index_head));
            viewHolder.setText(R.id.tv_zhuangtai, "待抢单");
            if (baseEntity.getTotal() != 0 && baseEntity.getTotal() > 0) {
                tv.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.GONE);
                tv.setText(baseEntity.getTotal() + "");
                setAnimation(tv);
            } else {
                tv.clearAnimation();
            }
        } else if (baseEntity.getType() == 2) {
            viewHolder.setText(R.id.tv_time, baseEntity.getFukuanShijian())
                    .setText(R.id.tv_address, baseEntity.getShouhuoDizhi());
            Glide.with(mContext)
                    .load(R.mipmap.index_take)
                    .into((ImageView) viewHolder.getView(R.id.iv_index_head));
            viewHolder.setText(R.id.tv_zhuangtai, "待取货");
            if (baseEntity.getTotal() != 0 && baseEntity.getTotal() > 0) {
                tv.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.GONE);
                tv.setText(baseEntity.getTotal() + "");
                setAnimation(tv);
            } else {
                tv.clearAnimation();
            }
        } else if (baseEntity.getType() == 3) {
            viewHolder.setText(R.id.tv_time, baseEntity.getFukuanShijian())
                    .setText(R.id.tv_address, baseEntity.getShouhuoDizhi());
            Glide.with(mContext).load(R.mipmap.index_send)
                    .into((ImageView) viewHolder.getView(R.id.iv_index_head));
            viewHolder.setText(R.id.tv_zhuangtai, "待送达");
            if (baseEntity.getTotal() != 0 && baseEntity.getTotal() > 0) {
                tv.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.GONE);
                tv.setText(baseEntity.getTotal() + "");
                setAnimation(tv);
            } else {
                tv.clearAnimation();
            }
        } else if (baseEntity.getType() == 4) {
            Glide.with(mContext).load(R.mipmap.index_check)
                    .into((ImageView) viewHolder.getView(R.id.iv_index_head));
            viewHolder.setText(R.id.tv_time, baseEntity.getPaidanShijian());
            viewHolder.setText(R.id.tv_zhuangtai, "报修审核");
            tvSongzhi.setText("");
            tvAddress.setVisibility(View.VISIBLE);
            tvAddress.setText("你有新的报修待处理");
            SPUtil spUtil = new SPUtil(mContext);
            tv.setVisibility(View.INVISIBLE);
            if ((Integer) spUtil.get(SPUtil.KEY_BAOXIU, 0) == 0) {
                tv1.setVisibility(View.VISIBLE);
            } else {
                tv1.setVisibility(View.GONE);
            }
        } else if (baseEntity.getType() == 5) {
            viewHolder.setText(R.id.tv_time, baseEntity.getFukuanShijian());
            Glide.with(mContext).load(R.mipmap.index_xunjiand)
                    .into((ImageView) viewHolder.getView(R.id.iv_index_head));
            tvSongzhi.setText("");
            viewHolder.setText(R.id.tv_zhuangtai, "巡检任务");
            tv1.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
        } else if (baseEntity.getType() == 6) {
            viewHolder.setText(R.id.tv_time, baseEntity.getFukuanShijian())
                    .setText(R.id.tv_address, baseEntity.getShouhuoDizhi());
            Glide.with(mContext).load(R.mipmap.index_yejian)
                    .into((ImageView) viewHolder.getView(R.id.iv_index_head));
            viewHolder.setText(R.id.tv_zhuangtai, "夜间订单");
            if (baseEntity.getTotal() != 0 && baseEntity.getTotal() > 0) {
                tv.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.GONE);
                tv.setText(baseEntity.getTotal() + "");
                setAnimation(tv);
            } else {
                tv.clearAnimation();
            }
        }


    }

    private void setAnimation(TextView tv) {
        /** 设置缩放动画 */
        ScaleAnimation animation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);//设置动画持续时间
        animation.setRepeatCount(-1);//设置重复次数
        animation.setRepeatMode(Animation.REVERSE);//重复 缩小和放大效果
        tv.startAnimation(animation);
    }
}
