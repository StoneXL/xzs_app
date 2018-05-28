package com.yxld.xzs.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.XiangMu;

import java.util.List;

/**
 * 作者：Android on 2017/9/13
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class ProjectListAdapter extends BaseQuickAdapter<XiangMu.DataBean, BaseViewHolder> {

    public ProjectListAdapter(@Nullable List<XiangMu.DataBean> data) {
        super(R.layout.project_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, XiangMu.DataBean dataBean) {
        baseViewHolder.setText(R.id.xiangmu, dataBean.getXiangmuName());
    }
}
