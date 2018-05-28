package com.yxld.xzs.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.yxld.xzs.R;
import com.yxld.xzs.entity.DayEntity;


/**
 * @author Yuan.Y.Q
 * @Date 2017/7/26.
 */

public class DayView extends View {
    private Paint mNormalTextPaint;
    private Paint mTextCyclePaint;
    private Paint mDotPaint;
    private Paint mExceptionPaint;

    private boolean mIsToday;
    private boolean mIsClicked;
    private boolean mHasHistoryTask;
    private boolean mHasFutureTask;
    private boolean mIsPlaceHolder;
    private boolean mIsWeekend;
    private boolean mHasException;

    private int mWidth;
    private int mHeight;
    private int mScreenWidth;
    private float mDotSize;
    private float mTextSize;
    private String mText;
    private Rect mTextRect;

    public DayView(Context context) {
        this(context, null);
    }

    public DayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mTextRect = new Rect();
        mText = "";
        initPaint();
    }

    private void initPaint() {
        mNormalTextPaint = new Paint();
        mNormalTextPaint.setAntiAlias(true);
        mNormalTextPaint.setDither(true);

        mDotPaint = new Paint();
        mDotPaint.setAntiAlias(true);
        mDotPaint.setDither(true);

        mTextCyclePaint = new Paint();
        mTextCyclePaint.setAntiAlias(true);
        mTextCyclePaint.setDither(true);


        mExceptionPaint =new Paint();
        mExceptionPaint.setAntiAlias(true);
        mExceptionPaint.setDither(true);
        mExceptionPaint.setColor(getResources().getColor(R.color.color_ff5654));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float width;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mScreenWidth / 7;
        }
        mWidth = (int) width;

        float heightTemp;
        if (heightMode == MeasureSpec.EXACTLY) {
            heightTemp = heightSize;
        } else {
            heightTemp = mWidth*0.7f;
        }
        mHeight = (int) heightTemp;

        mDotSize = mWidth * 1.0f / 11;
        mTextSize = mHeight*5/12;

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        if(mIsPlaceHolder)
            return;
        super.setOnClickListener(l);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mIsPlaceHolder)
            return;
        mNormalTextPaint.setTextSize(mTextSize);
        mNormalTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);


        drawTextBgCycle(canvas);
        drawText(canvas);

    }

    private void drawTextBgCycle(Canvas canvas) {
        if (mIsToday) {
            mTextCyclePaint.setColor(getResources().getColor(R.color.color_ff5654));
            if (mIsClicked) {
                mTextCyclePaint.setStyle(Paint.Style.FILL);
            } else {
                mTextCyclePaint.setStrokeWidth(2);
                mTextCyclePaint.setStyle(Paint.Style.STROKE);
            }
            canvas.drawCircle(mWidth / 2, mHeight/2, getTextBgRadius(), mTextCyclePaint);

        } else if (mIsClicked) {
            mTextCyclePaint.setStyle(Paint.Style.FILL);
            mTextCyclePaint.setColor(getResources().getColor(R.color.color_dddddd));
            canvas.drawCircle(mWidth / 2, mHeight/2, getTextBgRadius(), mTextCyclePaint);
        }
    }

    private void drawDot(Canvas canvas,float halfX,float halfY) {
        float rightX = mWidth/2+halfX+halfY/2+mDotSize/2;

        if(mHasException){
            mDotPaint.setColor(getResources().getColor(R.color.color_ff5654));
            canvas.drawCircle(rightX, mHeight/2-halfY/2-mDotSize, mDotSize / 2, mDotPaint);
        } else if (mHasFutureTask) {
            mDotPaint.setColor(getResources().getColor(R.color.color_0079bf));
            canvas.drawCircle(rightX, mHeight/2-halfY/2-mDotSize, mDotSize / 2, mDotPaint);
        } else if (mHasHistoryTask) {
            mDotPaint.setColor(Color.GRAY);
            canvas.drawCircle(rightX, mHeight/2-halfY/2-mDotSize, mDotSize / 2, mDotPaint);
        }

    }


    private void drawText(Canvas canvas) {
        if(TextUtils.isEmpty(mText))
            return;

        if (mIsToday && mIsClicked) {
            mNormalTextPaint.setColor(Color.WHITE);
        }else if(mIsWeekend){
            mNormalTextPaint.setColor(getResources().getColor(R.color.color_0079bf));
        } else if(mIsToday){
            mNormalTextPaint.setColor(getResources().getColor(R.color.color_ff5654));
        }else {
            mNormalTextPaint.setColor(getResources().getColor(R.color.color_999999));
        }

        float halfY =(mTextRect.bottom-mTextRect.top)/2;
        float halfX = (mTextRect.right-mTextRect.left)/2;

        float bottomY = mHeight/2+halfY;
        float leftX = mWidth/2-halfX;
        if(mText.contains("1")){
            //修正带1的日期的显示
            if("11".equals(mText) || "1".equals(mText)){
                leftX -= 3 ;
            }else {
                leftX -= mTextRect.left;
            }
        }

        canvas.drawText(mText,leftX, bottomY, mNormalTextPaint);

        drawDot(canvas,halfX,halfY);
    }

    private float getTextBgRadius(){
        return mHeight/2 - mDotSize;
    }

    public void setCalendar(DayEntity dayEntity) {
        mText = dayEntity.day;
        mIsToday = dayEntity.isToday;
        mIsClicked = dayEntity.isSelected;
        mHasHistoryTask = dayEntity.hasHistoryTask;
        mHasFutureTask = dayEntity.hasFutureTask;
        mIsPlaceHolder = dayEntity.isPlaceHolder;
        mIsWeekend = dayEntity.isWeekend;
        mHasException = dayEntity.hasException;
        invalidate();
    }

    public boolean isPlaceHolder(){
        return mIsPlaceHolder;
    }
}
