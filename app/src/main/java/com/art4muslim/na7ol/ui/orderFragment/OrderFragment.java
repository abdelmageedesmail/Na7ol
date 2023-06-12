package com.art4muslim.na7ol.ui.orderFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.FragmentOrderBinding;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.DeliveryMethodModel;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.tripFragment.CarTypesAdapter;
import com.art4muslim.na7ol.ui.tripFragment.TripAdapter;
import com.art4muslim.na7ol.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OrderFragment extends Fragment implements View.OnClickListener, TripsView {


    private FragmentOrderBinding binding;
    private String carType;
    @Inject
    TripsPresenter presenter;
    private List<TripsModelResponse.Returns> dataList;
    private OrderAdapter orderAdapter;
    private String carTypeId;
    private CarTypesAdapter carTypesAdapter;
    private String iconUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        View view = binding.getRoot();
        ((Na7ol) getActivity().getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(getActivity()).equals("ar")) {
            Utils.chooseLang(getActivity(), "ar");
        } else if (Utils.getLang(getActivity()).equals("en")) {
            Utils.chooseLang(getActivity(), "en");
        } else {
            Utils.chooseLang(getActivity(), "zh");
        }
        presenter.setView(this);
        presenter.getCarTypes(getActivity());
        dataList = new ArrayList<>();


        binding.liTrain.setOnClickListener(this);
        binding.liCars.setOnClickListener(this);
        binding.liPlan.setOnClickListener(this);
        return view;
    }

    private void carTypeSelection() {
        if (carType.equals("1")) {
            binding.rvTrips.setVisibility(View.VISIBLE);


            binding.tvTrain.setTextColor(Color.BLACK);
            binding.vTrain.setBackgroundColor(Color.BLACK);

            binding.tvCars.setTextColor(Color.parseColor("#919191"));
            binding.vCars.setBackgroundColor(Color.parseColor("#919191"));

            binding.tvPlane.setTextColor(Color.parseColor("#919191"));
            binding.vPlane.setBackgroundColor(Color.parseColor("#919191"));
        } else if (carType.equals("2")) {
            binding.rvTrips.setVisibility(View.VISIBLE);

            binding.tvCars.setTextColor(Color.BLACK);
            binding.vCars.setBackgroundColor(Color.BLACK);

            binding.tvTrain.setTextColor(Color.parseColor("#919191"));
            binding.vTrain.setBackgroundColor(Color.parseColor("#919191"));

            binding.tvPlane.setTextColor(Color.parseColor("#919191"));
            binding.vPlane.setBackgroundColor(Color.parseColor("#919191"));
        } else {
            binding.rvTrips.setVisibility(View.VISIBLE);

            binding.tvPlane.setTextColor(Color.BLACK);
            binding.vPlane.setBackgroundColor(Color.BLACK);

            binding.tvCars.setTextColor(Color.parseColor("#919191"));
            binding.vCars.setBackgroundColor(Color.parseColor("#919191"));

            binding.tvTrain.setTextColor(Color.parseColor("#919191"));
            binding.vTrain.setBackgroundColor(Color.parseColor("#919191"));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.liTrain:
                carType = "1";
                binding.rvTrips.setVisibility(View.VISIBLE);
                binding.tvTrain.setTextColor(Color.BLACK);
                binding.vTrain.setBackgroundColor(Color.BLACK);

                binding.tvCars.setTextColor(Color.parseColor("#919191"));
                binding.vCars.setBackgroundColor(Color.parseColor("#919191"));

                binding.tvPlane.setTextColor(Color.parseColor("#919191"));
                binding.vPlane.setBackgroundColor(Color.parseColor("#919191"));
                presenter.getAds(getActivity(), "order", carType,"");
                dataList.clear();
                if (orderAdapter != null) {
                    orderAdapter.notifyDataSetChanged();
                }

                break;
            case R.id.liCars:
                carType = "2";
                binding.rvTrips.setVisibility(View.VISIBLE);
                binding.tvCars.setTextColor(Color.BLACK);
                binding.vCars.setBackgroundColor(Color.BLACK);

                binding.tvTrain.setTextColor(Color.parseColor("#919191"));
                binding.vTrain.setBackgroundColor(Color.parseColor("#919191"));

                binding.tvPlane.setTextColor(Color.parseColor("#919191"));
                binding.vPlane.setBackgroundColor(Color.parseColor("#919191"));
                presenter.getAds(getActivity(), "order", carType,"");
                dataList.clear();
                if (orderAdapter != null) {
                    orderAdapter.notifyDataSetChanged();
                }

                break;
            case R.id.liPlan:
                carType = "3";
                binding.rvTrips.setVisibility(View.GONE);

                binding.tvPlane.setTextColor(Color.BLACK);
                binding.vPlane.setBackgroundColor(Color.BLACK);

                binding.tvCars.setTextColor(Color.parseColor("#919191"));
                binding.vCars.setBackgroundColor(Color.parseColor("#919191"));

                binding.tvTrain.setTextColor(Color.parseColor("#919191"));
                binding.vTrain.setBackgroundColor(Color.parseColor("#919191"));

                binding.rvTrips.setVisibility(View.VISIBLE);

                presenter.getAds(getActivity(), "order", carType,"");
                dataList.clear();
                if (orderAdapter != null) {
                    orderAdapter.notifyDataSetChanged();
                }
                break;

        }
    }

    @Override
    public void getTrips(List<TripsModelResponse.Returns> list, boolean canLoadMore) {
        dataList.clear();
        dataList = list;
        if (dataList != null) {
            if (dataList.size() > 0) {
                binding.rvTrips.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
                orderAdapter = new OrderAdapter(getActivity(), dataList, iconUrl);
                binding.rvTrips.setAdapter(orderAdapter);
                orderAdapter.notifyDataSetChanged();
                binding.rvTrips.setLayoutManager(new LinearLayoutManager(getActivity()));
            } else {
//                dataList.clear();
                binding.rvTrips.setVisibility(View.GONE);
                orderAdapter = new OrderAdapter(getActivity(), dataList, iconUrl);
                orderAdapter.notifyDataSetChanged();
                binding.noData.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void getFliterTrips(List<TripsModelResponse.Returns> list, boolean canLoadMore) {
        dataList.clear();
        dataList = list;
        if (dataList != null) {
            if (dataList.size() > 0) {
                binding.rvTrips.setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.GONE);
                orderAdapter = new OrderAdapter(getActivity(), dataList, iconUrl);
                binding.rvTrips.setAdapter(orderAdapter);
                orderAdapter.notifyDataSetChanged();
                binding.rvTrips.setLayoutManager(new LinearLayoutManager(getActivity()));
            } else {
//                dataList.clear();
                binding.rvTrips.setVisibility(View.GONE);
                orderAdapter = new OrderAdapter(getActivity(), dataList, iconUrl);
                orderAdapter.notifyDataSetChanged();
                binding.noData.setVisibility(View.VISIBLE);
            }
        }else{
                binding.rvTrips.setVisibility(View.GONE);
                orderAdapter = new OrderAdapter(getActivity(), dataList, iconUrl);
                orderAdapter.notifyDataSetChanged();
                binding.noData.setVisibility(View.VISIBLE);
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
            String servType = getArguments().getString("servType");
            String weightId = getArguments().getString("weightId");
//            carTypeSelection();
            dataList.clear();
            if (orderAdapter != null) {
                orderAdapter.notifyDataSetChanged();
            }
            presenter.filterAds(getActivity(), "" + toCountryID, "" + fromCityId, "" + toCityID, "" + fromCountryId, carType, "", "", servType, "order", weightId);
        } else {
            carType = returns.get(0).getCartype_id();
            presenter.getAds(getActivity(), "order", returns.get(0).getCartype_id(),"");
        }
        if (carTypeId != null) {
            carTypesAdapter = new CarTypesAdapter(getActivity(), returns, carTypeId);
        } else {
            carTypesAdapter = new CarTypesAdapter(getActivity(), returns, "");
        }

        binding.rvTypes.setAdapter(carTypesAdapter);
        binding.rvTypes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        carTypesAdapter.setCarTypeClick(new CarTypesAdapter.CarTypeClick() {
            @Override
            public void onCarTypeClick(int pos) {
                carType = returns.get(pos).getCartype_id();
                iconUrl = returns.get(pos).getIconUrl();

                dataList.clear();
                if (orderAdapter != null) {
                    orderAdapter.notifyDataSetChanged();
                }
                presenter.getAds(getActivity(), "order", returns.get(pos).getCartype_id(),"");
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
