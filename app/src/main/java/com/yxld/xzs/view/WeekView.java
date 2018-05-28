package com.yxld.xzs.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.DayEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Yuan.Y.Q
 * @Date 2017/7/27.
 */

public class WeekView extends View {
    private static final List<DayEntity> WEEKS= new ArrayList<>();
    static {
        WEEKS.add(new DayEntity("日").setWeekend(true));
        WEEKS.add(new DayEntity("一"));
        WEEKS.add(new DayEntity("二"));
        WEEKS.add(new DayEntity("三"));
        WEEKS.add(new DayEntity("四"));
        WEEKS.add(new DayEntity("五"));
        WEEKS.add(new DayEntity("六").setWeekend(true));

    }

    private int mOnePartitionWidth;
    private Paint mPaint;
    private float mTextYCentre;
    private float mTextXStart;
    private int mHeight;
    public WeekView(Context context) {
        this(context,null);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        int mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mOnePartitionWidth = mScreenWidth /7;
        mHeight = (int) (mOnePartitionWidth*0.6f);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(mHeight/2);

        String text = WEEKS.get(WEEKS.size()-1).day;

        Rect rect = new Rect();
        mPaint.getTextBounds(text,0,text.length(),rect);
        mTextYCentre = mHeight/2+(rect.bottom - rect.top)/2;
        mTextXStart = (rect.right-rect.left)/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i =0 ;i < WEEKS.size() ;i++){
            DayEntity entity = WEEKS.get(i);
            if(entity.isWeekend){
                mPaint.setColor(getResources().getColor(R.color.color_0079bf));
            }else {
                mPaint.setColor(getResources().getColor(R.color.color_86898B));
            }
            canvas.drawText(entity.day,mOnePartitionWidth*(i+1)-mOnePartitionWidth/2-mTextXStart,mTextYCentre,mPaint);
        }
    }
}
