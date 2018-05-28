package com.yxld.xzs.activity.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.yxld.xzs.R;
import com.yxld.xzs.adapter.MyFragmentPagerAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.view.SimpleBadgeTabCustomView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 巡更管理界面
 */
public class PatrolManagerActivity extends BaseActivity implements PatrolBadgeCallback {
    public static final String KEY_PAGE = "key_page";
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    List<Fragment> mFragment;
    private int mNeed2Page;
    private boolean mNeedTurn2Page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_manager);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(KEY_PAGE)) {

            mNeed2Page = bundle.getInt(KEY_PAGE);
            mNeedTurn2Page = true;
        }
        initData();
        init();
    }

    private void initData() {
        mFragment = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("本次任务");
        titles.add("计划任务");
        titles.add("历史任务");
//        titles.add("消息管理");
        titles.add("班组管理");

        mFragment.add(ThisTimeTaskFragment.newInstance());
        mFragment.add(new PlanTaskFragment());
        mFragment.add(new HistoryTaskFragment());
//        mFragment.add(new MsgManagerFragment());
        mFragment.add(new TeamManagerFragment());

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragment, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager, false);
        for (int i = 0; i < titles.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab == null) {
                continue;
            }
            SimpleBadgeTabCustomView customView = new SimpleBadgeTabCustomView(PatrolManagerActivity.this).setTitle(titles.get(i), tabLayout).setBadgeCount(0);
            tab.setCustomView(customView);
        }

        if (mNeedTurn2Page)
            viewPager.setCurrentItem(mNeed2Page, true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null || intent.getExtras() == null)
            return;
        Bundle bundle = intent.getExtras();
        if (!bundle.containsKey(KEY_PAGE))
            return;
        mNeed2Page = bundle.getInt(KEY_PAGE);
        viewPager.setCurrentItem(mNeed2Page, true);
    }

    private void init() {
    }

    /**
     * 下面5个重写方法来改变红点的数量
     * @param count
     */
    @Override
    public void onThisTimeTaskBadgeListener(int count) {
        onBadgeListener(0, count);
    }

    @Override
    public void onPlanTaskBadgeListener(int count) {
        onBadgeListener(1, count);
    }

    @Override
    public void onHistoryTaskBadgeListener(int count) {
        onBadgeListener(2, count);
    }

    @Override
    public void onMsgManagerBadgeListener(int count) {
        onBadgeListener(3, count);
    }

    @Override
    public void onTeamManagerBadgeListener(int count) {
        onBadgeListener(4, count);
    }

    private void onBadgeListener(int position, int count) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        if (tab == null) {
            Toast.makeText(this, "未知力量导致了错误~", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleBadgeTabCustomView view = (SimpleBadgeTabCustomView) tab.getCustomView();
        if (view == null) {
            Toast.makeText(this, "神秘力量导致了错误~", Toast.LENGTH_SHORT).show();
            return;
        }
        view.setBadgeCount(count);
    }

    @Override
    protected void onDestroy() {
        if(Contains.jilu!=null){
            Contains.jilu = null;
        }
        super.onDestroy();
    }
}
