package com.yxld.xzs.adapter;

import android.graphics.PorterDuff;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.MainMenuEntity;

import java.util.List;

/**
 * Created by YuanYQ on 2017/7/21.
 */

public class MainMenuAdapter extends BaseQuickAdapter<MainMenuEntity,BaseViewHolder> {


    public MainMenuAdapter(List<MainMenuEntity> data) {
        super(R.layout.item_main_menu,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainMenuEntity item) {
        ImageView iv = helper.getView(R.id.menu_icon);
        iv.setColorFilter(mContext.getResources().getColor(R.color.color_3f3f3f), PorterDuff.Mode.SRC_IN);
        Glide.with(mContext)
                .load(item.iconResId)
                .into(iv);

        helper.setText(R.id.tv_title,item.title);
    }
}
