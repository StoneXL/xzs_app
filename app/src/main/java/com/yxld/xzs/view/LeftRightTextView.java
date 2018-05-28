package com.yxld.xzs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.w3c.dom.Text;

/**
 * Yuan.Y.Q
 * Date 2017/7/21.
 */

public class LeftRightTextView extends AutoLinearLayout {
    private static final int DEFAULT_COLOR = Color.parseColor("#909090");
    private TextView tvLeft;
    private TextView tvRight;
    public LeftRightTextView(Context context) {
        this(context, null);
    }

    public LeftRightTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftRightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LeftRightTextView);
        float leftTextSize = ta.getDimension(R.styleable.LeftRightTextView_left_text_size, 44);
        float rightTextSize = ta.getDimension(R.styleable.LeftRightTextView_right_text_size, 44);
        int leftTextColor = ta.getColor(R.styleable.LeftRightTextView_left_text_color, DEFAULT_COLOR);
        int rightTextColor = ta.getColor(R.styleable.LeftRightTextView_right_text_color, DEFAULT_COLOR);
        String leftText = ta.getString(R.styleable.LeftRightTextView_left_text);
        String rightText = ta.getString(R.styleable.LeftRightTextView_right_text);
        ta.recycle();

        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX,leftTextSize);
        tvLeft.setTextColor(leftTextColor);
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX,rightTextSize);
        tvRight.setTextColor(rightTextColor);
        tvLeft.setText(leftText);
        tvRight.setText(rightText);

        AutoUtils.auto(tvLeft);
        AutoUtils.auto(tvRight);
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.left_right_text_view,this);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        tvRight = (TextView) view.findViewById(R.id.tv_right);

    }

    public void setLeftText(String text){
        tvLeft.setText(text);
    }

    public void setRightText(String text){
        tvRight.setText(text);
    }
}
