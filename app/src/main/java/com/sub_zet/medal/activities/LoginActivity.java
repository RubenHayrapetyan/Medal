package com.sub_zet.medal.activities;

import android.os.Bundle;

import com.sub_zet.medal.R;
import com.sub_zet.medal.fragments.login.LoginFragment;
import com.sub_zet.medal.helpers.MyFragmentTransaction;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyFragmentTransaction.addFragment(getSupportFragmentManager(), new LoginFragment(), R.id.login_main_container);
    }
}
