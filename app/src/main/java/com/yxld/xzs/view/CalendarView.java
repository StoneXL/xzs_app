package com.yxld.xzs.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yxld.xzs.entity.DayEntity;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/7/27.
 */

public class CalendarView extends LinearLayout {
    private WeekView mWeekView;
    private MonthView mMonthView;
    private float mDivideViewHeight;
    private boolean isFirst;
    public CalendarView(Context context) {
        this(context,null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        initWeekView();
        initMonthView();
        addView(mWeekView);
        addView(mMonthView);
    }

    private void addFootView() {
        View view = new View(getContext());
        int viewHeight = (int) (mDivideViewHeight-mDivideViewHeight/6)/2;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, viewHeight);
        view.setLayoutParams(params);
        addView(view);
    }

    private void initMonthView() {
        mMonthView = new MonthView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mMonthView.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDivideViewHeight =  getMeasuredWidth()/8;
        if(!isFirst){
            isFirst = true;
            addFootView();
        }
    }
    private void initWeekView() {
        mWeekView = new WeekView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mWeekView.setLayoutParams(params);
    }
    public void setCalendarData(List<DayEntity> dayEntities){
        mMonthView.setThisMonthData(dayEntities);
    }
    public void setOnDayClickListener(OnDayClickListener listener){
        mMonthView.setOnItemClickListener(listener);
    }

    public DayEntity getCurrentDayEntity(String day){
        return mMonthView.getDayEntityByDay(day);
    }

    public interface  OnDayClickListener{
        void onDayClick(View view,DayEntity entity);
    }
}
