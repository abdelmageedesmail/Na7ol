package com.art4muslim.na7ol.ui.tripFragment;

import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import android.text.PrecomputedText;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.FragmentTripBinding;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.DeliveryMethodModel;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.ui.orderFragment.TripsPresenter;
import com.art4muslim.na7ol.ui.orderFragment.TripsView;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class TripFragment extends Fragment implements View.OnClickListener, TripsView {

    private FragmentTripBinding binding;
    private Dialog dialog;
    private TripAdapter tripAdapter2;
    boolean isFiltered;
    @Inject
    TripsPresenter presenter;
    private String carType;
    private List<TripsModelResponse.Returns> dataList = new ArrayList<>();
    private TripAdapter tripAdapter1;
    PrefrencesStorage storage;
    public String carTypeId;
    private String iconUrl;
    RecyclerView rvTrips;
    private String type = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip, container, false);
        View view = binding.getRoot();
        ((Na7ol) getActivity().getApplication()).getApplicationComponent().inject(this);
        rvTrips = view.findViewById(R.id.rvTrips);
        if (Utils.getLang(getActivity()).equals("ar")) {
            Utils.chooseLang(getActivity(), "ar");
        } else if (Utils.getLang(getActivity()).equals("en")) {
            Utils.chooseLang(getActivity(), "en");
        } else {
            Utils.chooseLang(getActivity(), "zh");
        }
        presenter.setView(this);
        storage = new PrefrencesStorage(getActivity());
        presenter.getCarTypes(getActivity());
        binding.liTrain.setOnClickListener(this);
        binding.liCars.setOnClickListener(this);
        binding.liPlan.setOnClickListener(this);
        binding.liInnerFlight.setOnClickListener(this);
        binding.liForignFlight.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tripAdapter1 = new TripAdapter(getActivity(), dataList, iconUrl);
        rvTrips.setAdapter(tripAdapter1);
        rvTrips.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivClose:
                dialog.dismiss();
                break;
            case R.id.btnAddOffer:
                dialog.dismiss();
                break;
            case R.id.liInnerFlight:
                binding.liTrainType.setVisibility(View.GONE);
                type = "national";
                dataList.clear();
                if (tripAdapter1 != null) {
                    tripAdapter1.notifyDataSetChanged();
                }
                presenter.getTripFlight(getActivity(), "offer", carType, type);
                break;
            case R.id.liForignFlight:
                binding.liTrainType.setVisibility(View.GONE);
                type = "international";
                dataList.clear();
                if (tripAdapter1 != null) {
                    tripAdapter1.notifyDataSetChanged();
                }
                presenter.getTripFlight(getActivity(), "offer", carType, type);
                break;
        }
    }

    private void showPopupOffer(final TripsModelResponse.Returns returns) {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_trip_details);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        Button btnAddOffer = dialog.findViewById(R.id.btnAddOffer);
        TextView tvFrom = dialog.findViewById(R.id.tvFrom);
        TextView tvTo = dialog.findViewById(R.id.tvTo);
        TextView tvWeight = dialog.findViewById(R.id.tvWeight);
        TextView tvToTime = dialog.findViewById(R.id.tvToTime);
        TextView tvTimeFrom = dialog.findViewById(R.id.tvTimeFrom);
        TextView tvDetails = dialog.findViewById(R.id.tvDetails);
        tvFrom.setText(returns.getFrom_city_name());
        tvTo.setText(returns.getTo_city_name());
        tvWeight.setText(returns.getServ_weight_name() + " KG");
        tvToTime.setText(returns.getServ_to_time());
        String dateFormatFrom = getDateFormat(returns.getServ_from_time());
        String dateFormatTo = getDateFormat(returns.getServ_to_time());
        tvTimeFrom.setText(dateFormatFrom);
        tvToTime.setText(dateFormatTo);
