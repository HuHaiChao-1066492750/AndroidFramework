package com.huhaichao.androidframework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSONObject;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.huhaichao.androidframework.base.BaseActivity;
import com.huhaichao.androidframework.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.activity_main_fragment_container)
    protected FrameLayout activity_main_fragment_container;

    @BindView(R.id.activity_main_bottom_navigation_bar)
    protected BottomNavigationBar activity_main_bottom_navigation_bar;

    private TextBadgeItem messageBadgeItem;
    private BottomNavigationItem bottomNavigationItem;
    private List<BaseFragment> fragmentList;
    private boolean isExit = false;//退出状态

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData() {
        fragmentList = getFragments();
        initBottomNavigationBar();
        replaceFragment(0);
    }

    public void initBottomNavigationBar() {
        activity_main_bottom_navigation_bar.setMode(BottomNavigationBar.MODE_DEFAULT);
        activity_main_bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        activity_main_bottom_navigation_bar.setInActiveColor(R.color.white);
        messageBadgeItem = new TextBadgeItem();
        activity_main_bottom_navigation_bar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "你好1"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "你好2"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "你好3"))
                .setFirstSelectedPosition(0)
                .initialise();
        activity_main_bottom_navigation_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                replaceFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {
                if (fragmentList != null) {
                    if (position < fragmentList.size()) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        Fragment fragment = fragmentList.get(position);
                        ft.remove(fragment);
                        ft.commitAllowingStateLoss();
                    }
                }
            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private List<BaseFragment> getFragments() {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(TaskFragment.newInstance());
        fragments.add(TaskFragment.newInstance());
        fragments.add(TaskFragment.newInstance());
        return fragments;
    }


    private void replaceFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = fragmentList.get(position);
        if (fragment.isAdded()) {
            transaction.replace(R.id.activity_main_fragment_container, fragment);
        } else {
            transaction.add(R.id.activity_main_fragment_container, fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onFailureCallback(int requestCode, Throwable e) {

    }

    @Override
    public void onSuccessCallback(int requestCode, JSONObject jsonObject) {

    }
}
