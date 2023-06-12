package com.art4muslim.na7ol.ui.chat;


import com.art4muslim.na7ol.internet.model.MessagesResponse;

import java.util.List;

public interface ChatView {
//    void getChats(List<ChatModelResponse.Returns> returns);

    void messageSent(boolean b);

    void getChats(List<MessagesResponse.ReturnsEntity> returns);

//    void statusUpdate(boolean b);

//    void getTrips(List<TripsModelResponse.Returns> returns);
}
