package com.art4muslim.na7ol.ui.joinRequests;

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
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.priceOffers.PriceOfferPresenter;
import com.art4muslim.na7ol.ui.priceOffers.PriceOfferView;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinRequestsActivity extends AppCompatActivity implements PriceOfferView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvJoinRequests)
    RecyclerView rvJoinRequests;
    @BindView(R.id.noData)
    View noData;
    @Inject
    PriceOfferPresenter presenter;
    PrefrencesStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_requests);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        storage = new PrefrencesStorage(this);
        presenter.getAgreements(this, getIntent().getExtras().getString("servId"));
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
                presenter.updateAgreementStatus(JoinRequestsActivity.this, storage.getId(), agrId, "accepted");
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
        JoinRequestAdapter joinRequestAdapter = new JoinRequestAdapter(this, returns);
        rvJoinRequests.setAdapter(joinRequestAdapter);
        rvJoinRequests.setLayoutManager(new LinearLayoutManager(this));
        joinRequestAdapter.setPriceOfferClick(new JoinRequestAdapter.PriceOfferClick() {
            @Override
            public void onAcceptOfferClick(int position) {
                popUpAcceptJoin(returns.get(position).getAgr_id());
            }
        });
    }

    @Override
    public void isAccepted(boolean b) {
        if (b) {
            Toast.makeText(this, getString(R.string.youAcceptToDeliver), Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}