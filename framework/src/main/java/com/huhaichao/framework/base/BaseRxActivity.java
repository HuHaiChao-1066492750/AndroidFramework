package com.huhaichao.framework.base;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author HuHaiChao
 */
public abstract class BaseRxActivity extends RxAppCompatActivity implements IBaseView {
    /**
     * 绑定生命周期
     */
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.STOP);
    }
}
