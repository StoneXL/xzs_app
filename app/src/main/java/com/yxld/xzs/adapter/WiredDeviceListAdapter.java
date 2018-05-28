package com.yxld.xzs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jwkj.shakmanger.LocalDevice;
import com.yxld.xzs.R;

import java.util.List;

/**
 * Created by 89876 on 2017/5/6 0006.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 * <p>
 */
public class WiredDeviceListAdapter extends BaseQuickAdapter<LocalDevice, BaseViewHolder> {
    public WiredDeviceListAdapter(@Nullable List<LocalDevice> data) {
        super(R.layout.item_wired_device_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalDevice item) {
        helper.setText(R.id.devices_id, item.getId());
        helper.setText(R.id.devices_ip, item.getIP());
    }

}

