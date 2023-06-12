package com.art4muslim.na7ol.ui.notificationFragment;

import com.art4muslim.na7ol.internet.model.NotificationModel;

import java.util.List;

public interface NotificationsView {
    void isDeleted(boolean isDeleted);

    void getNotifications(List<NotificationModel.ReturnsEntity> messages);

    void isRead(boolean b);

    void statusUpdate(boolean b);
}
