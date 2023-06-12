package com.art4muslim.na7ol.ui.orderFragment;


import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.DeliveryMethodModel;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;

import java.util.List;

public interface TripsView {
    void getTrips(List<TripsModelResponse.Returns> list, boolean canLoadMore);

    void getFliterTrips(List<TripsModelResponse.Returns> list, boolean canLoadMore);

    void isJoined(boolean b);

    void getMethods(List<DeliveryMethodModel.ReturnsEntity> returns);

    void getCarTypes(List<CarTypeResponse.ReturnsEntity> returns);

    void getOrderTypes(List<OrderTypeResponse.ReturnsEntity> returns);

    void getWeights(List<WeightsResponse.ReturnsEntity> returns);
}
