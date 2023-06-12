package com.art4muslim.na7ol.ui.myCharge;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyChargeActivity extends AppCompatActivity implements ChargeView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvCharge)
    TextView tvCharge;
    @BindView(R.id.tvAppComm)
    TextView tvAppComm;
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.btnAddWithDraw)
    Button btnAddWithDraw;
    @BindView(R.id.btnPay)
    Button btnPay;
    @Inject
    ChargePresenter presenter;
    PrefrencesStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_charge);
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        storage = new PrefrencesStorage(this);
        presenter.setView(this);
        presenter.getBalance(this, storage.getId());
    }


    @OnClick({R.id.ivBack, R.id.btnAddWithDraw, R.id.btnPay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnAddWithDraw:
                break;
            case R.id.btnPay:
                break;
        }
    }

    @Override
    public void getBalance(String totalBalance, String appComm, String remainBalance) {
        if (totalBalance.equals("null") || totalBalance == null) {
            tvCharge.setText("0 SAR");
        } else if (appComm.equals("null") || appComm == null) {
            tvAppComm.setText("0 SAR");
        } else if (remainBalance.equals("null") || remainBalance == null) {
            tvBalance.setText("0 SAR");
        } else {
            tvCharge.setText(totalBalance + " SAR");
            tvAppComm.setText(appComm + " SAR");
            tvBalance.setText(remainBalance + " SAR");
        }

    }
}