package com.art4muslim.na7ol.ui.myOrders;

import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.internet.model.RateResponse;
import com.art4muslim.na7ol.internet.model.TermsResponseModel;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;

import java.util.List;

public interface MyOrderView {
    void getAgreements(List<AgreementsResponseModel.ReturnsEntity> returns);

    void getTrips(List<TripsModelResponse.Returns> returns);

    void getTerms(List<TermsResponseModel.ReturnsEntity> returns);

    void isSent(boolean b);

    void isCanceled(boolean b);

    void isUpdated(boolean b, String status);

    void isRated(boolean b);

    void getUserRate(List<RateResponse.ReturnsEntity> returns);

    void codeSent(boolean b);
}
