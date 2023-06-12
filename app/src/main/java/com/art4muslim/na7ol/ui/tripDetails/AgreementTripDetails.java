package com.art4muslim.na7ol.ui.tripDetails;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;



import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.internet.model.RateResponse;
import com.art4muslim.na7ol.internet.model.TermsResponseModel;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.myOrders.MyOrderPresenter;
import com.art4muslim.na7ol.ui.myOrders.MyOrderView;
import com.art4muslim.na7ol.ui.myOrders.RateAdapter;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
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

public class AgreementTripDetails extends AppCompatActivity implements MyOrderView {

    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.frOptions)
    FrameLayout frOptions;
    @BindView(R.id.tvFrom)
    TextView tvFrom;
    @BindView(R.id.tvTo)
    TextView tvTo;
    @BindView(R.id.tvTimeFrom)
    TextView tvTimeFrom;
    @BindView(R.id.tvToTime)
    TextView tvToTime;
    @BindView(R.id.tvWeight)
    TextView tvWeight;
    @BindView(R.id.tvDetails)
    TextView tvDetails;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.liTraveller)
    LinearLayout liTraveller;
    @BindView(R.id.v3)
    View v3;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.frPrice)
    FrameLayout frPrice;
    @BindView(R.id.btnCopy)
    Button btnCopy;
    @BindView(R.id.frContract)
    FrameLayout frContract;
    @BindView(R.id.tvContract)
    TextView tvContract;
    @BindView(R.id.v4)
    View v4;
    @BindView(R.id.tvRate)
    TextView tvRate;
    @BindView(R.id.rvRates)
    RecyclerView rvRates;
    @Inject
    MyOrderPresenter presenter;
    @BindView(R.id.v0)
    View v0;
    @BindView(R.id.tvRecipcientName)
    TextView tvRecipcientName;
    @BindView(R.id.tvCall)
    TextView tvCall;
    @BindView(R.id.liReciepcient)
    LinearLayout liReciepcient;
    //    @BindView(R.id.nestedScroll)
//    NestedScrollView nestedScroll;
    @BindView(R.id.tvFlight)
    TextView tvFlight;
    private AgreementsResponseModel.ReturnsEntity model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_trip_details);
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        model = new Gson().fromJson(getIntent().getStringExtra("model"), new TypeToken<AgreementsResponseModel.ReturnsEntity>() {
        }.getType());
        presenter.setView(this);
        presenter.getUserRate(this, model.getAgr_from_id());
        fillView();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void fillView() {
        tvFrom.setText(model.getFrom_city_name());
        tvTo.setText(model.getTo_city_name());
        tvWeight.setText(model.getServ_weight_name() + " KG");
        tvTimeFrom.setText(getDateFormat(model.getServ_from_time()));
        tvToTime.setText(getDateFormat(model.getServ_to_time()));
//        binding.tvDetails.setText();
        tvFlight.setText(model.getCartype_name());
        tvContract.setText(model.getServ_contract());
        tvDetails.setText(model.getAgr_package_details());
        if (!model.getImageUrl().isEmpty()) {
            Log.e("imagesss", "" + model.getImageUrl());
            Picasso.with(this).load(model.getImageUrl()).into(ivImage);
        }
        if (model.getAgr_status().equals("pending")) {
            liTraveller.setVisibility(View.GONE);
            frContract.setVisibility(View.GONE);
            frPrice.setVisibility(View.GONE);
            tvContract.setVisibility(View.GONE);
            v1.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v4.setVisibility(View.GONE);
            tvRate.setVisibility(View.GONE);
            rvRates.setVisibility(View.GONE);

        } else if (model.getAgr_status().equals("accepted")) {
            liTraveller.setVisibility(View.GONE);
            frContract.setVisibility(View.VISIBLE);
            tvContract.setVisibility(View.VISIBLE);
            v1.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
            v4.setVisibility(View.GONE);
            tvRate.setVisibility(View.GONE);
            rvRates.setVisibility(View.GONE);
        } else if (model.getAgr_status().equals("complete")) {
            liTraveller.setVisibility(View.VISIBLE);
            frContract.setVisibility(View.VISIBLE);
            tvContract.setVisibility(View.VISIBLE);
            v1.setVisibility(View.VISIBLE);
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

        tvName.setText(model.getTo_user_name());
        rating.setRating(model.getDriver_rating());
        if (!model.getFrom_user_image().isEmpty()) {
            Picasso.with(this).load(model.getFrom_user_image()).into(ivProfile);
        }
        tvPrice.setText(model.getAgr_cost() + " SAR");


    }

    private String getDateFormat(String stringDate) {
        String dateText = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(stringDate);
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
            String day = (String) DateFormat.format("dd", date); // 20
            String monthString = (String) DateFormat.format("MMM", date); // Jun
            String monthNumber = (String) DateFormat.format("MM", date); // 06
            String year = (String) DateFormat.format("yyyy", date); // 2013
            dateText = year + " " + monthString + " " + day;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateText;
    }


    @OnClick({R.id.ivBack, R.id.frOptions, R.id.btnCopy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
//                onBackPressed();
                Intent intent = new Intent(this, MyTrips.class);
                intent.putExtra("type", "myTrips");
                startActivity(intent);
                finish();
                break;
            case R.id.frOptions:
                break;
            case R.id.btnCopy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("code", model.getAgr_code());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, getString(R.string.codeCopiedSuccessfully), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, MyTrips.class);
        intent.putExtra("type", "myTrips");
        startActivity(intent);
        finish();
    }
}