package com.art4muslim.na7ol.ui.settings;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.SplashActivity;
import com.art4muslim.na7ol.ui.notificationSettings.NotificationsSettings;
import com.art4muslim.na7ol.ui.pages.PagesActivity;
import com.art4muslim.na7ol.ui.technicalSupport.TechnicalSupportActivity;
import com.art4muslim.na7ol.utils.HelperClass;
import com.art4muslim.na7ol.utils.LocaleHelper;
import com.art4muslim.na7ol.utils.Utils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.liLang)
    LinearLayout liLang;
    @BindView(R.id.liNotifications)
    LinearLayout liNotifications;
    @BindView(R.id.liAbout)
    LinearLayout liAbout;
    @BindView(R.id.liTerms)
    LinearLayout liTerms;
    @BindView(R.id.liTec)
    LinearLayout liTec;
    private Intent intent;
    private Dialog d;
    private String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        Log.e("language", "" + Utils.getLang(this));
        Utils.chooseLang(this, Utils.getLang(this));

    }

    @OnClick({R.id.ivBack, R.id.liLang, R.id.liNotifications, R.id.liAbout, R.id.liTerms, R.id.liTec})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.liLang:
                showLanguageDialoge();
                break;
            case R.id.liNotifications:
                startActivity(new Intent(this, NotificationsSettings.class));
                break;
            case R.id.liAbout:
                intent = new Intent(this, PagesActivity.class);
                intent.putExtra("from", "about");
                startActivity(intent);
                break;
            case R.id.liTerms:
                intent = new Intent(this, PagesActivity.class);
                intent.putExtra("from", "terms");
                startActivity(intent);
                break;
            case R.id.liTec:
                startActivity(new Intent(this, TechnicalSupportActivity.class));
                break;
        }
    }


    private void showLanguageDialoge() {
        d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.language_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = d.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        TextView title = (TextView) d.findViewById(R.id.title);
        RadioButton arabic = (RadioButton) d.findViewById(R.id.arabic);
        RadioButton english = (RadioButton) d.findViewById(R.id.english);
        RadioButton chinese = (RadioButton) d.findViewById(R.id.chinese);
        Button done = (Button) d.findViewById(R.id.done);

        arabic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    locale = "ar";
                    Utils.chooseLang(SettingsActivity.this, "ar");


                }
            }
        });
        chinese.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    locale = "zh";
                    Utils.chooseLang(SettingsActivity.this, "zh");
                }
            }
        });

        english.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    locale = "en";
                    Utils.chooseLang(SettingsActivity.this, "en");
                }

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                LocaleHelper.setLocale(SettingsActivity.this, locale);
                Intent intent = new Intent(SettingsActivity.this, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
//                overridePendingTransition( 0, 0);
//                startActivity(getIntent());
//                overridePendingTransition( 0, 0);
//                d.dismiss();
            }
        });
        d.show();

    }
}