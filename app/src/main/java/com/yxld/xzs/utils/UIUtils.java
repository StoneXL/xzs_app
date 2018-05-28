package com.yxld.xzs.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yxld.xzs.R;
import com.yxld.xzs.entity.MainMenuEntity;

import java.util.ArrayList;
import java.util.List;


public class UIUtils {

    public static int getDisplayWidth(Activity context) {
        WindowManager wm = context.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getDisplayHeigh(Activity context) {
        WindowManager wm = context.getWindowManager();
        int heigh = wm.getDefaultDisplay().getHeight();
        return heigh;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static List<MainMenuEntity> getMainMenu() {
        List<MainMenuEntity> entities = new ArrayList<>();
        entities.add(new MainMenuEntity("我的收入", R.mipmap.nav_income));
        entities.add(new MainMenuEntity("工作汇总", R.mipmap.nav_summary));
        entities.add(new MainMenuEntity("特殊门禁", R.mipmap.nav_quard));
        entities.add(new MainMenuEntity("通知管理", R.mipmap.nav_night));
        entities.add(new MainMenuEntity("报修审批", R.mipmap.nav_approval));
        entities.add(new MainMenuEntity("报修申请", R.mipmap.icon_repair));
        entities.add(new MainMenuEntity("报废申请", R.mipmap.icon_scrap));
        entities.add(new MainMenuEntity("申购列表", R.mipmap.icon_shengou));
        entities.add(new MainMenuEntity("电子券审批", R.mipmap.icon_shengou));
        entities.add(new MainMenuEntity("区域监控", R.mipmap.icon_gonggong));
        entities.add(new MainMenuEntity("巡检点录入", R.mipmap.icon_electricity));
        entities.add(new MainMenuEntity("巡检系统", R.mipmap.icon_electricity));
        entities.add(new MainMenuEntity("联系客服", R.mipmap.nav_customer));
        entities.add(new MainMenuEntity("岗位监控", R.mipmap.icon_jiankong));
        return entities;
    }

    public static List<MainMenuEntity> getHomeMenu() {
        List<MainMenuEntity> entities = new ArrayList<>();
        entities.add(new MainMenuEntity("我的收入", R.mipmap.icon_per_income));
        entities.add(new MainMenuEntity("联系客服", R.mipmap.icon_per_service));
        entities.add(new MainMenuEntity("通用设置", R.mipmap.icon_per_set));
        return entities;

    }

    public static View getRecyclerHeaderMargin(Context context) {
        return getRecyclerHeaderMargin(context, 32);
    }

    public static View getRecyclerHeaderMargin(Context context, int height) {
        View view = new View(context);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(layoutParams);
        return view;
    }

    public static void configSwipeRefreshLayoutColors(SwipeRefreshLayout layout) {
        layout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

}
