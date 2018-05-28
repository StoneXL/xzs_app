package com.yxld.xzs.activity.workbench;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.datepicker.NumericWheelAdapter;
import com.yxld.xzs.view.datepicker.WheelView;
import com.zaaach.toprightmenu.TopRightMenu;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author xlei
 * @Date 2018/1/24.
 */

public class SuggestActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private TopRightMenu mTopRightMenu;
    private List<String> mTitle;
    private List<Fragment> mFragments;
    private ArrayList<String> xiangmuList;
    private List<Integer> xiangmuIdList;
    private List<com.zaaach.toprightmenu.MenuItem> menuItems;
    private WheelView wheelView;
    private NumericWheelAdapter xiangmuAdapter;
    SuggestFragment suggestFragment1;
    SuggestFragment suggestFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState!=null&&StringUitl.isNoEmpty(savedInstanceState.getString("uuid"))) {
            Contains.uuid = savedInstanceState.getString("uuid");
        }
        initData();
        getXiangMu();

    }

    private void initData() {
        menuItems = new ArrayList<>();
        mTitle = new ArrayList<>();
        mFragments = new ArrayList<>();
        xiangmuList = new ArrayList<>();
        xiangmuIdList = new ArrayList<>();
        mTitle.add("处理中");
        mTitle.add("处理完");
        for (String title : mTitle) {
            if ("处理完".equals(title)) {
                suggestFragment1 = SuggestFragment.newInstance(1);
                mFragments.add(suggestFragment1);
            } else {
                suggestFragment2 = SuggestFragment.newInstance(0);
                mFragments.add(suggestFragment2);
            }
        }
        SuggestFragmentAdapter adapter = new SuggestFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.suggest, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View tianjia = findViewById(R.id.details);
        switch (item.getItemId()) {
            case R.id.details:
//                if (menuItems.size() != 0) {
//                    mTopRightMenu = new TopRightMenu(SuggestActivity.this);
//                    mTopRightMenu
//                            .setHeight(480)     //默认高度480
//                            .setWidth(RecyclerView.LayoutParams.WRAP_CONTENT)      //默认宽度wrap_content
//                            .showIcon(false)     //显示菜单图标，默认为true
//                            .dimBackground(true)           //背景变暗，默认为true
//                            .needAnimationStyle(true)   //显示动画，默认为true
//                            .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
//                            .addMenuList(menuItems)
//                            .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
//                                @Override
//                                public void onMenuItemClick(int position) {
//                                    ToastUtil.showToast(SuggestActivity.this, menuItems.get(position).getText());
//                                    suggestFragment1.loadDataFromServer(false, menuItems.get(position).getText());
//                                    suggestFragment2.loadDataFromServer(false, menuItems.get(position).getText());
//
//                                }
//                            })
//                            .showAsDropDown(tianjia, 0, 0);
//                }
                showWheelView(tianjia);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getXiangMu() {
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).findXm(map)
                .subscribe(new Consumer<XiangMu>() {
                    @Override
                    public void accept(@NonNull XiangMu xiangMu) throws Exception {
                        if (xiangMu.status == 0) {
                            for (int i = 0; i < xiangMu.getData().size(); i++) {
                                if (xiangMu.getData().get(i) != null) {
                                    xiangmuList.add(xiangMu.getData().get(i).getXiangmuName());
                                    xiangmuIdList.add(xiangMu.getData().get(i).getXiangmuId());
                                    menuItems.add(new com.zaaach.toprightmenu.MenuItem(xiangMu.getData().get(i).getXiangmuName()));
                                }
                            }
                        } else {
                            onError(xiangMu.status, xiangMu.error);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    private void showWheelView(View showView) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(showView.getWindowToken(), 0);
        View view = LayoutInflater.from(this).inflate(R.layout.picker_xiangmu, null);
        AutoLinearLayout ll_content = (AutoLinearLayout) view.findViewById(R.id.ll_content);
        ll_content.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_manage_product_in));
        TextView submit = (TextView) ll_content.findViewById(R.id.submit);
        TextView tv_title = (TextView) ll_content.findViewById(R.id.tv_title);
        wheelView = (WheelView) ll_content.findViewById(R.id.xiangmu);
        xiangmuAdapter = new NumericWheelAdapter(this, 0, xiangmuList.size() - 1, "", xiangmuList);
        xiangmuAdapter.setTextSize(15);
        wheelView.setViewAdapter(xiangmuAdapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
                if (xiangmuList.size() > 0 && xiangmuIdList.size() > 0) {
                    suggestFragment1.loadDataFromServer(false, xiangmuIdList.get(wheelView.getCurrentItem()) + "");
                    suggestFragment2.loadDataFromServer(false, xiangmuIdList.get(wheelView.getCurrentItem()) + "");
                }
            }
        });

        new CustomPopWindow.PopupWindowBuilder(this)
                .setClippingEnable(false)
                .setFocusable(true)
                .setView(view)
                .setContenView(ll_content)
                .size(UIUtils.getDisplayWidth(this), UIUtils.getDisplayHeigh(this))
                .create()
                .showAtLocation(showView, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("uuid", Contains.uuid);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Contains.uuid = savedInstanceState.getString("uuid");
    }

    private class SuggestFragmentAdapter extends FragmentPagerAdapter {

        public SuggestFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mTitle.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle.get(position);
        }
    }
}
