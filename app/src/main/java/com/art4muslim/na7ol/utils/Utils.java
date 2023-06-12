package com.art4muslim.na7ol.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;

import com.art4muslim.na7ol.BuildConfig;

import java.util.Locale;


public class Utils {

    public static String getLang(Context context) {
        PrefrencesStorage storage = new PrefrencesStorage(context);
        String lang = null;
        String language = storage.getKey("language");
        if (language.equals("null")) {
            lang = "ar";
        } else {
            if (language.equals("ar")) {
                lang = "ar";
            } else if (language.equals("en")) {
                lang = "en";
            } else if (language.equals("zh")) {
                lang = "zh";
            }
        }
        return lang;
    }


    public static void chooseLang(Context context, String lang) {
        PrefrencesStorage storage = new PrefrencesStorage(context);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
//        storage.setFirstTimeLaunch(false);
        storage.storeKey("language", lang);

    }

    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        context.startActivity(intent);
    }


    public static void openWhats(Context context, String contact) {
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void shareApp(Context context) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "نحول");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }
}
