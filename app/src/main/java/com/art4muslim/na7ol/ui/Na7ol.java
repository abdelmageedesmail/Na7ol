package com.art4muslim.na7ol.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.art4muslim.na7ol.dagger.component.ApplicationComponent;
//import com.art4muslim.na7ol.dagger.component.DaggerApplicationComponent;
import com.art4muslim.na7ol.dagger.component.DaggerApplicationComponent;
import com.art4muslim.na7ol.dagger.modules.ApplicationModule;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import java.util.Locale;


public class Na7ol extends Application {
    private ApplicationComponent applicationComponent;
    public static Application appCompatActivity;

    Locale locale;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        chooseLang("ar");
//        newConfig.setLocale(locale);
        Log.e("language", "" + Utils.getLang(this));
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();

    }

    private void initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

