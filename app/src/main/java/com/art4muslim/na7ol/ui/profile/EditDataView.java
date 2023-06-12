package com.art4muslim.na7ol.ui.profile;


import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.UserResponse;

import java.util.List;

public interface EditDataView {
    void getUserData(UserResponse.ReturnsEntity returns);

    void isUpdated(boolean b);

    void getCountries(List<CountriesResponse.Return> returns);
}
