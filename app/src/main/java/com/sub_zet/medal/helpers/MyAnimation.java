package com.sub_zet.medal.helpers;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MyAnimation {
    public void myAnimation(Context context, int animationFolder, View view){
        Animation animation;
        animation = AnimationUtils.loadAnimation(context, animationFolder);

        view.startAnimation(animation);
    }
}
