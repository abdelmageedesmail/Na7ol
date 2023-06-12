package com.art4muslim.na7ol.ui.myTrips;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.text.Editable;
import android.text.TextWatcher;
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
import com.art4muslim.na7ol.ui.myOrders.MyOrderPresenter;
import com.art4muslim.na7ol.ui.myOrders.MyOrderView;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.ui.tripFragment.TripAdapter;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTrips extends AppCompatActivity implements MyOrderView {

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
    @BindView(R.id.rvTrips)
    RecyclerView rvTrips;
    @BindView(R.id.noData)
    View noData;
    @Inject
    MyOrderPresenter presenter;
    PrefrencesStorage storage;
    @BindView(R.id.tvMyTrips)
    TextView tvMyTrips;
    @BindView(R.id.vMyTrips)
    View vMyTrips;
    @BindView(R.id.liMyTrips)
    LinearLayout liMyTrips;
    @BindView(R.id.tvJoinRequests)
    TextView tvJoinRequests;
    @BindView(R.id.vJoinRequests)
    View vJoinRequests;
    @BindView(R.id.liJoinRequests)
    LinearLayout liJoinRequests;
    //    @BindView(R.id.rvTypes)
//    RecyclerView rvTypes;
    private String status = "pending";
    private List<AgreementsResponseModel.ReturnsEntity> list;
    private MyTripsAgreementAdapter myOrderAdapter;
    private List<TripsModelResponse.Returns> trips = new ArrayList<>();
    private EditText et1, et2, et3, et4, et5, et6, etRate;
    RatingBar rating;
    private String agrId;
    private Dialog dialog;
    private String agr_to_id;
    private EditText etCode;
    private MyTripsAdapter myTripsAdapter;
    private String type = "myOrders";
    private String tripId;
    private String from;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);

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
        storage = new PrefrencesStorage(this);
        list = new ArrayList<>();
        if (getIntent() != null) {
            if (getIntent().getExtras().getString("type").equals("myTrips")) {
                if (status.equals("pending")) {
                    presenter.getAds(this, "offer", storage.getId(), status);
                } else if (getIntent().getExtras().getString("status") != null) {
                    if (getIntent().getExtras().getString("status").equals("done")) {
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
                        rvTrips.setVisibility(View.GONE);
                        presenter.getTripsAgreements(this, status, storage.getId());
                    }
                } else {
                    presenter.getTripsAgreements(this, status, storage.getId());
                }

            } else {
                presenter.getTripsAgreements(this, "pending", storage.getId());
            }
        }


    }

    @OnClick({R.id.ivBack, R.id.liPending, R.id.liActive, R.id.liFinished, R.id.liMyTrips, R.id.liJoinRequests})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;

            case R.id.liMyTrips:
                type = "myOrders";
                status = "pending";
                tvPending.setTextColor(Color.BLACK);
                vPending.setBackgroundColor(Color.BLACK);

                tvMyTrips.setTextColor(Color.BLACK);
                vMyTrips.setBackgroundColor(Color.BLACK);

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
                rvTrips.setVisibility(View.VISIBLE);
                presenter.getAds(this, "offer", storage.getId(), status);

                break;
            case R.id.liJoinRequests:
                type = "joinRequests";
                status = "pending";
                tvPending.setTextColor(Color.BLACK);
                vPending.setBackgroundColor(Color.BLACK);

                tvJoinRequests.setTextColor(Color.BLACK);
                vJoinRequests.setBackgroundColor(Color.BLACK);

                tvMyTrips.setTextColor(Color.parseColor("#919191"));
                vMyTrips.setBackgroundColor(Color.parseColor("#919191"));

                tvActive.setTextColor(Color.parseColor("#919191"));
                vActive.setBackgroundColor(Color.parseColor("#919191"));

                tvFinished.setTextColor(Color.parseColor("#919191"));
                vFinished.setBackgroundColor(Color.parseColor("#919191"));
                list.clear();
                if (myOrderAdapter != null) {
                    myOrderAdapter.notifyDataSetChanged();
                }
//                presenter.getAgreements(this, status, storage.getId());
                presenter.getTripsAgreements(this, "pending", storage.getId());

                trips.clear();
                if (myTripsAdapter != null) {
                    myTripsAdapter.notifyDataSetChanged();
                }
                rvTrips.setVisibility(View.GONE);
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
                rvTrips.setVisibility(View.VISIBLE);
                list.clear();
                if (myOrderAdapter != null) {
                    myOrderAdapter.notifyDataSetChanged();
                }
