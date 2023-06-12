package com.art4muslim.na7ol.dagger.component;


import com.art4muslim.na7ol.dagger.modules.ApplicationModule;
import com.art4muslim.na7ol.login.LoginActivity;
import com.art4muslim.na7ol.ui.addShipment.AddShipment;
import com.art4muslim.na7ol.ui.add_trip.AddTripActivity;
import com.art4muslim.na7ol.ui.chat.ChatActivity;
import com.art4muslim.na7ol.ui.chatFragment.ChatFragment;
import com.art4muslim.na7ol.ui.chooseTripType.ChooseAddingType;
import com.art4muslim.na7ol.ui.forgetPassword.EnterCodeActivity;
import com.art4muslim.na7ol.ui.forgetPassword.ForgetPasswordActivity;
import com.art4muslim.na7ol.ui.forgetPassword.ResetNewPasswordActivity;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.ui.joinRequests.JoinRequestsActivity;
import com.art4muslim.na7ol.ui.joinTransporter.JoinAsTransporter;
import com.art4muslim.na7ol.ui.myCharge.MyChargeActivity;
import com.art4muslim.na7ol.ui.myOrderDetails.MyOrderDetails;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.myOrders.TransferToAccountActivity;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.notificationFragment.NotificationsFragment;
import com.art4muslim.na7ol.ui.notificationSettings.NotificationsSettings;
import com.art4muslim.na7ol.ui.orderDetails.OrderDetailsActivity;
import com.art4muslim.na7ol.ui.orderFragment.OrderFragment;
import com.art4muslim.na7ol.ui.pages.PagesActivity;
import com.art4muslim.na7ol.ui.priceOffers.PriceOffersActivity;
import com.art4muslim.na7ol.ui.profile.ProfileActivity;
import com.art4muslim.na7ol.ui.register.RegisterationActivity;
import com.art4muslim.na7ol.ui.settings.SettingsActivity;
import com.art4muslim.na7ol.ui.technicalSupport.TechnicalSupportActivity;
import com.art4muslim.na7ol.ui.termsContract.TermsActivity;
import com.art4muslim.na7ol.ui.termsContract.WriteContractActivity;
import com.art4muslim.na7ol.ui.tripDetails.AgreementTripDetails;
import com.art4muslim.na7ol.ui.tripFragment.JoinRequestActivity;
import com.art4muslim.na7ol.ui.tripFragment.TripFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisterationActivity registerationActivity);

    void inject(OrderFragment orderFragment);

    void inject(TripFragment tripFragment);

    void inject(AddTripActivity addTripActivity);

    void inject(AddShipment addShipment);

    void inject(JoinAsTransporter joinAsTransporter);

    void inject(OrderDetailsActivity orderDetailsActivity);

    void inject(MyOrdersActivity myOrdersActivity);

    void inject(MyTrips myTrips);

    void inject(TechnicalSupportActivity technicalSupportActivity);

    void inject(PagesActivity pagesActivity);

    void inject(ProfileActivity profileActivity);

    void inject(NotificationsFragment notificationsFragment);

    void inject(JoinRequestActivity joinRequestActivity);

    void inject(PriceOffersActivity priceOffersActivity);

    void inject(ChatActivity chatActivity);

    void inject(TermsActivity termsActivity);

    void inject(WriteContractActivity writeContractActivity);

    void inject(JoinRequestsActivity joinRequestsActivity);

    void inject(MyOrderDetails myOrderDetails);

    void inject(ChatFragment chatFragment);

    void inject(MyChargeActivity myChargeActivity);

    void inject(ForgetPasswordActivity forgetPasswordActivity);

    void inject(EnterCodeActivity enterCodeActivity);

    void inject(ResetNewPasswordActivity resetNewPasswordActivity);

    void inject(ChooseAddingType chooseAddingType);

    void inject(NotificationsSettings notificationsSettings);

    void inject(AgreementTripDetails agreementTripDetails);

    void inject(SettingsActivity settingsActivity);

    void inject(TransferToAccountActivity transferToAccountActivity);
}
