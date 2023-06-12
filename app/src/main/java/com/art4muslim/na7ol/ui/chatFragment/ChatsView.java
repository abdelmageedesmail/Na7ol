package com.art4muslim.na7ol.ui.chatFragment;

import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.internet.model.ChatModel;
import com.art4muslim.na7ol.internet.model.ChatsModel;
import com.art4muslim.na7ol.internet.model.RateResponse;
import com.art4muslim.na7ol.internet.model.TermsResponseModel;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;

import java.util.List;

public interface ChatsView {


    void getChats(List<ChatsModel.ReturnsEntity> returns);
}
