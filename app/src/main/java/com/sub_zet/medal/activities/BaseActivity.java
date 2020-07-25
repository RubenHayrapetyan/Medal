package com.sub_zet.medal.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.sub_zet.medal.R;
import com.sub_zet.medal.helpers.CheckInternet;
import com.sub_zet.medal.helpers.LanguageChooser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private boolean isHaveConnection;

    public static class ConnectionEvent {
        boolean isConnected;

        public ConnectionEvent(boolean isConnected) {
            this.isConnected = isConnected;
        }
    }

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageChooser.changeLanguage(this);
        setContentView(R.layout.no_internet_connection);

    }

    // kap chuni, vor es methody seriova, inqy ogtagorcvuma EventBusi koxmic
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionEvent(ConnectionEvent event) {
        isHaveConnection = event.isConnected;
        if (!event.isConnected) {
            showNoInternetDialog();
        } else {
            hideNoInternetDialog();
        }
    }

    private void showNoInternetDialog() {
        if (dialog == null) {
            dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.no_internet_connection);
        }
        dialog.show();

    }

    private void hideNoInternetDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void checkInternet(Context context) {
        CheckInternet serviceManager = new CheckInternet(context);
        isHaveConnection = serviceManager.isNetworkAvailable();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        checkInternet(this);
        if (!isHaveConnection) {
            showNoInternetDialog();
        }
        Log.i("baseActivLifes", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("baseActivLifes", "Resume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("baseActivLifes", "Stop");
        EventBus.getDefault().unregister(this);
        hideNoInternetDialog();
    }
}
