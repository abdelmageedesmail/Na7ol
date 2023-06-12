package com.art4muslim.na7ol.ui.forgetPassword;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.login.LoginActivity;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetNewPasswordActivity extends AppCompatActivity implements ForgetPasswordView {

    @BindView(R.id.ivCancel)
    ImageView ivCancel;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etRepeatNewPassword)
    EditText etRepeatNewPassword;
    @BindView(R.id.btnSendCode)
    Button btnSendCode;
    @Inject
    ForgetPasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_new_password);
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

    @OnClick({R.id.ivCancel, R.id.btnSendCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivCancel:
                onBackPressed();
                break;
            case R.id.btnSendCode:
                if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError(getString(R.string.empty));
                } else if (etRepeatNewPassword.getText().toString().isEmpty()) {
                    etRepeatNewPassword.setError(getString(R.string.empty));
                } else if (!etPassword.getText().toString().equals(etRepeatNewPassword.getText().toString())) {
                    Toast.makeText(this, getString(R.string.passwordNotMAtches), Toast.LENGTH_SHORT).show();
                } else {
                    presenter.resetPassword(this, getIntent().getExtras().getString("code"), etPassword.getText().toString());
                }

                break;
        }
    }


    private void showPopupAdded() {
        final Dialog dialogAdded = new Dialog(this);
        dialogAdded.setContentView(R.layout.popup_success_reset);
        dialogAdded.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnManage = dialogAdded.findViewById(R.id.btnManage);
        TextView tvContent = dialogAdded.findViewById(R.id.tvContent);
        btnManage.setText(getString(R.string.login));
        tvContent.setText(getString(R.string.passwordReset));
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAdded.dismiss();
                startActivity(new Intent(ResetNewPasswordActivity.this, LoginActivity.class));
                finish();
            }
        });
        dialogAdded.show();
    }


    @Override
    public void getCode(String code) {

    }

    @Override
    public void isReset(boolean b) {
        if (b) {
            showPopupAdded();
        }
    }

    @Override
    public void getCountries(List<CountriesResponse.Return> returns) {

    }

    @Override
    public void codeCorrect(boolean b) {

    }

    @Override
    public void codeResent(boolean codeResent) {

    }
}
