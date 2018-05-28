package com.yxld.xzs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.Host;

import java.util.List;

/**
 * 作者：Android on 2017/9/11
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class HostAdapter extends BaseQuickAdapter<Host.DataBean, BaseViewHolder> {

    public HostAdapter(@Nullable List<Host.DataBean> data) {
        super(R.layout.host_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Host.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_xiangmu, dataBean.getZhujiXiangmuName())
                .setText(R.id.tv_zhujiming, dataBean.getZhujiShebeiName())
                .setText(R.id.tv_mac, dataBean.getZhujiMac())
                .setText(R.id.tv_fangwu, dataBean.getZhujiXiangmuName() + dataBean.getZhujiLoudong() + "栋" + dataBean.getZhujiDanyuan() + "单元" + dataBean.getZhujiFanghao() + "号")
                .setText(R.id.tv_guanliyuan, dataBean.getZhujiHaoma())
                .setText(R.id.tv_beizhu,dataBean.getZhujiBeizhu());
        baseViewHolder.addOnClickListener(R.id.xiugai)
                .addOnClickListener(R.id.fangqu_liebiao);
    }
}
