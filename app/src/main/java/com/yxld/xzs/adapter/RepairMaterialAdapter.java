package com.yxld.xzs.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.MaterialBean;

/**
 *
 * Created by William on 2017/11/22.
 */

public class RepairMaterialAdapter extends BaseQuickAdapter<MaterialBean, BaseViewHolder> {

    private TextView tvName;
    private TextView tvUnit;
    private TextView tvNum;
    public RepairMaterialAdapter() {
        super(R.layout.item_repair_material1);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MaterialBean data) {
        tvName = baseViewHolder.getView(R.id.tv_goods_name);
        tvUnit = baseViewHolder.getView(R.id.tv_unit);
        tvNum = baseViewHolder.getView(R.id.tv_num);

        tvName.setText(data.getShangpinMing());
        tvUnit.setText(data.getGuige());
        if (TextUtils.isEmpty(data.getShijiShuliang())) {
            tvNum.setText("未备货");
        } else {
            tvNum.setText(data.getShijiShuliang());
        }
    }
}
