package com.sub_zet.medal.helpers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sub_zet.medal.activities.BaseActivity;

import org.greenrobot.eventbus.EventBus;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (checkInternet(context)) {
            EventBus.getDefault().post(new BaseActivity.ConnectionEvent(true));
        } else {
            EventBus.getDefault().post(new BaseActivity.ConnectionEvent(false));
        }
    }

    boolean checkInternet(Context context) {
        CheckInternet serviceManager = new CheckInternet(context);
        return serviceManager.isNetworkAvailable();
    }

}
