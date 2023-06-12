package com.art4muslim.na7ol.ui.home;


import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;

import java.util.List;

public interface HomeView {
    void getCountries(List<CountriesResponse.Return> returnList);

    void getCities(List<CitiesResponse.Return> returnList);

    void addTrip(boolean isAdded);

    void getTrips(List<TripsModelResponse.Returns> returns);

    void getWeights(List<WeightsResponse.ReturnsEntity> returns);

    void isTransporter(String adv_driver_status);

    void getOrderTypes(List<OrderTypeResponse.ReturnsEntity> returns);

    void getCarTypes(List<CarTypeResponse.ReturnsEntity> returns);
}
