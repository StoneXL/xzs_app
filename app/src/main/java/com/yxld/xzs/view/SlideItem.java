package com.yxld.xzs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.hik.CASClient.CASClient;
import com.socks.library.KLog;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Yuan.Y.Q
 * Date 2017/7/21.
 */

public class SlideItem extends AutoHorizontalScrollView {
    private int mScreenWidth, mInVisibleContentWidth;
    private boolean mIsFirstMeasure,mIsOpen;
    private ViewGroup mVisibleContent, mInVisibleContent;

    public SlideItem(Context context) {
        this(context, null);
    }

    public SlideItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;//px

        mIsFirstMeasure = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIsFirstMeasure) {
            mIsFirstMeasure = false;
            AutoLinearLayout autoLinearLayout = (AutoLinearLayout) getChildAt(0);
            mVisibleContent = (ViewGroup) autoLinearLayout.getChildAt(0);
            mInVisibleContent = (ViewGroup) autoLinearLayout.getChildAt(1);

            mVisibleContent.getLayoutParams().width = mScreenWidth;
            mInVisibleContentWidth = mInVisibleContent.getLayoutParams().width;

            KLog.i("item","menuWidth:"+mInVisibleContentWidth);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            scrollTo(0, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                KLog.i("item","getScrollX:"+getScrollX());
                if (getScrollX() >= mInVisibleContentWidth / 2) {
                    open();
                } else {
                    close();
                }
                return true;
        }

        return super.onTouchEvent(ev);
    }

    private void open() {
        KLog.i("item","open");
            smoothScrollTo(mInVisibleContentWidth, 0);
            mIsOpen = true;
    }

    private void close() {
        KLog.i("item","close");
            smoothScrollTo(0, 0);
            mIsOpen = false;
    }

    public void exportClose(){
        if(mIsOpen){
            close();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        mInVisibleContent.setTranslationX(-getScrollX() / 2);
    }

}
