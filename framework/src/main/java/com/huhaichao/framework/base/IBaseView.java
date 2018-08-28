package com.huhaichao.framework.base;

import android.os.Bundle;
import android.view.View;

/**
 * Created by HuHaiChao on 2018/8/7.
 */

interface IBaseView extends View.OnClickListener{

    /**
     * 初始化数据
     */
    void initData(final Bundle bundle);

    /**
     * 绑定布局
     */
    int bindLayout();

    /**
     * 初始化View
     */
    void initView(final Bundle savedInstanceState, final View contentView);

    /**
     * 业务操作
     */
    void doBusiness();

    /**
     * 视图点击事件
     */
    void onWidgetClick(final View view);
}
