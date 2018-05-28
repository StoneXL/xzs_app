package com.yxld.xzs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxld.xzs.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/1.
 */

public class LeftImgTextView extends AutoLinearLayout {
    private static final String TAG = LeftImgTextView.class.toString();
    private TextView mRightTextView;
    private ImageView mLeftImageView;
    public LeftImgTextView(Context context) {
        this(context,null);
    }

    public LeftImgTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LeftImgTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.LeftImgTextView);

        String rightText = ta.getString(R.styleable.LeftImgTextView_right_text);
        int color = ta.getColor(R.styleable.LeftImgTextView_right_text_color, Color.WHITE);
        float textSize = ta.getDimension(R.styleable.LeftImgTextView_right_text_size, 48);
        Drawable leftDrawable = ta.getDrawable(R.styleable.LeftImgTextView_left_drawable);
        ta.recycle();

        mRightTextView.setText(rightText);
        mRightTextView.setTextColor(color);
        if(leftDrawable!=null){
            mLeftImageView.setVisibility(VISIBLE);
            mLeftImageView.setImageDrawable(leftDrawable);
        }
        mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        AutoUtils.auto(mRightTextView);
    }

    private void init() {
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.view_left_img_text_view,this);
        mRightTextView = (TextView) view.findViewById(R.id.tv_right);
        mLeftImageView = (ImageView) view.findViewById(R.id.iv_left);
    }

    public void setRightTextView(String text){
        mRightTextView.setText(text);
    }
}
