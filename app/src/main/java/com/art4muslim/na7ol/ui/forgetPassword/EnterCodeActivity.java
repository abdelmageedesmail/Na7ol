package com.art4muslim.na7ol.ui.forgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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

public class EnterCodeActivity extends AppCompatActivity implements ForgetPasswordView {

    @BindView(R.id.ivCancel)
    ImageView ivCancel;
    @BindView(R.id.etCode1)
    EditText etCode1;
    @BindView(R.id.etCode2)
    EditText etCode2;
    @BindView(R.id.etCode3)
    EditText etCode3;
    @BindView(R.id.etCode4)
    EditText etCode4;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.btnPasswordRecovery)
    Button btnPasswordRecovery;
    @BindView(R.id.tvCountDown)
    TextView tvCountDown;
    @BindView(R.id.btnResendCode)
    Button btnResendCode;
    @Inject
    ForgetPasswordPresenter presenter;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
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
        if (getIntent().getExtras().getString("from").equals("register")) {
            btnPasswordRecovery.setText(getString(R.string.confirm));
        } else {
            btnPasswordRecovery.setText(getString(R.string.recoverPassword));
        }
        requestFocus();
        showCountDown();
    }


    private void showCountDown() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText(String.valueOf(counter) + " : 00");
                counter++;
            }

            public void onFinish() {
                tvCountDown.setText(getString(R.string.havnotCode));
                btnResendCode.setEnabled(true);
            }
        }.start();
    }


    private void requestFocus() {
        etCode1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (etCode1.getText().toString().length() == 1) {
                    etCode2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        etCode2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (etCode2.getText().toString().length() == 1) {
                    etCode3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        etCode3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (etCode3.getText().toString().length() == 1) {
                    etCode4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


    }

    @OnClick(R.id.btnPasswordRecovery)
    public void onViewClicked() {
        if (getIntent().getExtras().getString("from").equals("register")) {
            if (etCode1.getText().toString().isEmpty()) {
                etCode1.setError(getString(R.string.empty));
            } else if (etCode2.getText().toString().isEmpty()) {
                etCode2.setError(getString(R.string.empty));
            } else if (etCode3.getText().toString().isEmpty()) {
                etCode3.setError(getString(R.string.empty));
            } else if (etCode4.getText().toString().isEmpty()) {
                etCode4.setError(getString(R.string.empty));
            } else {
                String s = etCode1.getText().toString() + etCode2.getText().toString() + etCode3.getText().toString() + etCode4.getText().toString();
                presenter.activateAccount(this, s);
            }
        } else {
            if (etCode1.getText().toString().isEmpty()) {
                etCode1.setError(getString(R.string.empty));
            } else if (etCode2.getText().toString().isEmpty()) {
                etCode2.setError(getString(R.string.empty));
            } else if (etCode3.getText().toString().isEmpty()) {
                etCode3.setError(getString(R.string.empty));
            } else if (etCode4.getText().toString().isEmpty()) {
                etCode4.setError(getString(R.string.empty));
            } else {
                String s = etCode1.getText().toString() + etCode2.getText().toString() + etCode3.getText().toString() + etCode4.getText().toString();
                presenter.checkCodeExist(this, s);
            }
        }
    }

    @OnClick(R.id.btnResendCode)
    void setBtnResendCode() {
        presenter.resendCode(this, getIntent().getExtras().getString("mobile"));
    }

    @OnClick(R.id.ivCancel)
    void cancle() {
        onBackPressed();
    }

    @Override
    public void getCode(String code) {

    }

    @Override
    public void isReset(boolean b) {

    }

    @Override
    public void getCountries(List<CountriesResponse.Return> returns) {

    }

    @Override
    public void codeResent(boolean codeResent) {
        if (codeResent) {
            btnResendCode.setEnabled(false);
            showCountDown();
        }
    }


    @Override
    public void codeCorrect(boolean b) {
        if (b) {
            if (getIntent().getExtras().getString("from").equals("register")) {
                Toast.makeText(this, getString(R.string.accountActivated), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, ResetNewPasswordActivity.class);
                intent.putExtra("code", etCode.getText().toString());
                startActivity(intent);
            }


        } else {
            Toast.makeText(this, getString(R.string.errorCode), Toast.LENGTH_SHORT).show();
        }
    }
}
