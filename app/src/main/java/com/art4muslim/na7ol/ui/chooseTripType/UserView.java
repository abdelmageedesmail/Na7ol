package com.art4muslim.na7ol.ui.chooseTripType;

import com.art4muslim.na7ol.internet.model.CarTypeResponse;

import java.util.List;

public interface UserView {
    void isTransporter(String isTransporter);

    void getCarTypes(List<CarTypeResponse.ReturnsEntity> returns);
}
