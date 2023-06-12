package com.art4muslim.na7ol.ui.tripDetails;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TripDetails extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
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
    @BindView(R.id.tvContract)
    TextView tvContract;
    @BindView(R.id.btnAddOffer)
    Button btnAddOffer;
    @BindView(R.id.tvFlight)
    TextView tvFlight;
    private AgreementsResponseModel.ReturnsEntity model;
    private EditText et1, et2, et3, et4, et5, et6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        model = new Gson().fromJson(getIntent().getStringExtra("model"), new TypeToken<AgreementsResponseModel.ReturnsEntity>() {
        }.getType());
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        fillView();
    }

    private void fillView() {
        tvFrom.setText(model.getFrom_city_name());
        tvTo.setText(model.getTo_city_name());
        String dateFormatFrom = getDateFormat(model.getServ_from_time());
        String dateFormatTo = getDateFormat(model.getServ_to_time());
        tvTimeFrom.setText(dateFormatFrom);
        tvToTime.setText(dateFormatTo);
        tvDetails.setText(model.getAgr_package_details());
        tvWeight.setText(model.getServ_weight() + " KG");
        tvFlight.setText(model.getCartype_name());

    }

    private void popupCode() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_code);
        et1 = dialog.findViewById(R.id.et1);
        et2 = dialog.findViewById(R.id.et2);
        et3 = dialog.findViewById(R.id.et3);
        et4 = dialog.findViewById(R.id.et4);

        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        Button btnSend = dialog.findViewById(R.id.btnSend);
        requestFocus();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.show();
    }

    private void requestFocus() {
        et1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (et1.getText().toString().length() == 1) {
                    et2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        et2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (et2.getText().toString().length() == 1) {
                    et3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        et3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (et3.getText().toString().length() == 1) {
                    et4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

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


    @OnClick({R.id.ivBack, R.id.btnAddOffer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
//                onBackPressed();
                Intent intent = new Intent(this, MyTrips.class);
                intent.putExtra("type", "myTrips");
                startActivity(intent);
                finish();
                break;
            case R.id.btnAddOffer:
                popupCode();
                break;
        }
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