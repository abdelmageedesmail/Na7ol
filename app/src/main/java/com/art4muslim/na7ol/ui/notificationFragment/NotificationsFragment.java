package com.art4muslim.na7ol.ui.notificationFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.NotificationModel;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.chat.ChatActivity;
import com.art4muslim.na7ol.ui.chatFragment.ChatFragment;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.ui.joinRequests.JoinRequestsActivity;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.orderFragment.OrderFragment;
import com.art4muslim.na7ol.ui.priceOffers.PriceOffersActivity;
import com.art4muslim.na7ol.ui.profile.ProfileActivity;
import com.art4muslim.na7ol.ui.tripFragment.JoinRequestActivity;
import com.art4muslim.na7ol.ui.tripFragment.TripFragment;
import com.art4muslim.na7ol.utils.HelperClass;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.UpdateViews;
import com.art4muslim.na7ol.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NotificationsFragment extends Fragment implements NotificationsView {

    @BindView(R.id.rvNotifications)
    RecyclerView rvNotifications;
    @BindView(R.id.noData)
    View noData;
    @Inject
    NotificationsPresenter presenter;
    PrefrencesStorage storage;
    Unbinder unbinder;
    UpdateViews updateViews;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((Na7ol) getActivity().getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(getActivity()).equals("ar")) {
            Utils.chooseLang(getActivity(), "ar");
        } else if (Utils.getLang(getActivity()).equals("en")) {
            Utils.chooseLang(getActivity(), "en");
        } else {
            Utils.chooseLang(getActivity(), "zh");
        }
        presenter.setView(this);
        storage = new PrefrencesStorage(getActivity());
        presenter.getNotifications(getActivity(), storage.getId());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void isDeleted(boolean isDeleted) {

    }

    @Override
    public void onAttach(@androidx.annotation.NonNull Context context) {
        super.onAttach(context);
        updateViews = (UpdateViews) context;
    }


    @Override
    public void onResume() {
        super.onResume();
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    // handle back button's click listener
//                    startActivity(new Intent(getActivity(), HomeActivity.class));
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void getNotifications(final List<NotificationModel.ReturnsEntity> arrayList) {
        NotificationsAdapter notificationsAdapter = new NotificationsAdapter(getActivity(), arrayList);
        rvNotifications.setAdapter(notificationsAdapter);
        rvNotifications.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        presenter.setNotificationRead(getActivity(), storage.getId());
        notificationsAdapter.setNotificationClick(new NotificationsAdapter.NotificationClick() {
            @Override
            public void setOnNotificationClick(int pos) {

                if (arrayList.get(pos).getNotify_type().equals("agreement_price_offer")) {
                    Intent i = new Intent(getActivity(), PriceOffersActivity.class);
                    i.putExtra("servId", arrayList.get(pos).getNotify_service_id());
                    startActivity(i);
                } else if (arrayList.get(pos).getNotify_type().equals("agreement_price_accepted")) {
                    Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
                    intent.putExtra("status", "accepted");
                    startActivity(intent);
                } else if (arrayList.get(pos).getNotify_type().equals("agreement_accepted")) {
                    if (arrayList.get(pos).getNotify_advertiser_id().equals(storage.getId())) {
                        Intent intent = new Intent(getActivity(), MyTrips.class);
                        intent.putExtra("status", "accepted");
                        intent.putExtra("type", "myTrips");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
                        intent.putExtra("status", "accepted");
                        startActivity(intent);
                    }

                } else if (arrayList.get(pos).getNotify_type().equals("account_updated")) {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    startActivity(intent);
                } else if (arrayList.get(pos).getNotify_type().equals("agreement_completed")) {
                    Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
                    intent.putExtra("status", "complete");
                    startActivity(intent);
                } else if (arrayList.get(pos).getNotify_type().equals("user_rated")) {
                    Intent intent = new Intent(getActivity(), MyTrips.class);
                    intent.putExtra("status", "complete");
                    intent.putExtra("type", "myTrips");
                    startActivity(intent);
                } else if (arrayList.get(pos).getNotify_type().equals("contract_added")) {
                    Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
                    intent.putExtra("status", "accepted");
                    startActivity(intent);
                } else if (arrayList.get(pos).getNotify_type().equals("price_agreement_created")) {
                    Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
                    intent.putExtra("status", "pending");
                    startActivity(intent);
                } else if (arrayList.get(pos).getNotify_type().equals("trip_updated")) {
                    Intent intent = new Intent(getActivity(), MyTrips.class);
                    intent.putExtra("type", "myTrips");
                    intent.putExtra("status", "pending");

                    startActivity(intent);
                } else if (arrayList.get(pos).getNotify_type().equals("order_agreement_created")) {
                    Intent i = new Intent(getActivity(), MyOrdersActivity.class);
                    i.putExtra("status", "accepted");
                    startActivity(i);
                } else if (arrayList.get(pos).getNotify_type().equals("auth_code")) {
                    Intent i = new Intent(getActivity(), MyTrips.class);
                    i.putExtra("type", "myTrips");
                    i.putExtra("status", "accepted");
                    startActivity(i);
                } else if (arrayList.get(pos).getNotify_type().equals("trip_created")) {
                    updateViews.updateView("trips");
                    HelperClass.replaceFragment(getActivity().getSupportFragmentManager().beginTransaction(), new TripFragment());
                } else if (arrayList.get(pos).getNotify_type().equals("new_offer")) {
                    updateViews.updateView("trips");
                    HelperClass.replaceFragment(getActivity().getSupportFragmentManager().beginTransaction(), new TripFragment());
                } else if (arrayList.get(pos).getNotify_type().equals("new_trip")) {
                    updateViews.updateView("trips");
                    HelperClass.replaceFragment(getActivity().getSupportFragmentManager().beginTransaction(), new TripFragment());
                } else if (arrayList.get(pos).getNotify_type().equals("new_order")) {

                    updateViews.updateView("order");
                    HelperClass.replaceFragment(getActivity().getSupportFragmentManager().beginTransaction(), new OrderFragment());
                } else if (arrayList.get(pos).getNotify_type().equals("trip_agreement_created")) {
                    if (storage.getId().equals(arrayList.get(pos).getNotify_agreement_owner())) {
                        Intent intent = new Intent(getActivity(), MyTrips.class);
                        intent.putExtra("type", "myTrips");
                        intent.putExtra("status", "accepted");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
                        startActivity(intent);
                    }
                } else if (arrayList.get(pos).getNotify_type().equals("agreement_created")) {
                    Intent intent = new Intent(getActivity(), MyTrips.class);
                    intent.putExtra("type", "myTrips");
                    intent.putExtra("status", "accepted");
                    startActivity(intent);

                } else if (arrayList.get(pos).getNotify_type().equals("msg_sent")) {
//                    Intent intent = new Intent(getActivity(), ChatActivity.class);
//                    if (storage.getId().equals(arrayList.get(pos).getNotify_message_from())) {
//                        intent.putExtra("to", arrayList.get(pos).getNotify_message_to());
//                    } else {
//                        intent.putExtra("to", arrayList.get(pos).getNotify_message_from());
//                    }
//                    if (arrayList.get(pos).getNotify_message_is_adminstrative().equals("1")) {
//                        intent.putExtra("isAdmin", "1");
//                    }
//
//                    intent.putExtra("userName", storage.getKey("name"));
//                    intent.putExtra("userImage", "");
//                    intent.putExtra("fromImage", storage.getKey("photo"));
//                    intent.putExtra("tripId", arrayList.get(pos).getNotify_service_id());
//                    startActivity(intent);
                    updateViews.updateView("chat");

                    HelperClass.replaceFragment(getActivity().getSupportFragmentManager().beginTransaction(), new ChatFragment());
                } else if (arrayList.get(pos).getNotify_type().equals("chat")) {
//                    Intent intent = new Intent(getActivity(), ChatActivity.class);
//                    intent.putExtra("to", arrayList.get(pos).getNotify_sender_chat_id());
//                    intent.putExtra("userName", storage.getKey("name"));
//                    intent.putExtra("userImage", "");
//                    intent.putExtra("fromImage", storage.getKey("photo"));
//                    intent.putExtra("tripId", arrayList.get(pos).getNotify_service_id());
//                    startActivity(intent);
                    updateViews.updateView("chat");
                    HelperClass.replaceFragment(getActivity().getSupportFragmentManager().beginTransaction(), new ChatFragment());
                }
            }
        });
    }

    @Override
    public void isRead(boolean b) {
        if (b) {

        }
    }

    @Override
    public void statusUpdate(boolean b) {

    }

}
