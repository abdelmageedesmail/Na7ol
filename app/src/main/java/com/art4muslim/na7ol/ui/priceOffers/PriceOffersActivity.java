package com.art4muslim.na7ol.ui.priceOffers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.joinRequests.JoinRequestsActivity;
import com.art4muslim.na7ol.ui.myOrders.TransferToAccountActivity;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PriceOffersActivity extends AppCompatActivity implements PriceOfferView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvPriceOffers)
    RecyclerView rvPriceOffers;
    @BindView(R.id.noData)
    View noData;
    @Inject
    PriceOfferPresenter presenter;
    PrefrencesStorage storage;
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_offers);
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
        presenter.getAgreements(this, getIntent().getExtras().getString("servId"));
        storage = new PrefrencesStorage(this);
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        onBackPressed();
    }

    private void popUpAcceptJoin(final String agrId) {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.popup_accept_join_request);
        Button btnAccept = dialog.findViewById(R.id.btnAccept);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(PriceOffersActivity.this, TransferToAccountActivity.class);
                intent.putExtra("billAmount", price);
                intent.putExtra("agrId", agrId);
                intent.putExtra("from", "priceOffer");
                startActivity(intent);
                presenter.updateAgreementStatus(PriceOffersActivity.this, storage.getId(), agrId, "accepted");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void getAgreements(final List<AgreementsResponseModel.ReturnsEntity> returns) {
        if (returns.size() > 0) {
            noData.setVisibility(View.GONE);
            PriceOfferAdapter priceOfferAdapter = new PriceOfferAdapter(this, returns);
            rvPriceOffers.setAdapter(priceOfferAdapter);
            rvPriceOffers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            priceOfferAdapter.setPriceOfferClick(new PriceOfferAdapter.PriceOfferClick() {
                @Override
                public void onAcceptOfferClick(int position) {
                    price = returns.get(position).getAgr_cost();
                    popUpAcceptJoin(returns.get(position).getAgr_id());
                }
            });
        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void isAccepted(boolean b) {
        if (b) {
            Toast.makeText(this, getString(R.string.priceOfferAccepted), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
        }
    }
}