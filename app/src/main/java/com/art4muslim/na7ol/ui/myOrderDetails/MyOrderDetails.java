package com.art4muslim.na7ol.ui.myOrderDetails;

import android.content.Intent;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.internet.model.RateResponse;
import com.art4muslim.na7ol.internet.model.TermsResponseModel;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.addShipment.AddShipment;
import com.art4muslim.na7ol.ui.chat.ChatActivity;
import com.art4muslim.na7ol.ui.myOrders.MyOrderPresenter;
import com.art4muslim.na7ol.ui.myOrders.MyOrderView;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.myOrders.RateAdapter;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.ui.priceOffers.PriceOffersActivity;
import com.art4muslim.na7ol.ui.termsContract.TermsActivity;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderDetails extends AppCompatActivity implements MyOrderView {

    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvFrom)
    TextView tvFrom;
    @BindView(R.id.tvTo)
    TextView tvTo;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvWeight)
    TextView tvWeight;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvAccessPoint)
    TextView tvAccessPoint;
    @BindView(R.id.tvDetails)
    TextView tvDetails;
    @BindView(R.id.btnCopy)
    Button btnCopy;
    @BindView(R.id.tvContract)
    TextView tvContract;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.tvRecipcientName)
    TextView tvRecipcientName;
    @BindView(R.id.tvCall)
    TextView tvCall;
    @BindView(R.id.liReciepcient)
    LinearLayout liReciepcient;
    @BindView(R.id.v2)
    View v2;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.liTraveller)
    LinearLayout liTraveller;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.frContract)
    FrameLayout frContract;
    @BindView(R.id.frPrice)
    FrameLayout frPrice;
    @BindView(R.id.v3)
    View v3;
    @BindView(R.id.frOptions)
    FrameLayout frOptions;
    @BindView(R.id.v4)
    View v4;
    @BindView(R.id.tvRate)
    TextView tvRate;
    @BindView(R.id.rvRates)
    RecyclerView rvRates;
    @Inject
    MyOrderPresenter presenter;
    @BindView(R.id.tvFlightType)
    TextView tvFlightType;
    //    @BindView(R.id.nestedScroll)
//    NestedScrollView nestedScroll;
    private AgreementsResponseModel.ReturnsEntity model;
    private TripsModelResponse.Returns tripModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_details);
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
//        if (getIntent().getExtras().getString("from").equals("orders")) {
//            tripModel = new Gson().fromJson(getIntent().getStringExtra("model"), new TypeToken<TripsModelResponse.Returns>() {
//            }.getType());
//        }else {
        model = new Gson().fromJson(getIntent().getStringExtra("model"), new TypeToken<AgreementsResponseModel.ReturnsEntity>() {
        }.getType());
//        }
        presenter.setView(this);
        presenter.getUserRate(this, model.getAgr_to_id());
        fillView();
    }

    private void fillView() {
        tvFrom.setText(model.getFrom_city_name());
        tvTo.setText(model.getTo_city_name());
        tvType.setText(model.getOrder_types());
        tvWeight.setText(model.getServ_weight_name() + " KG");
        if (model.getServ_time().isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date date1 = simpleDateFormat.parse(model.getServ_from_time());
                Date date2 = simpleDateFormat.parse(model.getServ_to_time());

                printDifference(date1, date2);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            tvDate.setText(model.getServ_time());
        }

        tvFlightType.setText(model.getCartype_name());
//        binding.tvDetails.setText();
        tvDetails.setText(model.getAgr_package_details());
        tvContract.setText(model.getServ_contract());


        if (!model.getImageUrl().isEmpty()) {
            Picasso.with(this).load(model.getImageUrl()).into(ivImage);
        }
        if (model.getAgr_status().equals("pending")) {
            liReciepcient.setVisibility(View.GONE);
            liTraveller.setVisibility(View.GONE);
            frContract.setVisibility(View.GONE);
            frPrice.setVisibility(View.GONE);
            tvContract.setVisibility(View.GONE);
            v1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v4.setVisibility(View.GONE);
            tvRate.setVisibility(View.GONE);
            rvRates.setVisibility(View.GONE);
            tvAccessPoint.setText(model.getAgr_delivery_type_name());

        } else if (model.getAgr_status().equals("accepted")) {
            liReciepcient.setVisibility(View.VISIBLE);
            liTraveller.setVisibility(View.GONE);
            frContract.setVisibility(View.VISIBLE);
            tvContract.setVisibility(View.VISIBLE);
            tvAccessPoint.setText(model.getAgr_delivery_type_name());
            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.VISIBLE);
            v4.setVisibility(View.GONE);
            tvRate.setVisibility(View.GONE);
            rvRates.setVisibility(View.GONE);
        } else if (model.getAgr_status().equals("complete")) {
            liReciepcient.setVisibility(View.VISIBLE);
            liTraveller.setVisibility(View.VISIBLE);
            frContract.setVisibility(View.VISIBLE);
            tvContract.setVisibility(View.VISIBLE);
            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
            v4.setVisibility(View.VISIBLE);
            tvRate.setVisibility(View.VISIBLE);
            rvRates.setVisibility(View.VISIBLE);
        }


        if (!model.getServ_receiver_name().isEmpty()) {
            tvRecipcientName.setText(model.getServ_receiver_name());
            tvCall.setText(model.getServ_receiver_mobile());
        } else {
            tvRecipcientName.setText(model.getAgr_receiver_name());
            tvCall.setText(model.getAgr_receiver_mobile());
        }

        tvName.setText(model.getFrom_user_name());
        rating.setRating(model.getDriver_rating());
        if (!model.getFrom_user_image().isEmpty()) {
            Picasso.with(this).load(model.getFrom_user_image()).into(ivProfile);
        }
        tvPrice.setText(model.getAgr_cost() + " SAR");


    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        if (elapsedDays == 0) {
            tvDate.setText(elapsedHours + " " + getString(R.string.hour));
        } else {
            tvDate.setText(elapsedDays + " " + getString(R.string.day));
        }
        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
    }


    @OnClick({R.id.ivBack, R.id.btnCopy, R.id.frOptions})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
