package com.yxld.xzs.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.XunJianDianEntity;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/4.
 */

public class PatrolFlowAdapter extends BaseQuickAdapter<XunJianDianEntity, BaseViewHolder> {
    public PatrolFlowAdapter(@Nullable List<XunJianDianEntity> data) {
        super(R.layout.item_patrol_flow, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, XunJianDianEntity entity) {
        baseViewHolder.setText(R.id.tv_serial_num, baseViewHolder.getLayoutPosition() + 1 + "");
        if (baseViewHolder.getLayoutPosition() == getData().size() - 1) {
            baseViewHolder.getView(R.id.line).setVisibility(View.INVISIBLE);
        }
        //开始巡检
        boolean hasChecked = entity.hasChecked == 1 || entity.hasSaveData == 1;
        baseViewHolder.getView(R.id.line_up).setSelected(hasChecked);
        baseViewHolder.getView(R.id.line_dowm).setSelected(hasChecked);
        baseViewHolder.getView(R.id.tv_serial_num).setSelected(hasChecked);
        baseViewHolder.setTextColor(R.id.tv_check_status, mContext.getResources().getColor(hasChecked ? R.color.color_007bc2 : R.color.color_ff5654))
                .setTextColor(R.id.tv_serial_num, mContext.getResources().getColor(hasChecked ? R.color.white : R.color.color_666666))
                .setText(R.id.tv_check_status, entity.hasSaveData == 1 ? "已保存" : hasChecked ? "已打卡" : "未打卡").setText(R.id.tv_address,entity.dianDizhi);

        ImageView ivEye = baseViewHolder.getView(R.id.iv_eye);
        ivEye.setColorFilter(mContext.getResources().getColor(hasChecked ? R.color.color_007bc2 : R.color.color_ff5654));
    }
}
