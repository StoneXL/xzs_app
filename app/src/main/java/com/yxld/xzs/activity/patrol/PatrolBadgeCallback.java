package com.yxld.xzs.activity.patrol;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/1.
 * 显示红点数量的回调
 */

public interface PatrolBadgeCallback {
    void onThisTimeTaskBadgeListener(int count);
    void onPlanTaskBadgeListener(int count);
    void onHistoryTaskBadgeListener(int count);
    void onMsgManagerBadgeListener(int count);
    void onTeamManagerBadgeListener(int count);
}
