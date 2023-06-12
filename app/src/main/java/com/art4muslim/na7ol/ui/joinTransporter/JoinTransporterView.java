package com.art4muslim.na7ol.ui.joinTransporter;


import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;

import java.util.List;

public interface JoinTransporterView {
    void getCountries(List<CountriesResponse.Return> returnList);

    void getCities(List<CitiesResponse.Return> returnList);

    void isJoined(boolean isAdded);

}
