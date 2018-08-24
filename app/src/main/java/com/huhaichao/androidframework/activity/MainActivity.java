package com.huhaichao.androidframework.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.fragment.UtilsFragment;
import com.huhaichao.androidframework.base.BaseActivity;
import com.huhaichao.androidframework.base.BaseFragment;
import com.huhaichao.androidframework.fragment.ViewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.activity_main_bottom_navigation_bar)
    protected BottomNavigationBar activity_main_bottom_navigation_bar;

    private TextBadgeItem messageBadgeItem;
    private BottomNavigationItem bottomNavigationItem;
    private List<BaseFragment> fragmentList;

    public void initBottomNavigationBar() {
        activity_main_bottom_navigation_bar.setMode(BottomNavigationBar.MODE_DEFAULT);
        activity_main_bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        activity_main_bottom_navigation_bar.setInActiveColor(R.color.white);
        messageBadgeItem = new TextBadgeItem();
        activity_main_bottom_navigation_bar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Utils"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Views"))
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
        fragments.add(UtilsFragment.newInstance());
        fragments.add(ViewFragment.newInstance());
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

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        fragmentList = getFragments();
        initBottomNavigationBar();
        replaceFragment(0);
    }

    @Override
    public void doBusiness() {


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case 0:
                break;
            default:
                break;
        }
    }
}
