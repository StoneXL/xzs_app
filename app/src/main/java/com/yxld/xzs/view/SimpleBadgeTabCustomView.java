package com.yxld.xzs.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yxld.xzs.R;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/1.
 */

public class SimpleBadgeTabCustomView extends AutoRelativeLayout {
    private TextView tvTitle;
    private TextView tvBadge;
    public SimpleBadgeTabCustomView(Context context) {
        this(context,null);
    }

    public SimpleBadgeTabCustomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleBadgeTabCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.view_patrol_tab,this);
        tvTitle  = (TextView) view.findViewById(R.id.tv_title);
        tvBadge = (TextView) view.findViewById(R.id.tv_badge);
    }
    public SimpleBadgeTabCustomView setTitle(String title,TabLayout tabLayout){
        tvTitle.setText(title);
        tvTitle.setTextColor(tabLayout.getTabTextColors());
        return this;
    }
    public SimpleBadgeTabCustomView setBadgeCount(int count){
        if(count <1){
            tvBadge.setVisibility(INVISIBLE);
            return this;
        }
        tvBadge.setText(String.valueOf(count));
        tvBadge.setVisibility(VISIBLE);
        return this;
    }
}
