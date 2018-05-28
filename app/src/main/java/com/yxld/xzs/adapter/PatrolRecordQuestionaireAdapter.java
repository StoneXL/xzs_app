package com.yxld.xzs.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.XunJianXiangEntity;
import com.yxld.xzs.view.PatrolRecordSwitchView;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/4.
 */

public class PatrolRecordQuestionaireAdapter extends BaseQuickAdapter<XunJianXiangEntity,BaseViewHolder> {
    private boolean mJustLook;
    public PatrolRecordQuestionaireAdapter(@Nullable List<XunJianXiangEntity> data,boolean justLook) {
        super(R.layout.item_patrol_record,data);
        mJustLook = justLook;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final XunJianXiangEntity entity) {
        if(entity.xunjianxiangLeixin == 1){
            //逻辑类型
            baseViewHolder.setVisible(R.id.tv_iv_container,false)
                    .setVisible(R.id.switch_view,true)
                    .setText(R.id.tv_star,"*").setText(R.id.tv_title,baseViewHolder.getLayoutPosition()+1+"、"+entity.xunjianxiangName);
            PatrolRecordSwitchView switchView = baseViewHolder.getView(R.id.switch_view);
            switchView.setLeftText(entity.xunjianxiangLuojiName, TextUtils.isEmpty(entity.xunjianxiangDaAn)?entity.xunjianxiangZhengchangzhi:entity.xunjianxiangDaAn);
            switchView.setRightText(entity.xunjianxiangLuojiName2,TextUtils.isEmpty(entity.xunjianxiangDaAn)?entity.xunjianxiangZhengchangzhi:entity.xunjianxiangDaAn);
            if(!mJustLook){
                switchView.setOnItemClickListener(new PatrolRecordSwitchView.OnItemClickListener() {
                    @Override
                    public void onPositiveClick(String text) {
                        entity.xunjianxiangDaAn = text;
                    }

                    @Override
                    public void onNegativeClick(String text) {
                        entity.xunjianxiangDaAn = text;
                    }
                });
            }
        }else if(entity.xunjianxiangLeixin == 2){
            //数值型
            baseViewHolder.setVisible(R.id.tv_iv_container,true)
                    .setVisible(R.id.switch_view,false)
                    .setText(R.id.tv_star,"*").setText(R.id.tv_title,baseViewHolder.getLayoutPosition()+1+"、"+entity.xunjianxiangName);
            if(entity.isReplied ==1){
                baseViewHolder.setText(R.id.tv_desc,entity.xunjianxiangDaAn);
            }

        }


    }
}
