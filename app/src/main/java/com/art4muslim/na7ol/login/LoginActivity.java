package com.art4muslim.na7ol.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.UserResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.forgetPassword.ForgetPasswordActivity;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.ui.register.RegisterationActivity;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnCreateAccount)
    Button btnCreateAccount;
    @BindView(R.id.btnForgetPassword)
    Button btnForgetPassword;
    @Inject
    LoginPresenter presenter;
    private PrefrencesStorage storage;
    String phoneCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else {
            Utils.chooseLang(this, "en");
        }
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        storage = new PrefrencesStorage(this);
        etPassword.setLongClickable(false);
        phoneCode = ccp.getDefaultCountryCode();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                phoneCode = selectedCountry.getPhoneCode();
            }
        });
    }

    @OnClick({R.id.btnLogin, R.id.btnCreateAccount, R.id.btnForgetPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                setUpValidation();
                break;
            case R.id.btnCreateAccount:
                startActivity(new Intent(this, RegisterationActivity.class));
                break;
            case R.id.btnForgetPassword:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }


    private void setUpValidation() {
        if (etPhone.getText().toString().isEmpty()) {
            etPhone.setError(getString(R.string.empty));
        } else if (etPhone.getText().toString().contains(" ")) {
            Toast.makeText(this, getString(R.string.PhoneMustNotHaveSpaces), Toast.LENGTH_SHORT).show();
        }
//        else if (etPhone.getText().toString().length() < 15) {
//            Toast.makeText(this, getString(R.string.phoneError), Toast.LENGTH_SHORT).show();
//        }
        else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(getString(R.string.empty));
        } else if (etPassword.getText().toString().contains(" ")) {
            Toast.makeText(this, getString(R.string.passwordMustNotContainSpace), Toast.LENGTH_SHORT).show();
        } else {
            presenter.loginUser(this, phoneCode + etPhone.getText().toString(), etPassword.getText().toString());
        }
    }

    @Override
    public void getUserData(UserResponse.ReturnsEntity details) {
        if (details != null) {
            storage.storeId(details.getAdv_id());
            storage.storeKey("name", details.getAdv_name());
            storage.storeKey("city", details.getAdv_city_id());
            storage.storeKey("id", details.getAdv_id());
            storage.storeKey("email", details.getAdv_email());
            storage.storeKey("phone", details.getAdv_mobile());
            storage.storeKey("photo", details.getImageUrl());
            storage.storeKey("isTransporter", "" + details.getAdv_driver_status());
            storage.setFirstTimeLogin(true);
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }
}