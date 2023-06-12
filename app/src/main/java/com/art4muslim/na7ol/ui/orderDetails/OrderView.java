package com.art4muslim.na7ol.ui.orderDetails;

import com.art4muslim.na7ol.internet.model.WeightsResponse;

import java.util.List;

public interface OrderView {
    void isAdded(boolean isAdded);

    void isTransporter(String adv_driver_status);

    void getWeights(List<WeightsResponse.ReturnsEntity> returns);
}
