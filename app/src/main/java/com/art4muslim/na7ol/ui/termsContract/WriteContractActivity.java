package com.art4muslim.na7ol.ui.termsContract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.internet.model.RateResponse;
import com.art4muslim.na7ol.internet.model.TermsResponseModel;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.myOrders.MyOrderPresenter;
import com.art4muslim.na7ol.ui.myOrders.MyOrderView;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteContractActivity extends AppCompatActivity implements MyOrderView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etContract)
    EditText etContract;
    @BindView(R.id.btnSend)
    Button btnSend;
    @Inject
    MyOrderPresenter presenter;
    private String tripId, driverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_contract);
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        presenter.setView(this);
        tripId = getIntent().getExtras().getString("tripId");
        driverId = getIntent().getExtras().getString("driverId");

    }

    @OnClick({R.id.ivBack, R.id.btnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnSend:
                presenter.writeContract(this, tripId, driverId, etContract.getText().toString());
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
        if (b) {
            Toast.makeText(this, getString(R.string.contractSentSuccessfully), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MyTrips.class);
            intent.putExtra("type", "myTrips");
            startActivity(intent);
            finish();
        }
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

    }

    @Override
    public void codeSent(boolean b) {

    }
}