package com.art4muslim.na7ol.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.art4muslim.na7ol.R;


public class MyAmimatorClass {

    public static void ShakeAnimator(Context context, View view) {
        final Animation animShake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(animShake);
    }
}
