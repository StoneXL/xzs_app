package com.yxld.xzs.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;

/**
 * Created by William on 2017/11/22.
 */

public class RepairFlowAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private ImageView lineup;
    private ImageView linedown;
    private TextView tvNameHalf;
    private TextView tvName;
    private TextView tvState;
    private TextView tvOpinion;

    public RepairFlowAdapter() {
        super(R.layout.item_repair_material2);


    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        lineup = baseViewHolder.getView(R.id.iv_line_up);
        linedown = baseViewHolder.getView(R.id.iv_line_down);
        tvNameHalf = baseViewHolder.getView(R.id.tv_name_half);
        tvName = baseViewHolder.getView(R.id.tv_name);
        tvState = baseViewHolder.getView(R.id.tv_state);
        tvOpinion = baseViewHolder.getView(R.id.tv_opinion);

        //控制虚线显示
        int adapterPosition = baseViewHolder.getAdapterPosition();
        Log.e("wh", "adapterPosition" + adapterPosition);
        if (adapterPosition == 0) {
            lineup.setVisibility(View.INVISIBLE);
        } else {
            lineup.setVisibility(View.VISIBLE);
        }
        if (adapterPosition == getData().size() - 1) {
            linedown.setVisibility(View.INVISIBLE);
        } else {
            linedown.setVisibility(View.VISIBLE);
        }

        /*  //统计汉字的个数
        int count =0;
        String Reg="^[\u4e00-\u9fa5]{1}$";  //汉字的正规表达式
        for(int i=0;i<s.length();i++){
            String b=Character.toString(s.charAt(i));
            if(b.matches(Reg))
                count++;
        }*/

        //控制建议显示
        if (adapterPosition % 2 == 0) {
            tvOpinion.setVisibility(View.VISIBLE);
        } else {
            tvOpinion.setVisibility(View.GONE);
        }

    }
}
