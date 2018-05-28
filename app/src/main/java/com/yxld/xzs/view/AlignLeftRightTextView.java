package com.yxld.xzs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yxld.xzs.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * @author Yuan.Y.Q
 * @Date 2017/7/31.
 */

public class AlignLeftRightTextView extends AutoRelativeLayout {
//    private static final int DEFAULT_COLOR = 0x666666;
    private TextView tvLeft;
    private TextView tvRight;
    private TextView tvBagde;
    public AlignLeftRightTextView(Context context) {
        this(context,null);
    }

    public AlignLeftRightTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AlignLeftRightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int defaultColor = getResources().getColor(R.color.color_666666);
        init();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AlignLeftRightTextView);
        String leftText = ta.getString(R.styleable.AlignLeftRightTextView_left_text);
        String rightText = ta.getString(R.styleable.AlignLeftRightTextView_right_text);
        boolean showDash = ta.getBoolean(R.styleable.AlignLeftRightTextView_show_dash,true);
        int leftTextColor = ta.getColor(R.styleable.AlignLeftRightTextView_left_text_color,defaultColor);
        int rightTextColor = ta.getColor(R.styleable.AlignLeftRightTextView_right_text_color,defaultColor);
        boolean showBadge = ta.getBoolean(R.styleable.AlignLeftRightTextView_show_badge,false);
        float margin = ta.getDimension(R.styleable.AlignLeftRightTextView_margin_left_right,60);
        int leftTextSize = (int) ta.getDimension(R.styleable.AlignLeftRightTextView_left_text_size,44);
        int rightTextSize =(int) ta.getDimension(R.styleable.AlignLeftRightTextView_right_text_size,44);
        String badge = ta.getString(R.styleable.AlignLeftRightTextView_badge);
        ta.recycle();
        tvLeft.setText(leftText);
        tvRight.setText(rightText);
        tvLeft.setTextColor(leftTextColor);
        tvRight.setTextColor(rightTextColor);
        tvBagde.setText(badge);
        tvBagde.setVisibility(showBadge?VISIBLE:GONE);

        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX,leftTextSize);
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX,rightTextSize);

        AutoLinearLayout.LayoutParams leftParams = (AutoLinearLayout.LayoutParams) tvLeft.getLayoutParams();
        leftParams.leftMargin = (int) margin;
        tvLeft.setLayoutParams(leftParams);


        LayoutParams rightParams = (LayoutParams) tvRight .getLayoutParams();
        rightParams.rightMargin = (int) margin;
        tvRight.setLayoutParams(rightParams);
        if(showDash){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(getResources().getDrawable(R.drawable.bg_dash_3lines_gray));
            }else {
                setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dash_3lines_gray));
            }
        }

        AutoUtils.auto(tvLeft);
        AutoUtils.auto(tvRight);
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_align_left_right,this);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        tvRight = (TextView) view.findViewById(R.id.tv_right);
        tvBagde = (TextView) view.findViewById(R.id.tv_badge);
    }

    public void setLeftText(String text){
        tvLeft.setText(text);
    }
    public AlignLeftRightTextView setRightText(String text){
        tvRight.setText(text);
        return this;
    }
    public void setBadge(String text){
        if(tvBagde.getVisibility() != VISIBLE){
            tvBagde.setVisibility(VISIBLE);
        }
        tvBagde.setText(text);
    }

    public void setRightTextColor(int color){
        tvRight.setTextColor(color);
    }
    public void setMyBackgroundDrawable(Drawable noraml,Drawable exception,boolean isException){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(isException?exception:noraml);
        }else {
            setBackgroundDrawable(isException?exception:noraml);
        }
    }
}
