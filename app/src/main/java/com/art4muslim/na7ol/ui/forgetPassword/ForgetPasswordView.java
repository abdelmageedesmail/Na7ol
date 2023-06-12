package com.art4muslim.na7ol.ui.forgetPassword;

import com.art4muslim.na7ol.internet.model.CountriesResponse;

import java.util.List;

public interface ForgetPasswordView {
    void getCode(String code);

    void isReset(boolean b);

    void getCountries(List<CountriesResponse.Return> returns);

    void codeCorrect(boolean b);

    void codeResent(boolean codeResent);

}
