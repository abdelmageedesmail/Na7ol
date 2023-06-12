package com.art4muslim.na7ol.dagger.modules;


import com.art4muslim.na7ol.login.LoginPresenter;
import com.art4muslim.na7ol.ui.add_trip.TripPresenter;
import com.art4muslim.na7ol.ui.chat.ChatPresenter;
import com.art4muslim.na7ol.ui.chatFragment.ChatsPresenter;
import com.art4muslim.na7ol.ui.chooseTripType.UserPresenter;
import com.art4muslim.na7ol.ui.forgetPassword.ForgetPasswordPresenter;
import com.art4muslim.na7ol.ui.home.HomePresenter;
import com.art4muslim.na7ol.ui.joinTransporter.JoinAsTransporter;
import com.art4muslim.na7ol.ui.joinTransporter.JoinTransporterPresenter;
import com.art4muslim.na7ol.ui.myCharge.ChargePresenter;
import com.art4muslim.na7ol.ui.myOrders.MyOrderPresenter;
import com.art4muslim.na7ol.ui.myOrders.PaymentPresenter;
import com.art4muslim.na7ol.ui.notificationFragment.NotificationsPresenter;
import com.art4muslim.na7ol.ui.notificationSettings.NotificationPresenter;
import com.art4muslim.na7ol.ui.orderDetails.OrderPresenter;
import com.art4muslim.na7ol.ui.orderFragment.TripsPresenter;
import com.art4muslim.na7ol.ui.pages.PagesPresenter;
import com.art4muslim.na7ol.ui.priceOffers.PriceOfferPresenter;
import com.art4muslim.na7ol.ui.profile.ProfilePresenter;
import com.art4muslim.na7ol.ui.register.RegisterationPresenter;
import com.art4muslim.na7ol.ui.technicalSupport.ContactUsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    @Singleton
    @Provides
    public RegisterationPresenter registerationPresenter() {
        return new RegisterationPresenter();
    }

    @Singleton
    @Provides
    public LoginPresenter loginPresenter() {
        return new LoginPresenter();
    }

    @Singleton
    @Provides
    public TripsPresenter tripsPresenter() {
        return new TripsPresenter();
    }

    @Singleton
    @Provides
    public TripPresenter tripPresenter() {
        return new TripPresenter();
    }

    @Singleton
    @Provides
    public JoinTransporterPresenter joinTransporterPresenter() {
        return new JoinTransporterPresenter();
    }

    @Singleton
    @Provides
    public OrderPresenter orderPresenter() {
        return new OrderPresenter();
    }

    @Singleton
    @Provides
    public MyOrderPresenter myOrderPresenter() {
        return new MyOrderPresenter();
    }

    @Singleton
    @Provides
    public ContactUsPresenter contactUsPresenter() {
        return new ContactUsPresenter();
    }

    @Singleton
    @Provides
    public PagesPresenter pagesPresenter() {
        return new PagesPresenter();
    }

    @Singleton
    @Provides
    public ProfilePresenter profilePresenter() {
        return new ProfilePresenter();
    }

    @Singleton
    @Provides
    public NotificationsPresenter notificationsPresenter() {
        return new NotificationsPresenter();
    }

    @Singleton
    @Provides
    public PriceOfferPresenter priceOfferPresenter() {
        return new PriceOfferPresenter();
    }

    @Singleton
    @Provides
    public ChatPresenter chatPresenter() {
        return new ChatPresenter();
    }

    @Singleton
    @Provides
    public ChatsPresenter chatsPresenter() {
        return new ChatsPresenter();
    }

    @Singleton
    @Provides
    public HomePresenter homePresenter() {
        return new HomePresenter();
    }

    @Singleton
    @Provides
    public ChargePresenter chargePresenter() {
        return new ChargePresenter();
    }

    @Singleton
    @Provides
    public ForgetPasswordPresenter forgetPasswordPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Singleton
    @Provides
    public UserPresenter userPresenter() {
        return new UserPresenter();
    }

    @Singleton
    @Provides
    public NotificationPresenter notificationPresenter() {
        return new NotificationPresenter();
    }

    @Singleton
    @Provides
    public PaymentPresenter paymentPresenter() {
        return new PaymentPresenter();
    }

}
