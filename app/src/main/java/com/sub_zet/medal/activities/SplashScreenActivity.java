package com.sub_zet.medal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sub_zet.medal.R;
import com.sub_zet.medal.helpers.MySavedData;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (MySavedData.getLoginState()){
            goToMenuActivity();
        }else {
            goToLoginActivity();
        }
    }

    private void goToMenuActivity() {
        new Handler().postDelayed(()->{
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        },1500);
    }

    private void goToLoginActivity() {
        new Handler().postDelayed(()->{
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        },1500);
    }
}
