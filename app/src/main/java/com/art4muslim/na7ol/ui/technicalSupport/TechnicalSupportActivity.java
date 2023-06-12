package com.art4muslim.na7ol.ui.technicalSupport;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TechnicalSupportActivity extends AppCompatActivity implements ContactUs_view {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etMessage)
    EditText etMessage;
    @BindView(R.id.btnSend)
    Button btnSend;
    @Inject
    ContactUsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_support);
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        presenter.setView(this);
    }

    @OnClick({R.id.ivBack, R.id.btnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnSend:
                if (etName.getText().toString().isEmpty()) {
                    etName.setError(getString(R.string.empty));
                } else if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError(getString(R.string.empty));
                } else if (!isValidEmail(etEmail.getText().toString())) {
                    Toast.makeText(this, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
                } else if (etMessage.getText().toString().isEmpty()) {
                    etMessage.setError(getString(R.string.empty));
                } else {
                    presenter.contactUs(this, etName.getText().toString(), etEmail.getText().toString(), etMessage.getText().toString());
                }
                break;
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void isMSGSend(boolean isMsgSent) {
        if (isMsgSent) {
            Toast.makeText(this, getString(R.string.messageSent), Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
        }
    }
}