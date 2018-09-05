package com.huhaichao.framework.base;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Created by Administrator on 2018/1/16.
 * @author HuHaiChao
 */

public abstract class BaseRxFragment extends RxFragment implements IBaseView {
    /**
     * 绑定生命周期
     */
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(FragmentEvent.STOP);
    }
}
