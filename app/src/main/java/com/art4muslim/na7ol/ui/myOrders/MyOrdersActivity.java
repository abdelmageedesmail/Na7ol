package com.art4muslim.na7ol.ui.myOrders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrdersActivity extends AppCompatActivity implements MyOrderView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvPending)
    TextView tvPending;
    @BindView(R.id.vPending)
    View vPending;
    @BindView(R.id.liPending)
    LinearLayout liPending;
    @BindView(R.id.tvActive)
    TextView tvActive;
    @BindView(R.id.vActive)
    View vActive;
    @BindView(R.id.liActive)
    LinearLayout liActive;
    @BindView(R.id.tvFinished)
    TextView tvFinished;
    @BindView(R.id.vFinished)
    View vFinished;
    @BindView(R.id.liFinished)
    LinearLayout liFinished;
    @BindView(R.id.rvAgreements)
    RecyclerView rvAgreements;
    @BindView(R.id.noData)
    View noData;
    @Inject
    MyOrderPresenter presenter;
    PrefrencesStorage storage;
    @BindView(R.id.rvMyOrders)
    RecyclerView rvMyOrders;
    @BindView(R.id.tvMyOrders)
    TextView tvMyOrders;
    @BindView(R.id.vMyOrders)
    View vMyOrders;
    @BindView(R.id.liMyOrders)
    LinearLayout liMyOrders;
    @BindView(R.id.tvJoinRequests)
    TextView tvJoinRequests;
    @BindView(R.id.vJoinRequests)
    View vJoinRequests;
    @BindView(R.id.liJoinRequests)
    LinearLayout liJoinRequests;
    public static AppCompatActivity appCompatActivity;
    private String status = "pending";
    private List<AgreementsResponseModel.ReturnsEntity> list;
    private MyAgreementAdapter myOrderAdapter;
    private MyOrderAdapter myTripsAdapter;
    private List<TripsModelResponse.Returns> trips;
    private Dialog dialog;
    private EditText etRate;
    private RatingBar rating;
    private String agr_from_id;
    private String agr_id;
    private String orderType = "myOrders";
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        ButterKnife.bind(this);
        appCompatActivity = this;
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);

        presenter.setView(this);
        storage = new PrefrencesStorage(this);
        list = new ArrayList<>();
        trips = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("status").equals("accepted")) {
                status = "accepted";
                tvActive.setTextColor(Color.BLACK);
                vActive.setBackgroundColor(Color.BLACK);

                tvPending.setTextColor(Color.parseColor("#919191"));
                vPending.setBackgroundColor(Color.parseColor("#919191"));

                tvFinished.setTextColor(Color.parseColor("#919191"));
                vFinished.setBackgroundColor(Color.parseColor("#919191"));
                list.clear();
                if (myOrderAdapter != null) {
                    myOrderAdapter.notifyDataSetChanged();
                }
                presenter.getAgreements(this, status, storage.getId());
                trips.clear();
                if (myTripsAdapter != null) {
                    myTripsAdapter.notifyDataSetChanged();
                }
                rvMyOrders.setVisibility(View.GONE);
            } else if (getIntent().getExtras().getString("status").equals("complete")) {
                status = "complete";
                tvFinished.setTextColor(Color.BLACK);
                vFinished.setBackgroundColor(Color.BLACK);

                tvActive.setTextColor(Color.parseColor("#919191"));
                vActive.setBackgroundColor(Color.parseColor("#919191"));

                tvPending.setTextColor(Color.parseColor("#919191"));
                vPending.setBackgroundColor(Color.parseColor("#919191"));
                list.clear();
                if (myOrderAdapter != null) {
                    myOrderAdapter.notifyDataSetChanged();
                }
                presenter.getAgreements(this, status, storage.getId());
                trips.clear();
                if (myTripsAdapter != null) {
                    myTripsAdapter.notifyDataSetChanged();
                }
                rvMyOrders.setVisibility(View.GONE);
            }
        } else {
            if (status.equals("pending")) {
                presenter.getAds(this, "order", storage.getId(), status);
            } else {
                presenter.getAgreements(this, status, storage.getId());
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }

    }

    private void showPopupOffer() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_rate);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        etRate = dialog.findViewById(R.id.etPrice);
        rating = dialog.findViewById(R.id.rating);
        Button btnSend = dialog.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addRate(MyOrdersActivity.this, storage.getId(), agr_from_id, "" + rating.getRating(), etRate.getText().toString(), agr_id);
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @OnClick({R.id.ivBack, R.id.liPending, R.id.liActive, R.id.liFinished, R.id.liMyOrders, R.id.liJoinRequests})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.liMyOrders:
                orderType = "myOrders";
                status = "pending";
                tvPending.setTextColor(Color.BLACK);
                vPending.setBackgroundColor(Color.BLACK);

                tvMyOrders.setTextColor(Color.BLACK);
                vMyOrders.setBackgroundColor(Color.BLACK);

                tvJoinRequests.setTextColor(Color.parseColor("#919191"));
                vJoinRequests.setBackgroundColor(Color.parseColor("#919191"));

                tvActive.setTextColor(Color.parseColor("#919191"));
                vActive.setBackgroundColor(Color.parseColor("#919191"));

                tvFinished.setTextColor(Color.parseColor("#919191"));
                vFinished.setBackgroundColor(Color.parseColor("#919191"));
                list.clear();

                if (myOrderAdapter != null) {
                    myOrderAdapter.notifyDataSetChanged();
                }
