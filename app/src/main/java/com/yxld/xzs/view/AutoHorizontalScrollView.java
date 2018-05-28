package com.yxld.xzs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Yuan.Y.Q
 * Date 2017/7/24.
 */

public class AutoHorizontalScrollView extends HorizontalScrollView {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);
    public AutoHorizontalScrollView(Context context) {
        super(context);
    }

    public AutoHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoFrameLayout.LayoutParams(getContext(), attrs);
    }
}