//        tvDetails.setText(returns.getno());
        tvFrom.setText(returns.getFrom_city_name());
        btnAddOffer.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        btnAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (storage.getId().equals(returns.getServ_driver_id())) {
                    Toast.makeText(getActivity(), getString(R.string.youCannotJoinToyourTrip), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), JoinRequestActivity.class);
                    intent.putExtra("servId", "" + returns.getServ_id());
                    intent.putExtra("weight", returns.getServWeight());
                    intent.putExtra("from", "add");
                    if (!returns.getServ_driver_id().isEmpty()) {
                        intent.putExtra("fromId", "" + returns.getServ_driver_id());
                    } else {
                        intent.putExtra("fromId", "" + returns.getServ_user_id());
                    }

                    startActivity(intent);
                }
            }
        });
        dialog.show();
    }


    private String getDateFormat(String stringDate) {
        String dateText = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date = format.parse(stringDate);
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
            String day = (String) DateFormat.format("dd", date); // 20
            String monthString = (String) DateFormat.format("MMM", date); // Jun
            String monthNumber = (String) DateFormat.format("MM", date); // 06
            String year = (String) DateFormat.format("yyyy", date); // 2013
            if (Utils.getLang(getActivity()).equals("ar")) {
                dateText = day + " " + monthString + " " + year;
            } else {
                dateText = day + " " + monthString + " " + year;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateText;
    }


    @Override
    public void getTrips(final List<TripsModelResponse.Returns> list, boolean canLoadMore) {
        dataList.clear();
        isFiltered = false;
        dataList = list;
        if (list != null) {
            if (dataList.size() > 0) {
                rvTrips.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
                tripAdapter1 = new TripAdapter(getActivity(), dataList, iconUrl);
                rvTrips.setAdapter(tripAdapter1);
                tripAdapter1.notifyDataSetChanged();
                rvTrips.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                tripAdapter1.onItemClick(new TripAdapter.ItemClick() {
                    @Override
                    public void onItemClick(int pos) {

                        TripsModelResponse.Returns returns = dataList.get(pos);
                        showPopupOffer(returns);

                    }
                });


            } else {
                isFiltered = true;
                dataList.clear();
                int size = dataList.size();
                dataList.clear();

                rvTrips.setVisibility(View.GONE);
                binding.noData.setVisibility(View.VISIBLE);

            }
        }
    }


    @Override
    public void getFliterTrips(final List<TripsModelResponse.Returns> list, boolean canLoadMore) {
        dataList.clear();
        dataList = list;
        if (dataList != null) {
            if (dataList.size() > 0) {
                binding.rvTrips.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
                tripAdapter1 = new TripAdapter(getActivity(), dataList, iconUrl);
                binding.rvTrips.setAdapter(tripAdapter1);
                tripAdapter1.notifyDataSetChanged();
                binding.rvTrips.setLayoutManager(new LinearLayoutManager(getActivity()));
                tripAdapter1.onItemClick(new TripAdapter.ItemClick() {
                    @Override
                    public void onItemClick(int pos) {
                        TripsModelResponse.Returns returns = dataList.get(pos);
                        showPopupOffer(returns);
                    }
                });

            } else {
                dataList.clear();
                binding.rvTrips.setVisibility(View.GONE);

                tripAdapter1 = new TripAdapter(getActivity(), dataList, iconUrl);
                tripAdapter1.notifyDataSetChanged();
                binding.rvTrips.setAdapter(tripAdapter1);
                binding.noData.setVisibility(View.VISIBLE);
                tripAdapter1.onItemClick(new TripAdapter.ItemClick() {
                    @Override
                    public void onItemClick(int pos) {

                    }
                });
            }
        } else {
            Toast.makeText(getActivity(), ".." + dataList.size(), Toast.LENGTH_SHORT).show();
            dataList.clear();
            binding.rvTrips.setVisibility(View.GONE);
            tripAdapter1 = new TripAdapter(getActivity(), dataList, iconUrl);
            tripAdapter1.notifyDataSetChanged();
            binding.rvTrips.setAdapter(tripAdapter1);
            binding.noData.setVisibility(View.VISIBLE);
            tripAdapter1.onItemClick(new TripAdapter.ItemClick() {
                @Override
                public void onItemClick(int pos) {

                }
            });
        }
    }

    @Override
    public void isJoined(boolean b) {

    }

    @Override
    public void getMethods(List<DeliveryMethodModel.ReturnsEntity> returns) {

    }

    @Override
    public void getCarTypes(final List<CarTypeResponse.ReturnsEntity> returns) {
        iconUrl = returns.get(0).getIconUrl();
        if (getArguments() != null) {
            String fromCountryId = getArguments().getString("fromCountryId");
            String toCountryID = getArguments().getString("toCountryID");
            carTypeId = getArguments().getString("carType");
            carType = carTypeId;
            String fromCityId = getArguments().getString("fromCityId");
            String toCityID = getArguments().getString("toCityID");
            String from_time = getArguments().getString("from_time");
            String toTime = getArguments().getString("toTime");

            dataList.clear();
            tripAdapter1 = new TripAdapter(getActivity(), dataList, iconUrl);
            if (tripAdapter1 != null) {
                tripAdapter1.notifyDataSetChanged();
            }
            isFiltered = true;
            presenter.filterAds(getActivity(), "" + toCountryID, "" + fromCityId, "" + toCityID, "" + fromCountryId, carTypeId, from_time, toTime, "", "offer", "");
        } else {
            carType = returns.get(0).getCartype_id();
            presenter.getTripFlight(getActivity(), "offer", returns.get(0).getCartype_id(), "");
        }
        CarTypesAdapter carTypesAdapter;
        if (carTypeId != null) {
            carTypesAdapter = new CarTypesAdapter(getActivity(), returns, carTypeId);
        } else {
            carTypesAdapter = new CarTypesAdapter(getActivity(), returns, "");
        }

        binding.rvCarTypes.setAdapter(carTypesAdapter);
        binding.rvCarTypes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        carTypesAdapter.setCarTypeClick(new CarTypesAdapter.CarTypeClick() {
            @Override
            public void onCarTypeClick(int pos) {
                carType = returns.get(pos).getCartype_id();
                iconUrl = returns.get(pos).getIconUrl();
                dataList.clear();
                if (tripAdapter1 != null) {
                    tripAdapter1.notifyDataSetChanged();
                }
                if (returns.get(pos).getCartype_name().equals("طيران") || returns.get(pos).getCartype_name().equals("plane")) {
                    binding.liTrainType.setVisibility(View.VISIBLE);
                    binding.rvTrips.setVisibility(View.GONE);
                } else {
                    binding.liTrainType.setVisibility(View.GONE);
                    binding.rvTrips.setVisibility(View.VISIBLE);
                    presenter.getTripFlight(getActivity(), "offer", returns.get(pos).getCartype_id(), "");

                }
            }
        });
    }

    @Override
    public void getOrderTypes(List<OrderTypeResponse.ReturnsEntity> returns) {

    }

    @Override
    public void getWeights(List<WeightsResponse.ReturnsEntity> returns) {

    }

}
