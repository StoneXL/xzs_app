package com.yxld.xzs.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.yxld.xzs.R;
import com.yxld.xzs.view.CustomPopWindow;



/**
 * 作者：hu on 2017/6/8
 * 邮箱：365941593@qq.com
 * 描述：
 */

/**
 * 公共的popwindow弹出类。所有的popwindow都可以封装在这个类里边
 */
public class PopWindowUtil {
    OnSubmitClickListener onSubmitClickListener;
    public static void  showFullScreenPopWindow(Activity activity, View showView, View backView, View contentView) {
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        contentView.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.pop_manage_product_in));
        new CustomPopWindow.PopupWindowBuilder(activity)
                .setView(backView)
                .setClippingEnable(false)
                .setContenView(contentView)
                .setFocusable(false)
                .size(UIUtils.getDisplayWidth(activity), UIUtils.getDisplayHeigh(activity))
                .create()
                .showAtLocation(showView, Gravity.NO_GRAVITY, 0, 0);
    }

    public static void showAddFangxingPop(Activity activity, View showView, View contentView) {
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        new CustomPopWindow.PopupWindowBuilder(activity)
                .setClippingEnable(false)
                .setFocusable(true)
                .setView(contentView)
                .setContenView(contentView)
                .setAnimationStyle(android.R.style.Animation_Dialog)
                .size(UIUtils.getDisplayWidth(activity), UIUtils.getDisplayHeigh(activity))
                .create()
                .showAtLocation(showView, Gravity.CENTER, 0, 0);
    }

    /**
     *
     * @param activity  上下文
     * @param showView  从activity中传进来的view,用于让popWindow附着的
     * @param maskView  门板view，阴影
     * @param contentView 内容显示view，打开关闭会有类似输入法弹窗的效果
     */
    public static void showPopWindow(Activity activity, View showView, View maskView, View contentView) {
        contentView.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.pop_manage_product_in));
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        new CustomPopWindow.PopupWindowBuilder(activity)
                .setView(maskView)
                .setClippingEnable(false)
                .setContenView(contentView)
                .setFocusable(false)
                .size(UIUtils.getDisplayWidth(activity), UIUtils.getDisplayHeigh(activity))
                .create()
                .showAtLocation(showView, Gravity.NO_GRAVITY, 0, 0);
    }




    public void setOnSubmitClickListener(OnSubmitClickListener onSubmitClickListener) {
        this.onSubmitClickListener = onSubmitClickListener;
    }
    public interface OnSubmitClickListener {
        void onSubmitClick(View v, String time);
    }
}
