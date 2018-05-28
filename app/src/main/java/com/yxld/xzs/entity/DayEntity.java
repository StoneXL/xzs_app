package com.yxld.xzs.entity;

/**
 * @author Yuan.Y.Q
 * @Date 2017/7/26.
 */

public class DayEntity {
    public String day;
    public boolean isToday;
    public boolean hasFutureTask;
    public boolean hasHistoryTask;
    public boolean isPlaceHolder;
    public boolean isWeekend;
    public boolean hasException;
    public boolean isSelected;


    public DayEntity() {
    }

    public DayEntity(String day) {
        this.day = day;
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public DayEntity setWeekend(boolean weekend) {
        isWeekend = weekend;
        return this;
    }

    public DayEntity setException(boolean exception){
        hasException = exception;
        return this;
    }

    public DayEntity setDay(String day) {
        this.day = day;
        return this;
    }

    public void setPlan(int code){
        if(code == 1){
            hasHistoryTask = true;
        }else if(code ==2){
            hasException = true;
        }else if(code ==3){
            hasFutureTask = true;
        }
    }

    public DayEntity setToday(boolean today) {
        isToday = today;
        return this;
    }

    public DayEntity setHasFutureTask(boolean hasFutureTask) {
        this.hasFutureTask = hasFutureTask;
        return this;
    }

    public DayEntity setHasHistoryTask(boolean hasHistoryTask) {
        this.hasHistoryTask = hasHistoryTask;
        return this;
    }

    public DayEntity setPlaceHolder(boolean placeHolder) {
        isPlaceHolder = placeHolder;
        return this;
    }

}
