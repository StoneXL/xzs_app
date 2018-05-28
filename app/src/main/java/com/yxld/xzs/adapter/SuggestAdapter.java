package com.yxld.xzs.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;

import com.yxld.xzs.entity.SuggestBean;
import com.yxld.xzs.http.api.API;
import com.yxld.xzs.utils.StringUitl;

import java.util.List;

/**
 * @author xlei
 * @Date 2018/1/24.
 */

public class SuggestAdapter extends BaseQuickAdapter<SuggestBean.RowBean, BaseViewHolder> {
    public SuggestAdapter(List<SuggestBean.RowBean> data) {
        super(R.layout.adapter_suggest, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, SuggestBean.RowBean suggestBean) {
        viewHolder.setText(R.id.tv_xm_name, suggestBean.getTousuXiangmu());
        viewHolder.setText(R.id.tv_zhuangtai, suggestBean.getTousuStatus());
        viewHolder.setText(R.id.tv_name, suggestBean.getTousuName());
        viewHolder.setText(R.id.tv_fanghao, suggestBean.getLoudong());
        viewHolder.setText(R.id.tv_phone, suggestBean.getTousuShouji());
        viewHolder.setText(R.id.tv_danghao, suggestBean.getTousuDanhao());
        viewHolder.setText(R.id.tv_time, suggestBean.getTousuTime());
        viewHolder.setText(R.id.tv_leixing, suggestBean.getTousuFanwei());
        viewHolder.setText(R.id.tv_neirong, suggestBean.getTousuNeirong());

        final ImageView imageView1 = viewHolder.getView(R.id.image1);
        final ImageView imageView2 = viewHolder.getView(R.id.image2);
        final ImageView imageView3 = viewHolder.getView(R.id.image3);
        if (StringUitl.isNoEmpty(suggestBean.getTousuTupianUrl())) {
            final String[] split = suggestBean.getTousuTupianUrl().split(";");
            if (split.length == 1 && split[0] != null) {
                Glide.with(mContext)
                        .load(API.PIC + split[0])
                        .into(imageView1);
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnImageViewClick != null) {
                            mOnImageViewClick.onImageViewClick(new ImageView[]{imageView1}, new String[]{
                                    API.PIC + split[0],}, 0);
                        }
                    }
                });
                //清空点击事件 防止数据错乱
                imageView2.setOnClickListener(null);
                imageView3.setOnClickListener(null);
                imageView2.setImageDrawable(null);
                imageView3.setImageDrawable(null);
            } else if (split.length == 2 && split[0] != null && split[1] != null) {
                Glide.with(mContext)
                        .load(API.PIC + split[0])
                        .into(imageView1);
                Glide.with(mContext)
                        .load(API.PIC + split[1])
                        .into(imageView2);
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnImageViewClick != null) {
                            mOnImageViewClick.onImageViewClick(new ImageView[]{imageView1, imageView2}, new String[]{
                                    API.PIC + split[0], API.PIC + split[1]}, 0);
                        }
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnImageViewClick != null) {
                            mOnImageViewClick.onImageViewClick(new ImageView[]{imageView1, imageView2}, new String[]{
                                    API.PIC + split[0], API.PIC + split[1]}, 1);
                        }
                    }
                });
                imageView3.setOnClickListener(null);
                imageView3.setImageDrawable(null);
            } else if (split.length == 3 && split[0] != null && split[1] != null && split[2] != null) {
                Glide.with(mContext)
                        .load(API.PIC + split[0])
                        .into(imageView1);
                Glide.with(mContext)
                        .load(API.PIC + split[1])
                        .into(imageView2);
                Glide.with(mContext)
                        .load(API.PIC + split[2])
                        .into(imageView3);
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnImageViewClick != null) {
                            mOnImageViewClick.onImageViewClick(new ImageView[]{imageView1, imageView2, imageView3}, new String[]{
                                    API.PIC + split[0], API.PIC + split[1], API.PIC + split[2]}, 0);
                        }
                    }
                });
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnImageViewClick != null) {
                            mOnImageViewClick.onImageViewClick(new ImageView[]{imageView1, imageView2, imageView3}, new String[]{
                                    API.PIC + split[0], API.PIC + split[1], API.PIC + split[2]}, 1);
                        }
                    }
                });
                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnImageViewClick != null) {
                            mOnImageViewClick.onImageViewClick(new ImageView[]{imageView1, imageView2, imageView3}, new String[]{
                                    API.PIC + split[0], API.PIC + split[1], API.PIC + split[2]}, 2);
                        }
                    }
                });
            } else {
                imageView1.setOnClickListener(null);
                imageView2.setOnClickListener(null);
                imageView3.setOnClickListener(null);

                imageView1.setImageDrawable(null);
                imageView2.setImageDrawable(null);
                imageView3.setImageDrawable(null);
            }
        } else {
            imageView1.setOnClickListener(null);
            imageView2.setOnClickListener(null);
            imageView3.setOnClickListener(null);

            imageView1.setImageDrawable(null);
            imageView2.setImageDrawable(null);
            imageView3.setImageDrawable(null);
        }


    }

    //imageview的点击事件接口

    private OnImageViewClick mOnImageViewClick;

    public void setOnImageViewClick(OnImageViewClick onImageViewClick) {
        mOnImageViewClick = onImageViewClick;
    }

    public interface OnImageViewClick {
        void onImageViewClick(ImageView[] imageViews, String[] imageUrls, int position);

    }
}
