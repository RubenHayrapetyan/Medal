package com.sub_zet.medal.helpers;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanguageChooser {

    public static void changeLanguage(Context context){
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(MySavedData.loadLanguage()));
        res.updateConfiguration(conf, dm);
    }
}
