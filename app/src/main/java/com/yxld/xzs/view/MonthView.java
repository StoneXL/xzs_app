package com.yxld.xzs.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.DayEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/7/27.
 */

public class MonthView extends RecyclerView {
    private CalendarAdapter mAdapter;
    private CalendarView.OnDayClickListener mListener;

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new GridLayoutManager(getContext(), 7, LinearLayoutManager.VERTICAL, false));
        setNestedScrollingEnabled(false);
        init();
    }

    private void init() {
        List<DayEntity> dayEntityList = getDays();
        List<DayEntity> dayEntities = handlerDayEntities(dayEntityList);
        mAdapter = new CalendarAdapter(dayEntities);
        setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.notifyViewClicked(position);
                if (mListener != null) {
                    mListener.onDayClick(view, mAdapter.getData().get(position));
                }
            }
        });
    }

    public void setThisMonthData(List<DayEntity> entities) {
        for (DayEntity entity : mAdapter.getData()) {
            for (DayEntity day : entities) {
                if (day.day.equals(entity.day)) {
                    entity.hasException = day.hasException;
                    entity.hasHistoryTask = day.hasHistoryTask;
                    entity.hasFutureTask = day.hasFutureTask;
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public DayEntity getDayEntityByDay(String day) {
        if (mAdapter == null || mAdapter.getData().size() ==0)
            return null;
        for (DayEntity entity : mAdapter.getData()){
            if(entity.isPlaceHolder)
                continue;
            if(!TextUtils.isEmpty(day) &&day.equals(entity.day)){
                return entity;
            }
        }

        return null;
    }

    public void setOnItemClickListener(CalendarView.OnDayClickListener listener) {
        mListener = listener;
    }

    private List<DayEntity> handlerDayEntities(List<DayEntity> entities) {
        List<DayEntity> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, -today + 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        //添加占位
        for (int i = 1; i < dayOfWeek; i++) {
            DayEntity entity = new DayEntity();
            entity.isPlaceHolder = true;
            list.add(entity);
        }

        list.addAll(entities);
        return list;
    }

    private List<DayEntity> getDays() {
        final List<DayEntity> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.MONTH, 1);
        int dayNew= calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DAY_OF_MONTH, -dayNew
        );

        while (calendar.get(Calendar.DAY_OF_MONTH) > 1) {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DayEntity entity = new DayEntity();
            entity.day = day + "";
            entity.isToday = day == today;
            entity.isSelected = entity.isToday;
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            entity.isWeekend = dayOfWeek == 7 || dayOfWeek == 1;
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            list.add(entity);
        }
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        DayEntity entity = new DayEntity();
        entity.day = "1";
        entity.isToday = 1 == today;
        entity.isSelected = entity.isToday;
        entity.isWeekend = dayOfWeek == 7 || dayOfWeek == 1;
        list.add(entity);
        Collections.reverse(list);
        return list;
    }

    private static class CalendarAdapter extends BaseQuickAdapter<DayEntity, BaseViewHolder> {

        public CalendarAdapter(@Nullable List<DayEntity> data) {
            super(R.layout.item_month, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DayEntity item) {
            DayView view = helper.getView(R.id.dayView);
            view.setCalendar(item);
            helper.addOnClickListener(R.id.dayView);
        }

        public void notifyViewClicked(int position) {
            DayEntity entity = mData.get(position);
            if (entity.isPlaceHolder)
                return;
            for (int i = 0; i < mData.size(); i++) {
                DayEntity v = mData.get(i);
                if (i == position && v.isSelected) {
                    return;
                } else if (i == position) {
                    v.isSelected = true;
                    notifyItemChanged(i);
                } else if (v.isSelected) {
                    v.isSelected = false;
                    notifyItemChanged(i);
                }
            }
        }
    }


}
