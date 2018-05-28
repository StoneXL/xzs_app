package com.yxld.xzs.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.AppPeisongorder;

import java.util.List;

/**
 * Created by yishangfei on 2016/10/21 0021.
 */

public class NightAdapter extends BaseQuickAdapter<AppPeisongorder,BaseViewHolder> {

    public NightAdapter(List<AppPeisongorder> data) {
        super(R.layout.activity_night_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AppPeisongorder appPeisongorder) {
                   baseViewHolder.setText(R.id.night_name,"配送人：" +appPeisongorder.getPeisongOrderPeisongName())
                           .setText(R.id.night_danhao,"订单号：" + appPeisongorder.getPeisongOrderOrderBianhao())
                           .setText(R.id.night_time,"抢单时间：" + appPeisongorder.getPeisongOrderKaishiTime());

    }
}

