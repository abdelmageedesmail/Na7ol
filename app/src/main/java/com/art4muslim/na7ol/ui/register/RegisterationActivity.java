package com.art4muslim.na7ol.ui.register;


import android.content.Intent;
import android.os.Bundle;


import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.databinding.DataBindingUtil;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ActivityRegisterationBinding;
import com.art4muslim.na7ol.internet.model.UserResponse;
import com.art4muslim.na7ol.login.LoginActivity;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.forgetPassword.EnterCodeActivity;
import com.art4muslim.na7ol.utils.Utils;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.PriorityBlockingQueue;

import javax.inject.Inject;

public class RegisterationActivity extends AppCompatActivity implements View.OnClickListener, RegisterationView {

    private ActivityRegisterationBinding binding;

    @Inject
    RegisterationPresenter presenter;
    String phoneCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registeration);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        presenter.setView(this);
        binding.ivBack.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);

        preventCopy();
        phoneCode = binding.ccp.getDefaultCountryCode();
        binding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                phoneCode = selectedCountry.getPhoneCode();
            }
        });
    }

    private void preventCopy() {
        binding.etName.setLongClickable(false);
        binding.etPassword.setLongClickable(false);
        binding.etConfirmPassword.setLongClickable(false);
        binding.etName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(RegisterationActivity.this, getString(R.string.copyPrevented), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        binding.etPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(RegisterationActivity.this, getString(R.string.copyPreventedPassword), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        binding.etConfirmPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(RegisterationActivity.this, getString(R.string.copyPreventedPassword), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        binding.etEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(RegisterationActivity.this, getString(R.string.copyPreventedPassword), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnRegister:
                setUpValidation();
                break;

        }
    }

    public static boolean isValidEmail(String target) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (target.matches(emailPattern))
            return true;
        else
            return false;
    }

    private void setUpValidation() {
        if (binding.etName.getText().toString().isEmpty()) {
            binding.etName.setError(getString(R.string.empty));
        } else if (checkName(binding.etName)) {
            Toast.makeText(this, getString(R.string.nameMustNotHaveNumber), Toast.LENGTH_SHORT).show();
        } else if (binding.etName.getText().toString().length() < 3) {
            Toast.makeText(this, getString(R.string.nameMustBeGreaterThanThreeCha), Toast.LENGTH_SHORT).show();
        } else if (binding.etName.getText().toString().length() > 50) {
            Toast.makeText(this, getString(R.string.nameMustBeGreaterThanlessCha), Toast.LENGTH_SHORT).show();
        } else if (binding.etName.getText().toString().startsWith(" ")) {
            Toast.makeText(this, getString(R.string.nameMustNotStartWithSpace), Toast.LENGTH_SHORT).show();
        } else if (binding.etLastName.getText().toString().isEmpty()) {
            binding.etLastName.setError(getString(R.string.empty));
        } else if (checkName(binding.etLastName)) {
            Toast.makeText(this, getString(R.string.nameMustNotHaveNumber), Toast.LENGTH_SHORT).show();
        } else if (binding.etLastName.getText().toString().length() < 3) {
            Toast.makeText(this, getString(R.string.nameMustBeGreaterThanThreeCha), Toast.LENGTH_SHORT).show();
        } else if (binding.etLastName.getText().toString().length() > 50) {
            Toast.makeText(this, getString(R.string.nameMustBeGreaterThanlessCha), Toast.LENGTH_SHORT).show();
        } else if (binding.etLastName.getText().toString().startsWith(" ")) {
            Toast.makeText(this, getString(R.string.nameMustNotStartWithSpace), Toast.LENGTH_SHORT).show();
        } else if (binding.etEmail.getText().toString().isEmpty()) {
            binding.etEmail.setError(getString(R.string.empty));
        } else if (!isValidEmail(binding.etEmail.getText().toString())) {
            Toast.makeText(this, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
        } else if (binding.etPhone.getText().toString().isEmpty()) {
            binding.etPhone.setError(getString(R.string.empty));
        } else if (binding.etPhone.getText().toString().length() < 10) {
            Toast.makeText(this, getString(R.string.phoneError), Toast.LENGTH_SHORT).show();
        }
//        else if (binding.etPhone.getText().toString().length() > 15) {
//            Toast.makeText(this, getString(R.string.phoneError), Toast.LENGTH_SHORT).show();
//        }
        else if (binding.etPhone.getText().toString().contains(" ")) {
            Toast.makeText(this, getString(R.string.PhoneMustNotHaveSpaces), Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().isEmpty()) {
            binding.etPassword.setError(getString(R.string.empty));
        } else if (binding.etConfirmPassword.getText().toString().isEmpty()) {
            binding.etConfirmPassword.setError(getString(R.string.empty));
        } else if (!binding.etPassword.getText().toString().equals(binding.etConfirmPassword.getText().toString())) {
            Toast.makeText(this, getString(R.string.passwordNotMAtches), Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().length() < 6) {
            Toast.makeText(this, getString(R.string.passwordMustBeGreaterThan), Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().contains(" ")) {
            Toast.makeText(this, getString(R.string.passwordMustNotContainSpace), Toast.LENGTH_SHORT).show();
        } else if (!hasUpperCase(binding.etPassword.getText().toString())) {
            Toast.makeText(this, getString(R.string.passwordMustHaveUpperCase), Toast.LENGTH_SHORT).show();
        } else if (!hasLowerCase(binding.etPassword.getText().toString())) {
            Toast.makeText(this, getString(R.string.passwordMustHaveLowerCase), Toast.LENGTH_SHORT).show();
        } else {
            presenter.registerUser(this, binding.etName.getText().toString() + " " + binding.etLastName.getText().toString(), phoneCode + binding.etPhone.getText().toString(), binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
//            signUpViewModel.signUp(binding.etName.getText().toString(), binding.etPhone.getText().toString(), binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
        }
    }


    private static boolean hasSymbol(CharSequence data) {
        String password = String.valueOf(data);
        return !password.matches("[A-Za-z0-9 ]*");
    }

    private static boolean hasUpperCase(CharSequence data) {
        String password = String.valueOf(data);
        return !password.equals(password.toLowerCase());
    }

    private static boolean hasLowerCase(CharSequence data) {
        String password = String.valueOf(data);
        return !password.equals(password.toUpperCase());
    }

    private boolean checkName(EditText etName) {
        boolean hasNum = false;
        if (etName.getText().toString().contains("0") || etName.getText().toString().contains("1") || etName.getText().toString().contains("2") || etName.getText().toString().contains("3") || etName.getText().toString().contains("4") || etName.getText().toString().contains("5") || etName.getText().toString().contains("6") || etName.getText().toString().contains("7") || etName.getText().toString().contains("8") || etName.getText().toString().contains("9")) {
            hasNum = true;
        } else {
            hasNum = false;
        }
        return hasNum;
    }

    @Override
    public void getUserDetails(UserResponse.ReturnsEntity returns) {
        if (returns != null) {
//            Toast.makeText(this, getString(R.string.accountCreated), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, EnterCodeActivity.class);
            intent.putExtra("mobile", phoneCode + binding.etPhone.getText().toString());
            intent.putExtra("from", "register");
            startActivity(intent);
            finish();
        }
    }
}