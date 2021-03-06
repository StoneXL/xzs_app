package com.yxld.xzs.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.ApproveBean;

import java.util.List;

/**
 * Created by William on 2017/11/16.
 */

public class ApproveAdapter extends BaseMultiItemQuickAdapter<ApproveBean, BaseViewHolder> {

    private TextView tvType;
    private TextView approveName;
    private TextView approveTime;

    public ApproveAdapter(@Nullable List<ApproveBean> data) {
        super(data);
        addItemType(ApproveBean.CLICK_ITEM_VIEW, R.layout.item_approve1);
        addItemType(ApproveBean.CLICK_ITEM_CHILD_VIEW, R.layout.item_approve2);
        addItemType(ApproveBean.LONG_CLICK_ITEM_VIEW, R.layout.item_approve3);
        addItemType(ApproveBean.LONG_CLICK_ITEM_CHILD_VIEW, R.layout.item_approve4);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ApproveBean approveBean) {
        switch (baseViewHolder.getItemViewType()) {
            case ApproveBean.CLICK_ITEM_VIEW: {
                tvType = baseViewHolder.getView(R.id.tv_type);
//                tvType.setText("未出库");
                tvType.setText(approveBean.getShenpiLeixin());
                TextView approveNum = baseViewHolder.getView(R.id.tv_approve_num);
                approveTime = baseViewHolder.getView(R.id.tv_approve_time);
                approveName = baseViewHolder.getView(R.id.tv_approve_name);
                TextView approveLocation = baseViewHolder.getView(R.id.tv_approve_location);

                approveNum.setText(approveBean.getBaoxiuDanhao());
                approveTime.setText(approveBean.getTime());
                approveName.setText(approveBean.getShenqingren());
                approveLocation.setText(approveBean.getXiangmuName());

                break;
            }
            case ApproveBean.CLICK_ITEM_CHILD_VIEW: {
                tvType = baseViewHolder.getView(R.id.tv_type);
//                tvType.setText("待部门审批");
                tvType.setText(approveBean.getShenpiLeixin());
                TextView programName = baseViewHolder.getView(R.id.tv_program_name);
                approveTime = baseViewHolder.getView(R.id.tv_approve_time);
                approveName = baseViewHolder.getView(R.id.tv_approve_name);

                programName.setText(approveBean.getXiangmuName());
                approveTime.setText(approveBean.getTime());
                approveName.setText(approveBean.getShenqingren());

                break;
            }
            case ApproveBean.LONG_CLICK_ITEM_VIEW: {
                tvType = baseViewHolder.getView(R.id.tv_type);
//                tvType.setText("未处理");
                tvType.setText(approveBean.getShenpiLeixin());
                TextView typeProgram = baseViewHolder.getView(R.id.tv_type_program);
                TextView tvProgram = baseViewHolder.getView(R.id.tv_program);
                approveTime = baseViewHolder.getView(R.id.tv_approve_time);
                approveName = baseViewHolder.getView(R.id.tv_approve_name);

                typeProgram.setText(approveBean.getShengouLeixinName());
                tvProgram.setText(approveBean.getXiangmuName());
                approveTime.setText(approveBean.getTime());
                approveName.setText(approveBean.getShenqingren());
                break;
            }
            case ApproveBean.LONG_CLICK_ITEM_CHILD_VIEW: {
                // TODO: 2017/11/25 没有此类型数据

                tvType = baseViewHolder.getView(R.id.tv_type);
                tvType.setText("待指派");

                break;
            }

        }
    }
}
