package com.yxld.xzs.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.YiJieSuanBean;
import com.yxld.xzs.utils.StringUitl;


/**
 * @author xlei
 * @Date 2017/11/22.
 */

public class JieSuanAdapter extends BaseQuickAdapter<YiJieSuanBean.DataBean, BaseViewHolder> {


    public JieSuanAdapter() {
        super(R.layout.item_yijiesuan);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, YiJieSuanBean.DataBean dataBean) {
        viewHolder.setText(R.id.tv_address, dataBean.getShouhuodizhi());
        viewHolder.setText(R.id.tv_order, dataBean.getDingdanBianhao());
        viewHolder.setText(R.id.tv_time, dataBean.getShouhuoshijian());
        if (!StringUitl.isEmpty(dataBean.getJiesuanshijian())) {
            viewHolder.setText(R.id.tv_time2, dataBean.getJiesuanshijian().substring(5, 10));
        }
        viewHolder.setText(R.id.tv_money, "¥ " + dataBean.getJiesuanjine());
    }
}