//                presenter.getAgreements(this, status, storage.getId());
                trips.clear();
                if (myTripsAdapter != null) {
                    myTripsAdapter.notifyDataSetChanged();
                }
                rvMyOrders.setVisibility(View.VISIBLE);
                presenter.getAds(this, "order", storage.getId(), status);

                break;
            case R.id.liJoinRequests:
                orderType = "joinRequests";
                status = "pending";
                tvPending.setTextColor(Color.BLACK);
                vPending.setBackgroundColor(Color.BLACK);

                tvJoinRequests.setTextColor(Color.BLACK);
                vJoinRequests.setBackgroundColor(Color.BLACK);

                tvMyOrders.setTextColor(Color.parseColor("#919191"));
                vMyOrders.setBackgroundColor(Color.parseColor("#919191"));

                tvActive.setTextColor(Color.parseColor("#919191"));
                vActive.setBackgroundColor(Color.parseColor("#919191"));

                tvFinished.setTextColor(Color.parseColor("#919191"));
                vFinished.setBackgroundColor(Color.parseColor("#919191"));
                list.clear();
                if (myOrderAdapter != null) {
                    myOrderAdapter.notifyDataSetChanged();
                }
                presenter.getAgreements(this, status, storage.getId());
                trips.clear();
                if (myTripsAdapter != null) {
                    myTripsAdapter.notifyDataSetChanged();
                }
                rvMyOrders.setVisibility(View.GONE);
                break;
            case R.id.liPending:
                status = "pending";


                tvPending.setTextColor(Color.BLACK);
                vPending.setBackgroundColor(Color.BLACK);

                tvActive.setTextColor(Color.parseColor("#919191"));
                vActive.setBackgroundColor(Color.parseColor("#919191"));

                tvFinished.setTextColor(Color.parseColor("#919191"));
                vFinished.setBackgroundColor(Color.parseColor("#919191"));

                trips.clear();
                if (myTripsAdapter != null) {
                    myTripsAdapter.notifyDataSetChanged();
                }

                list.clear();

                if (myOrderAdapter != null) {
                    myOrderAdapter.notifyDataSetChanged();
                }

                if (orderType.equals("myOrders")) {

                    rvMyOrders.setVisibility(View.VISIBLE);
                    presenter.getAds(this, "order", storage.getId(), status);
                } else {


                    rvMyOrders.setVisibility(View.GONE);
                    rvAgreements.setVisibility(View.VISIBLE);
                    presenter.getAgreements(this, status, storage.getId());
                }


                break;
            case R.id.liActive:
                status = "accepted";
                tvActive.setTextColor(Color.BLACK);
                vActive.setBackgroundColor(Color.BLACK);

                tvPending.setTextColor(Color.parseColor("#919191"));
                vPending.setBackgroundColor(Color.parseColor("#919191"));

                tvFinished.setTextColor(Color.parseColor("#919191"));
                vFinished.setBackgroundColor(Color.parseColor("#919191"));
                list.clear();
                if (myOrderAdapter != null) {
                    myOrderAdapter.notifyDataSetChanged();
                }
                presenter.getAgreements(this, status, storage.getId());
                trips.clear();
                if (myTripsAdapter != null) {
                    myTripsAdapter.notifyDataSetChanged();
                }
                rvMyOrders.setVisibility(View.GONE);
