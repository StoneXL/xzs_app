package com.yxld.xzs.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.TestBean;
import com.yxld.xzs.utils.ToastUtil;
import com.zhy.autolayout.AutoFrameLayout;

/**
 * @author xlei
 * @Date 2017/11/27.
 */

public class ApplyMaterialsAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    private onItemClick mOnItemClick;

    public void setOnItemClick(onItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public ApplyMaterialsAdapter() {
        super(R.layout.item_apply_materials);
    }

    @Override
    protected void convert(final BaseViewHolder viewHolder, final TestBean testBean) {
        viewHolder.setText(R.id.tv_name, testBean.getAddress())
                .setText(R.id.tv_count, testBean.getTime())
                .setText(R.id.tv_danjia, testBean.getZhangtai())
                .setText(R.id.tv_current_count, testBean.getImgid() + "");
        final TextView tvCurrentCount = viewHolder.getView(R.id.tv_current_count);
        final TextView count = viewHolder.getView(R.id.tv_count);
        AutoFrameLayout tvReduse = viewHolder.getView(R.id.iv_jian);
        AutoFrameLayout tvAdd = viewHolder.getView(R.id.iv_jia);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.setOnItemClick(0,viewHolder.getAdapterPosition(), tvCurrentCount, count);
                }
            }
        });
        tvReduse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.setOnItemClick(1, viewHolder.getAdapterPosition(),tvCurrentCount, count);
                }
            }
        });
    }

    public interface onItemClick {
        void setOnItemClick(int type,int position, TextView current, TextView count);
    }
}
