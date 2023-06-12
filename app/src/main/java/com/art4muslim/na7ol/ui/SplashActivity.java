package com.art4muslim.na7ol.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.login.LoginActivity;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

public class SplashActivity extends AppCompatActivity {

    PrefrencesStorage storage;
    Runnable run;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storage = new PrefrencesStorage(this);
        startNewActivity();
    }

    private void startNewActivity() {
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else if (Utils.getLang(this).equals("zh")) {
            Utils.chooseLang(this, "zh");
        }
        run = new Runnable() {
            @Override
            public void run() {
                if ((!storage.isFirstTimeLogin())) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }

                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
            }
        }

        ;
        handler.postDelayed(run, 2000);
    }
}

