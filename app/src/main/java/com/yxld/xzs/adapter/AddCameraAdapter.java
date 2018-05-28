package com.yxld.xzs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.yxld.xzs.R;
import com.yxld.xzs.entity.CameraDetail;

/**
 * @author xlei
 * @Date 2018/1/9.
 */

public class AddCameraAdapter extends BaseQuickAdapter<CameraDetail.DataBean, BaseViewHolder> {
    public AddCameraAdapter(@Nullable List<CameraDetail.DataBean> data) {
        super(R.layout.item_add_camera, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CameraDetail.DataBean dataBean) {
        viewHolder.setText(R.id.tv_name, dataBean.getSb_name());
        viewHolder.setText(R.id.tv_xuliehao, dataBean.getSb_ipc_id());
        viewHolder.setText(R.id.tv_phone, dataBean.getPhone());
        viewHolder.setText(R.id.tv_address, dataBean.getXiangmu() + dataBean.getLoudong() + "栋" + dataBean.getDanyuan() + "单元" + dataBean.getFanghao() + "房号");
    }
}
