package com.art4muslim.na7ol.ui.notificationSettings;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ActivityAddTripBinding;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.NotificationSettingResponse;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.addShipment.AddShipment;
import com.art4muslim.na7ol.ui.addShipment.MultiOrderTypesAdapter;
import com.art4muslim.na7ol.ui.addShipment.OrderTypesAdapter;
import com.art4muslim.na7ol.ui.addShipment.WeightsSpinnerAdapter;
import com.art4muslim.na7ol.ui.add_trip.CarTypesAdapter;
import com.art4muslim.na7ol.ui.add_trip.CitiesAdapter;
import com.art4muslim.na7ol.ui.add_trip.CountriesAdapter;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationsSettings extends AppCompatActivity implements NotificationView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.swTrips)
    Switch swTrips;
    @BindView(R.id.swOrders)
    Switch swOrders;
    @Inject
    NotificationPresenter presenter;
    PrefrencesStorage storage;
    @BindView(R.id.rvTypes)
    RecyclerView rvTypes;
    @BindView(R.id.etDeparturePoint)
    EditText etDeparturePoint;
    @BindView(R.id.etArrivalPoint)
    EditText etArrivalPoint;
    @BindView(R.id.spTripType)
    Spinner spTripType;
    @BindView(R.id.liTripType)
    LinearLayout liTripType;
    @BindView(R.id.rvOrderTypes)
    RecyclerView rvOrderTypes;
    @BindView(R.id.etWeight)
    Spinner etWeight;
    @BindView(R.id.btnSave)
    Button btnSave;
    private String isTrip = "1";
    private String isOrder = "1";

    private RecyclerView rvCities;
    private CountriesAdapter countriesAdapter;
    private List<CountriesResponse.Return> countriesList;
    private Dialog dialog;
    private List<CitiesResponse.Return> citiesList;
    private CitiesAdapter citiesAdapter;
    private String type;
    private int fromCityID;
    private int toCityID;
    private int toCountryID, fromCountryID;
    private String carType;
    private String typeOrder;
    private String servTypeID;

    private String weightId;
    private String tripType;
    private List<CitiesResponse.Return> models = new ArrayList<>();
    private List<CountriesResponse.Return> countriesModels = new ArrayList<>();
    private SearchView searchView;
    private String adv_not_car_type;
    public static List<String> arryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_settings);
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
        presenter.getNotificationSettings(this, storage.getId());

        if (swOrders.isChecked()) {
            isOrder = "1";
        } else {
            isOrder = "0";
        }
        if (swTrips.isChecked()) {
            isTrip = "1";
        } else {
            isTrip = "0";
        }
        swTrips.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isTrip = "1";
                    swTrips.setChecked(true);
                } else {
                    isTrip = "0";
                    swTrips.setChecked(false);
                }
//                presenter.updateNotifications(NotificationsSettings.this, storage.getId(), isTrip, isOrder);
            }
        });
        swOrders.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isOrder = "1";
                    swOrders.setChecked(true);

                } else {
                    isOrder = "0";
                    swOrders.setChecked(false);
                }
