package com.art4muslim.na7ol.ui.notificationSettings;

import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.NotificationSettingResponse;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;

import java.util.List;

public interface NotificationView {
    void updateNotification(boolean isUpdated);

    void getNotificationSettings(NotificationSettingResponse.ReturnsEntity returns);

    void getCountries(List<CountriesResponse.Return> returnList);

    void getCities(List<CitiesResponse.Return> returnList);


    void getWeights(List<WeightsResponse.ReturnsEntity> returns);

    void getCarTypes(List<CarTypeResponse.ReturnsEntity> returns);

    void getOrderTypes(List<OrderTypeResponse.ReturnsEntity> returns);
}