//                presenter.getAds(this, "order", status, storage.getId());
                break;
            case R.id.liFinished:
                status = "complete";
                tvFinished.setTextColor(Color.BLACK);
                vFinished.setBackgroundColor(Color.BLACK);

                tvActive.setTextColor(Color.parseColor("#919191"));
                vActive.setBackgroundColor(Color.parseColor("#919191"));

                tvPending.setTextColor(Color.parseColor("#919191"));
                vPending.setBackgroundColor(Color.parseColor("#919191"));
                list.clear();
                if (myOrderAdapter != null) {
                    myOrderAdapter.notifyDataSetChanged();
                }
                presenter.getAgreements(this, status, storage.getId());
                trips.clear();
                if (myTripsAdapter != null) {
                    myTripsAdapter.notifyDataSetChanged();
                }
                rvMyOrders.setVisibility(View.GONE);
//                presenter.getAds(this, "order", status, storage.getId());
                break;
        }
    }

    @Override
    public void getAgreements(final List<AgreementsResponseModel.ReturnsEntity> returns) {
        list = returns;
        if (list != null) {
            if (list.size() > 0) {
                noData.setVisibility(View.GONE);
                myOrderAdapter = new MyAgreementAdapter(this, list, orderType);
                rvAgreements.setAdapter(myOrderAdapter);
                rvAgreements.setLayoutManager(new LinearLayoutManager(this));
//                rvAgreements.setNestedScrollingEnabled(false);
                myOrderAdapter.notifyDataSetChanged();
                myOrderAdapter.setUpdateAgreement(new MyAgreementAdapter.UpdateAgreement() {
                    @Override
                    public void onUpdateAgreement(int pos) {
                        position = pos;
                        presenter.updateAgreementStatus(MyOrdersActivity.this, storage.getId(), returns.get(pos).getAgr_id(), "rejected");
                    }
                });

                myOrderAdapter.setAddRate(new MyAgreementAdapter.AddRate() {
                    @Override
                    public void onAddRate(int pos) {
                        if (storage.getId().equals(list.get(pos).getAgr_from_id())) {
                            agr_from_id = list.get(pos).getAgr_to_id();
                        } else {
                            agr_from_id = list.get(pos).getAgr_from_id();
                        }

                        agr_id = list.get(pos).getAgr_id();
                        showPopupOffer();
                    }
                });
            } else {
                noData.setVisibility(View.VISIBLE);
            }

        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getTrips(final List<TripsModelResponse.Returns> returns) {
//        presenter.getAgreements(this, status, storage.getId());
        trips = returns;
        if (trips.size() > 0) {
            noData.setVisibility(View.GONE);

            myTripsAdapter = new MyOrderAdapter(this, trips);
            rvMyOrders.setAdapter(myTripsAdapter);
            rvMyOrders.setLayoutManager(new LinearLayoutManager(this));
            rvMyOrders.setNestedScrollingEnabled(false);
            myTripsAdapter.notifyDataSetChanged();
            myTripsAdapter.setUpdateTrip(new MyOrderAdapter.UpdateTrip() {
                @Override
                public void onCancelClick(int pos) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyOrdersActivity.this)
                            .setMessage(getString(R.string.doYouWantToCancel))
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    presenter.updateTripStatus(MyOrdersActivity.this, "" + returns.get(pos).getServ_id(), storage.getId(), "canceled");
                                }
                            }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    alertDialog.show();

                }
            });
        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getTerms(List<TermsResponseModel.ReturnsEntity> returns) {

    }

    @Override
    public void isSent(boolean b) {

    }

    @Override
    public void isCanceled(boolean b) {
        if (b) {
            Toast.makeText(this, getString(R.string.youCanceledTripSuccessfully), Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        } else {
            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void isUpdated(boolean b, String status) {
        if (b) {
            if (status.equals("canceled")) {
                Toast.makeText(this, getString(R.string.youCanceledTripSuccessfully), Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            } else if (status.equals("rejected")) {
                Toast.makeText(appCompatActivity, getString(R.string.joinRequestsCanceled), Toast.LENGTH_SHORT).show();
                list.remove(position);
                myOrderAdapter.notifyDataSetChanged();
//or use this for better perfomance.
                myOrderAdapter.notifyItemRemoved(position);
            }
        } else {
            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void isRated(boolean b) {
        if (b) {
            Toast.makeText(this, getString(R.string.thanks), Toast.LENGTH_SHORT).show();
            startActivity(getIntent());
            finish();
        } else {
            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getUserRate(List<RateResponse.ReturnsEntity> returns) {

    }

    @Override
    public void codeSent(boolean b) {

    }
}