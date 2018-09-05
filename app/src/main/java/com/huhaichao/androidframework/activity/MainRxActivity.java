package com.huhaichao.androidframework.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.huhaichao.androidframework.R;
import com.huhaichao.androidframework.base.BaseRxFragment;
import com.huhaichao.androidframework.fragment.UtilsRxFragment;
import com.huhaichao.androidframework.base.BaseRxActivity;
import com.huhaichao.androidframework.fragment.ViewRxFragment;
import com.huhaichao.androidframework.fragment.WidgetsRxFragment;
import com.huhaichao.framework.event.NetworkEvent;
import com.huhaichao.framework.event.UpdateEvent;
import com.huhaichao.framework.widgets.CustomDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainRxActivity extends BaseRxActivity {
    private static final String TAG = "MainRxActivity";

    @BindView(R.id.activity_main_bottom_navigation_bar)
    protected BottomNavigationBar activity_main_bottom_navigation_bar;
    @BindView(R.id.floatingActionButton)
    protected FloatingActionButton floatingActionButton;
    private TextBadgeItem messageBadgeItem;
    private ShapeBadgeItem shapeBadgeItem = new ShapeBadgeItem();
    private BottomNavigationItem bottomNavigationItem;
    private List<BaseRxFragment> fragmentList;

    public void initBottomNavigationBar() {
        shapeBadgeItem.setShape(ShapeBadgeItem.SHAPE_OVAL)
                .setShapeColorResource(R.color.white)
                .setGravity(Gravity.TOP | Gravity.END)
                .setHideOnSelect(true);
        activity_main_bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED);
        activity_main_bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        activity_main_bottom_navigation_bar.setInActiveColor(R.color.colorPrimary);
        activity_main_bottom_navigation_bar.setActiveColor(R.color.white);
        activity_main_bottom_navigation_bar.setBarBackgroundColor(R.color.colorAccent);
        messageBadgeItem = new TextBadgeItem();
        activity_main_bottom_navigation_bar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Utils").setBadgeItem(shapeBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Views"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Widgets"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Widgets"))
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

    private List<BaseRxFragment> getFragments() {
        List<BaseRxFragment> fragments = new ArrayList<>();
        fragments.add(UtilsRxFragment.newInstance());
        fragments.add(ViewRxFragment.newInstance());
        fragments.add(WidgetsRxFragment.newInstance());
        fragments.add(UtilsRxFragment.newInstance());
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
        requiresPermission();
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

    public void requiresPermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {
                        shouldRequest.again(true);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        Toast("授权成功");
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            // TODO: 2018/8/28 添加选择对话框
                            new CustomDialog.Builder(mBaseActivity)
//                                    .setTitle("权限申请")
                                    .setMessage("需要权限，是否去设置？", Gravity.CENTER)
                                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            PermissionUtils.launchAppDetailsSettings();
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .create()
                                    .show();
                        }
                    }
                }).request();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(UpdateEvent updateEvent) {
        shapeBadgeItem.hide();
        shapeBadgeItem.setHideOnSelect(false);
    }
}
