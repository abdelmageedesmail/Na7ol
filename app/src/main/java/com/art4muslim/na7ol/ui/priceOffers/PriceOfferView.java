package com.art4muslim.na7ol.ui.priceOffers;

import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;

import java.util.List;

public interface PriceOfferView {
    void getAgreements(List<AgreementsResponseModel.ReturnsEntity> returns);

    void isAccepted(boolean b);
}