//                presenter.getTripsAgreements(this, status, storage.getId());
                if (type.equals("myOrders")) {
                    presenter.getAds(this, "offer", storage.getId(), status);
                } else {
//                    presenter.getAgreements(this, status, storage.getId());
                    presenter.getTripsAgreements(this, "pending", storage.getId());

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
                rvTrips.setVisibility(View.GONE);
                presenter.getTripsAgreements(this, status, storage.getId());
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
                rvTrips.setVisibility(View.GONE);
                presenter.getTripsAgreements(this, status, storage.getId());
                break;
        }
    }


    private void popupCode() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_code);
        et1 = dialog.findViewById(R.id.et1);
        et2 = dialog.findViewById(R.id.et2);
        et3 = dialog.findViewById(R.id.et3);
        et4 = dialog.findViewById(R.id.et4);
        etCode = dialog.findViewById(R.id.etCode);
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
                if (et1.getText().toString().isEmpty()) {
                    et1.setError(getString(R.string.empty));
                } else if (et2.getText().toString().isEmpty()) {
                    et2.setError(getString(R.string.empty));
                } else if (et3.getText().toString().isEmpty()) {
                    et3.setError(getString(R.string.empty));
                } else if (et4.getText().toString().isEmpty()) {
                    et4.setError(getString(R.string.empty));
                } else {
                    presenter.finishTrip(MyTrips.this, storage.getId(), agrId, "complete", et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString());
                }

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
                presenter.addRate(MyTrips.this, storage.getId(), agr_to_id, "" + rating.getRating(), etRate.getText().toString(), agrId);
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

    @Override
    public void getAgreements(final List<AgreementsResponseModel.ReturnsEntity> returns) {
        list = returns;
        if (list != null) {
            if (list.size() > 0) {
                noData.setVisibility(View.GONE);
                myOrderAdapter = new MyTripsAgreementAdapter(this, list);
                rvAgreements.setAdapter(myOrderAdapter);
                rvAgreements.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                rvAgreements.setNestedScrollingEnabled(false);
                myOrderAdapter.onItemClick(new TripAdapter.ItemClick() {
                    @Override
                    public void onItemClick(int pos) {
                        agrId = returns.get(pos).getAgr_id();
                        tripId = returns.get(pos).getServ_id();
                        if (storage.getId().equals(list.get(pos).getAgr_to_id())) {
                            agr_to_id = returns.get(pos).getAgr_from_id();
                        } else {
                            agr_to_id = returns.get(pos).getAgr_to_id();
                        }
                        presenter.sendCode(MyTrips.this, agrId);
                    }
                });

                myOrderAdapter.setEditPriceOffer(new MyTripsAgreementAdapter.EditPriceOffer() {
                    @Override
                    public void onEditPriceOffer(int pos, String type) {
                        position = pos;
                        if (type.equals("cancel")) {
                            from = "agreement";
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyTrips.this)
                                    .setMessage(getString(R.string.doYouWantToCancel))
                                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            presenter.updateAgreementStatus(MyTrips.this, storage.getId(), "" + returns.get(pos).getAgr_id(), "canceled");
                                        }
                                    }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                            alertDialog.show();


                        } else {
                            agrId = returns.get(pos).getAgr_id();
                            tripId = returns.get(pos).getServ_id();
                            if (storage.getId().equals(list.get(pos).getAgr_to_id())) {
                                agr_to_id = returns.get(pos).getAgr_from_id();
                            } else {
                                agr_to_id = returns.get(pos).getAgr_from_id();
                            }
                            showPopupPriceOffer();
                        }
                    }
                });


                myOrderAdapter.setAddRate(new MyTripsAgreementAdapter.AddRate() {
                    @Override
                    public void onAddRate(int pos) {
                        agrId = returns.get(pos).getAgr_id();
                        if (storage.getId().equals(list.get(pos).getAgr_to_id())) {
                            agr_to_id = returns.get(pos).getAgr_from_id();
                        } else {
                            agr_to_id = returns.get(pos).getAgr_from_id();
                        }


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

    private void showPopupPriceOffer() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_offer);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        final EditText etPrice = dialog.findViewById(R.id.etPrice);
        Button btnSend = dialog.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.updateTripAgreementCost(MyTrips.this, agrId, tripId, agr_to_id, storage.getId(), etPrice.getText().toString());
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

    @Override
    public void getTrips(final List<TripsModelResponse.Returns> returns) {
        trips = returns;
        if (trips != null) {
            if (trips.size() > 0) {
                myTripsAdapter = new MyTripsAdapter(this, trips);
                rvTrips.setAdapter(myTripsAdapter);
                rvTrips.setLayoutManager(new LinearLayoutManager(this));
                rvTrips.setNestedScrollingEnabled(false);
                myTripsAdapter.setUpdateTrip(new MyTripsAdapter.UpdateTrip() {
                    @Override
                    public void onCancelClick(int pos) {

                        position = pos;
                        from = "trip";
//                        Toast.makeText(MyTrips.this, "cancellll", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyTrips.this)
                                .setMessage(getString(R.string.doYouWantToCancel))
                                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        presenter.updateTripStatus(MyTrips.this, "" + returns.get(pos).getServ_id(), storage.getId(), "canceled");
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
        if (status.equals("complete")) {
            if (b) {
                showPopupOffer();
            }
        } else if (status.equals("canceled")) {
            if (b) {
                Toast.makeText(this, getString(R.string.tripCanceledSuccessfully), Toast.LENGTH_SHORT).show();
                if (from.equals("agreement")) {
                    list.remove(position);
                    myOrderAdapter.notifyDataSetChanged();
                    myOrderAdapter.notifyItemRemoved(position);
                } else {
                    trips.remove(position);
                    myTripsAdapter.notifyDataSetChanged();
                    myTripsAdapter.notifyItemRemoved(position);
                }

            }

        } else if (status.equals("askForAccept")) {
            Toast.makeText(this, getString(R.string.bidUpdated), Toast.LENGTH_SHORT).show();
        } else if (status.equals("tripUpdated")) {
            if (b) {
                Toast.makeText(this, getString(R.string.bidUpdated), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (status.equals("complete")) {
                Toast.makeText(this, getString(R.string.enterCorrectCode), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void isRated(boolean b) {
        if (b) {
            Toast.makeText(this, getString(R.string.thanks), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MyTrips.class);
            intent.putExtra("type", "myTrips");
            startActivity(intent);
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
        if (b) {
            popupCode();
        }
    }
}