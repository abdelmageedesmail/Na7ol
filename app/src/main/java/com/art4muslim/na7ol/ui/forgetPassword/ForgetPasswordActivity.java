package com.art4muslim.na7ol.ui.forgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.add_trip.CountriesSpinnerAdapter;
import com.art4muslim.na7ol.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends AppCompatActivity implements ForgetPasswordView {

    @BindView(R.id.ivCancel)
    ImageView ivCancel;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.btnSendCode)
    Button btnSendCode;
    @Inject
    ForgetPasswordPresenter presenter;
    private String country_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
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
        presenter.getCountries(this);
    }

    @OnClick({R.id.ivCancel, R.id.btnSendCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivCancel:
                onBackPressed();
                break;
            case R.id.btnSendCode:
                if (etPhone.getText().toString().isEmpty()) {
                    etPhone.setError(getString(R.string.empty));
                } else {
                    presenter.forgetPassword(this, etPhone.getText().toString());
                }
                break;
        }
    }

    @Override
    public void getCode(String code) {
        if (!code.isEmpty()) {
            Intent intent = new Intent(this, EnterCodeActivity.class);
            intent.putExtra("code", code);
            intent.putExtra("mobile", etPhone.getText().toString());
            intent.putExtra("from", "forgetPassword");
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void isReset(boolean b) {

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
