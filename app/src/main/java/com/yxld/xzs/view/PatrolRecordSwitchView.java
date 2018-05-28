package com.yxld.xzs.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yxld.xzs.R;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/4.
 */

public class PatrolRecordSwitchView extends AutoLinearLayout {
    private TextView tvPositive;
    private TextView tvNegative;
    public PatrolRecordSwitchView(Context context) {
        this(context,null);
    }

    public PatrolRecordSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PatrolRecordSwitchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_patrol_record_swich,this);
        tvPositive = (TextView) view.findViewById(R.id.tv_positive);
        tvNegative = (TextView) view.findViewById(R.id.tv_negative);
    }

    public void setPositiveSelected(boolean flag){
        tvPositive.setSelected(flag);
        tvNegative.setSelected(!flag);

        tvPositive.setTextColor(flag? Color.WHITE:getResources().getColor(R.color.color_666666));
        tvNegative.setTextColor(!flag?Color.WHITE:getResources().getColor(R.color.color_666666));
    }

    public void setNotClicked(){
        tvPositive.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvNegative.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

   public void setLeftText(String text,String defaultAnswer){
       tvPositive.setText(text);
       setPositiveSelected(defaultAnswer.equals(text));
   }
   public void setRightText(String text,String defaultAnswer){
       tvNegative.setText(text);
       setPositiveSelected(!defaultAnswer.equals(text));
   }

   private OnItemClickListener mListener;
   public void setOnItemClickListener(OnItemClickListener click){
       mListener = click;
       tvPositive.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               setPositiveSelected(!tvPositive.isSelected());
               if(mListener!=null){
                   mListener.onPositiveClick(tvPositive.getText().toString());
               }
           }
       });

       tvNegative.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               setPositiveSelected(tvNegative.isSelected());
               if(mListener!=null){
                   mListener.onNegativeClick(tvNegative.getText().toString());
               }

           }
       });
   }

   public interface OnItemClickListener{
       void onPositiveClick(String text);
       void onNegativeClick(String text);
   }
}