//                onBackPressed();
                Intent intent = new Intent(this, MyOrdersActivity.class);
                intent.putExtra("status", "accepted");
                startActivity(intent);
                finish();
                break;
            case R.id.btnCopy:
                break;
            case R.id.frOptions:
                PopupMenu popup = new PopupMenu(this, frOptions);
                if (model.getAgr_status().equals("pending")) {
                    popup.inflate(R.menu.order_menu_pending);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.tvCancel:

                                    return true;
                                case R.id.tvEditOrder:
                                    Intent intent1 = new Intent(MyOrderDetails.this, AddShipment.class);
                                    intent1.putExtra("from", "edit");
                                    Gson gsonObj = new Gson();
                                    String jsonStr = gsonObj.toJson(model);
                                    intent1.putExtra("model", jsonStr);
                                    startActivity(intent1);
                                    return true;
                                case R.id.tvPriceOffer:
                                    Intent intent = new Intent(MyOrderDetails.this, PriceOffersActivity.class);
                                    intent.putExtra("servId", "" + model.getServ_id());
                                    startActivity(intent);
                                    return true;
                            }
                            return false;
                        }
                    });

                } else if (model.getAgr_status().equals("accepted")) {
                    popup.inflate(R.menu.order_menu_active);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.tvCancel:

                                    return true;
                                case R.id.tvChat:
                                    Intent intent = new Intent(MyOrderDetails.this, ChatActivity.class);
                                    intent.putExtra("to", model.getAgr_from_id());
                                    intent.putExtra("userName", model.getFrom_user_name());
                                    intent.putExtra("userImage", model.getFrom_user_image());
                                    intent.putExtra("fromImage", model.getTo_user_image());
                                    intent.putExtra("tripId", model.getServ_id());
                                    startActivity(intent);
                                    return true;
                                case R.id.tvTerms:
                                    Intent intent1 = new Intent(MyOrderDetails.this, TermsActivity.class);
                                    intent1.putExtra("tripId", "" + model.getServ_id());
                                    intent1.putExtra("driverId", "" + model.getAgr_from_id());
                                    startActivity(intent1);
                                    return true;
                            }
                            return false;
                        }
                    });
                } else if (model.getAgr_status().equals("complete")) {
                    popup.inflate(R.menu.order_menu_done);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.tvRate:
                                    return true;

                            }
                            return false;
                        }
                    });
                }

                popup.show();
                break;
        }
    }

    @Override
    public void getAgreements(List<AgreementsResponseModel.ReturnsEntity> returns) {

    }

    @Override
    public void getTrips(List<TripsModelResponse.Returns> returns) {

    }

    @Override
    public void getTerms(List<TermsResponseModel.ReturnsEntity> returns) {

    }

    @Override
    public void isSent(boolean b) {

    }

    @Override
    public void isCanceled(boolean b) {

    }

    @Override
    public void isUpdated(boolean b, String status) {

    }

    @Override
    public void isRated(boolean b) {

    }

    @Override
    public void getUserRate(List<RateResponse.ReturnsEntity> returns) {
        rvRates.setAdapter(new RateAdapter(this, returns));
        rvRates.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void codeSent(boolean b) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MyOrdersActivity.class);
        intent.putExtra("status", "accepted");
        startActivity(intent);
        finish();
    }

}