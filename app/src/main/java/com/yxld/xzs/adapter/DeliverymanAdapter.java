package com.yxld.xzs.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.SenderBean;

/**
 * Created by William on 2017/11/30.
 */

public class DeliverymanAdapter extends BaseQuickAdapter<SenderBean, BaseViewHolder> {

    private TextView tvName;
    private ImageView imageView;

    public DeliverymanAdapter() {
        super(R.layout.item_deliveryman);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SenderBean senderBean) {
        tvName = baseViewHolder.getView(R.id.tv_name);
        imageView = baseViewHolder.getView(R.id.iv_isChoose);

        tvName.setText(senderBean.getCxwyPeisongName());
        if (senderBean.isChecked()) {
            imageView.setImageResource(R.mipmap.choose);
        } else {
            imageView.setImageResource(R.mipmap.nochoose);
        }


    }
}
