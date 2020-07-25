package com.sub_zet.medal.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.sub_zet.medal.R;

import kotlin.Lazy;

import static android.content.Context.MODE_PRIVATE;
import static org.koin.java.KoinJavaComponent.inject;

public class MySavedData {

    private static Lazy<Context> mContextLazy = inject(Context.class);

    private static SharedPreferences sPref = mContextLazy.getValue().getSharedPreferences("data", MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sPref.edit();

    private final static String LOGIN_STATE = "login_state";
    private final static String NICK_NAME = "nickname";
    private final static String LANGUAGE = "language";
    private final static String PLATFORM = "platform";

    public static void setLoginState(boolean loginState) {
        editor.putBoolean(LOGIN_STATE, loginState).apply();
    }

    public static boolean getLoginState() {
        return sPref.getBoolean(LOGIN_STATE, false);
    }

    public static void saveGameNickname(String nickname){
        editor.putString(NICK_NAME, nickname).apply();
    }

    public static String getNickName(){
        return sPref.getString(NICK_NAME, mContextLazy.getValue().getString(R.string.enter_your_game_nickname));
    }

    public static void saveLanguage(String language){
        editor.putString(LANGUAGE, language).apply();
    }

    public static String loadLanguage(){
        return sPref.getString(LANGUAGE, "ru");
    }

    public static void saveChoosenPlatform(String platformName){
        editor.putString(PLATFORM, platformName).apply();
    }

    public static String getChoosenPlatform(){
        return sPref.getString(PLATFORM, "mobile");
    }

}