//                presenter.updateNotifications(NotificationsSettings.this, storage.getId(), isTrip, isOrder);
            }
        });

    }

    @OnClick(R.id.btnSave)
    public void saveChanges() {
        servTypeID = "";
        for (String s : MultiOrderTypesAdapter.orderTypes) {
            if (MultiOrderTypesAdapter.orderTypes.size() > 1) {
                servTypeID += s + "|";
            } else {
                servTypeID = s;
            }

        }
        presenter.updateNotifications(NotificationsSettings.this, storage.getId(), isTrip, isOrder, carType, "" + fromCityID, "" + toCityID, servTypeID, weightId);
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void updateNotification(boolean isUpdated) {
        if (isUpdated) {
            Toast.makeText(this, getString(R.string.NotificationIsUpdated), Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopupCountries() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_city);
        searchView = dialog.findViewById(R.id.searchView);
        rvCities = dialog.findViewById(R.id.rvCities);
        presenter.getCountries(this);
        dialog.show();
    }

    private void getSearchResult(final String from) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (from.equals("countries")) {
                    List<CountriesResponse.Return> countriesAds = findCountriesAds(countriesList, s);
                    countriesAdapter = new CountriesAdapter(NotificationsSettings.this, countriesModels);
                    rvCities.setAdapter(countriesAdapter);
                    rvCities.setLayoutManager(new LinearLayoutManager(NotificationsSettings.this, LinearLayoutManager.VERTICAL, false));
                    countriesAdapter.notifyDataSetChanged();
                    countriesAdapter.onNameClic(new CountriesAdapter.NameClick() {
                        @Override
                        public void onNameClick(int pos) {
                            dialog.cancel();
                            if (type.equals("from")) {
                                fromCountryID = countriesModels.get(pos).getCountry_id();
                            } else {
                                toCountryID = countriesModels.get(pos).getCountry_id();

                            }
                            countriesList.clear();
                            countriesAdapter.notifyDataSetChanged();
                            if (type.equals("from")) {
                                presenter.getCities(NotificationsSettings.this, "" + fromCountryID);
                            } else {
                                presenter.getCities(NotificationsSettings.this, "" + toCountryID);
                            }
                        }
                    });
                } else {
                    findAds(citiesList, s);
                    citiesAdapter = new CitiesAdapter(NotificationsSettings.this, models);
                    rvCities.setAdapter(citiesAdapter);
                    rvCities.setLayoutManager(new LinearLayoutManager(NotificationsSettings.this, LinearLayoutManager.VERTICAL, false));
                    citiesAdapter.notifyDataSetChanged();
                    citiesAdapter.onNameClic(new CitiesAdapter.NameClick() {
                        @Override
                        public void onNameClick(int pos) {
                            if (type.equals("from")) {
                                dialog.dismiss();
                                fromCityID = models.get(pos).getCity_id();
                                etDeparturePoint.setText(models.get(pos).getCity_name());
                            } else {
                                toCityID = models.get(pos).getCity_id();
                                etArrivalPoint.setText(models.get(pos).getCity_name());
                            }
                            dialog.dismiss();
                        }
                    });
                }
                return false;
            }
        });
    }

    public List<CitiesResponse.Return> findAds(List<CitiesResponse.Return> categories, String name) {
        models.clear();
        if (name.length() == 0) {
            models.addAll(citiesList);
        } else {
            for (CitiesResponse.Return cat : categories) //assume categories isn't null.
            {
                if (cat.getCity_name().contains(name)) {
                    models.add(cat);
                }
            }
        }


        return models;
    }

    public List<CountriesResponse.Return> findCountriesAds(List<CountriesResponse.Return> categories, String name) {
        countriesModels.clear();
        if (name.length() == 0) {
            countriesModels.addAll(countriesModels);
        } else {
            for (CountriesResponse.Return cat : categories) //assume categories isn't null.
            {
                if (cat.getCountry_name().contains(name)) {
                    countriesModels.add(cat);
                }
            }
        }


        return countriesModels;
    }


    @Override
    public void getNotificationSettings(NotificationSettingResponse.ReturnsEntity returns) {
        if (returns.getAdv_not_orders().equals("1")) {
            swOrders.setChecked(true);
        } else if (returns.getAdv_not_orders().equals("0")) {
            swOrders.setChecked(false);
        }
        if (returns.getAdv_not_trips().equals("1")) {
            swTrips.setChecked(true);
        } else if (returns.getAdv_not_trips().equals("0")) {
            swTrips.setChecked(false);
        }

        adv_not_car_type = returns.getAdv_not_car_type();
        servTypeID = returns.getAdv_not_order_type();
        weightId = returns.getAdv_not_weight();
        etDeparturePoint.setText(returns.getCityNameFrom());
        etArrivalPoint.setText(returns.getCityNameTo());
        arryList = returns.getOrderTypes();

        presenter.getCarTypes(this);
        presenter.getOrdersTypes(this);
        presenter.getWeights(this);
    }


    @Override
    public void getCountries(final List<CountriesResponse.Return> returnList) {
        getSearchResult("countries");
        countriesList = returnList;
        countriesAdapter = new CountriesAdapter(this, returnList);
        rvCities.setAdapter(countriesAdapter);
        rvCities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        countriesAdapter.onNameClic(new CountriesAdapter.NameClick() {
            @Override
            public void onNameClick(int pos) {
                dialog.cancel();
                if (type.equals("from")) {
                    fromCountryID = returnList.get(pos).getCountry_id();
                } else {
                    toCountryID = returnList.get(pos).getCountry_id();

                }
                countriesList.clear();
                countriesAdapter.notifyDataSetChanged();
                if (type.equals("from")) {
                    presenter.getCities(NotificationsSettings.this, "" + fromCountryID);
                } else {
                    presenter.getCities(NotificationsSettings.this, "" + toCountryID);
                }
            }
        });
    }

    @Override
    public void getCities(final List<CitiesResponse.Return> returnList) {

        dialog.show();
        getSearchResult("cities");
        citiesList = returnList;
        citiesAdapter = new CitiesAdapter(this, returnList);
        rvCities.setAdapter(citiesAdapter);
        rvCities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        citiesAdapter.onNameClic(new CitiesAdapter.NameClick() {
            @Override
            public void onNameClick(int pos) {
                if (type.equals("from")) {
                    dialog.dismiss();
                    fromCityID = returnList.get(pos).getCity_id();
                    etDeparturePoint.setText(returnList.get(pos).getCity_name());
                } else {
                    toCityID = returnList.get(pos).getCity_id();
                    etArrivalPoint.setText(returnList.get(pos).getCity_name());
                }

                dialog.dismiss();
            }
        });
    }

    @Override
    public void getWeights(final List<WeightsResponse.ReturnsEntity> returns) {
        etWeight.setAdapter(new WeightsSpinnerAdapter(this, returns));
        etWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                weightId = returns.get(i).getPrice_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        for (int i = 0; i < returns.size(); i++) {
            if (weightId.equals(returns.get(i).getPrice_id())) {
                etWeight.setSelection(i);
            }
        }
    }

    @Override
    public void getCarTypes(final List<CarTypeResponse.ReturnsEntity> returns) {
        CarTypesAdapter carTypesAdapter = new CarTypesAdapter(this, returns, adv_not_car_type);
        rvTypes.setAdapter(carTypesAdapter);
        rvTypes.setLayoutManager(new GridLayoutManager(this, 3));
        carTypesAdapter.setCarTypeClick(new CarTypesAdapter.CarTypeClick() {
            @Override
            public void onCarTypeClick(int pos) {
                carType = returns.get(pos).getCartype_id();
                if (returns.get(pos).getCartype_name().equals("طيران") || returns.get(pos).getCartype_name().equals("plane")) {
                    liTripType.setVisibility(View.VISIBLE);
                } else {
                    liTripType.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void getOrderTypes(final List<OrderTypeResponse.ReturnsEntity> returns) {
        MultiOrderTypesAdapter orderTypesAdapter = new MultiOrderTypesAdapter(this, returns, servTypeID);
        rvOrderTypes.setAdapter(orderTypesAdapter);
        rvOrderTypes.setLayoutManager(new GridLayoutManager(this, 3));
//        orderTypesAdapter.setCarTypeClick(new MultiOrderTypesAdapter.CarTypeClick() {
//            @Override
//            public void onCarTypeClick(int pos) {
//                servTypeID = returns.get(pos).getType_id();
//            }
//        });
    }

    @OnClick({R.id.etDeparturePoint, R.id.etArrivalPoint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.etDeparturePoint:
                type = "from";
                showPopupCountries();
                break;
            case R.id.etArrivalPoint:
                type = "to";
                showPopupCountries();
                break;
        }
    }
}