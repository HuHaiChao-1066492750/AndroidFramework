package com.huhaichao.framework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.huhaichao.framework.event.NetworkEvent;

import org.greenrobot.eventbus.EventBus;

public class NetworkChangeReceiver extends BroadcastReceiver {
    public NetworkChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().post(new NetworkEvent());
    }
}
