package com.huhaichao.framework.base;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class RxBaseActivity extends RxAppCompatActivity implements BaseView{
    /**
     * 绑定生命周期
     */
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.STOP);
    }
}
