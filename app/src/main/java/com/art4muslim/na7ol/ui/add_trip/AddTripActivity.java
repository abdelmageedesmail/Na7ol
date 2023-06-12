package com.art4muslim.na7ol.ui.add_trip;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;

import android.os.Bundle;


import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ActivityAddTripBinding;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.addShipment.AddShipment;
import com.art4muslim.na7ol.ui.addShipment.WeightsSpinnerAdapter;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AddTripActivity extends AppCompatActivity implements View.OnClickListener, TripView {

    @Inject
    TripPresenter presenter;

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
    private LinearLayout.LayoutParams param;
    private ActivityAddTripBinding binding;
    PrefrencesStorage storage;
    private String tripType;
    private String weightId;
    private TripsModelResponse.Returns model;
    private boolean isValid;
    String startDate, endDate, fullDate, startTime, endTime, fullTime;
    String finalDate;
    private List<CitiesResponse.Return> models = new ArrayList<>();
    private List<CountriesResponse.Return> countriesModels = new ArrayList<>();
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        presenter.setTripView(this);
        storage = new PrefrencesStorage(this);
        binding.etDeparturePoint.setOnClickListener(this);
        binding.etArrivalPoint.setOnClickListener(this);
        binding.etDepartureTime.setOnClickListener(this);
        binding.etDepartureDate.setOnClickListener(this);
        binding.etArrivalDate.setOnClickListener(this);
        binding.etArrivalTime.setOnClickListener(this);
        binding.btnComplete.setOnClickListener(this);
        presenter.getCarTypes(this);

        binding.ivBack.setOnClickListener(this);
        if (getIntent().getExtras().getString("from").equals("add")) {
            carType = getIntent().getExtras().getString("carType");
            if (getIntent().getExtras().getString("carType").equals("3")) {
                binding.rdPlan.setChecked(true);
                carType = "3";
            } else if (getIntent().getExtras().getString("carType").equals("2")) {
                binding.rdCars.setChecked(true);
                carType = "2";
            } else if (getIntent().getExtras().getString("carType").equals("1")) {
                binding.rdTrain.setChecked(true);
                carType = "1";
            }
        } else {
            binding.tvTitle.setText(getString(R.string.editTrip));
            binding.btnComplete.setText(getString(R.string.edit));
            model = new Gson().fromJson(getIntent().getStringExtra("model"), new TypeToken<TripsModelResponse.Returns>() {
            }.getType());
            fillData();


        }
        binding.rdPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "3";
                    binding.liTripType.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.rdCars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "2";
                    binding.liTripType.setVisibility(View.GONE);
                }
            }
        });
        binding.rdTrain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "1";
                    binding.liTripType.setVisibility(View.GONE);
                }
            }
        });
        binding.spTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    tripType = "national";
                } else {
                    tripType = "international";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        presenter.getWeights(this);
        binding.etNotes.setFilters(new InputFilter[]{filter});

    }

    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (isCharAllowed(c)) // put your condition here
                    sb.append(c);
                else
                    keepOriginal = false;
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };

    private boolean isCharAllowed(char c) {
        return Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
    }


    private void fillData() {
        carType = model.getServ_cartype_id();
        String[] s = model.getServ_from_time().split(" ");
        binding.etDepartureDate.setText(s[0]);
        binding.etDepartureTime.setText(s[1]);
        String[] to = model.getServ_to_time().split(" ");
        binding.etArrivalDate.setText(to[0]);
        binding.etArrivalTime.setText(to[1]);

        if (model.getServ_cartype_id().equals("1")) {
            binding.rdTrain.setChecked(true);
        } else if (model.getServ_cartype_id().equals("2")) {
            binding.rdCars.setChecked(true);
        } else if (model.getServ_cartype_id().equals("3")) {
            binding.rdPlan.setChecked(true);
        }

        fromCountryID = Integer.parseInt(model.getServ_from_country_id());
        toCountryID = Integer.parseInt(model.getServ_to_country_id());
        fromCityID = Integer.parseInt(model.getServ_from_city_id());
        toCityID = Integer.parseInt(model.getServ_to_city_id());
        binding.etDeparturePoint.setText(model.getFrom_city_name());
        binding.etArrivalPoint.setText(model.getTo_city_name());
        binding.etDepartureTime.setText(model.getServ_time());
        binding.etNotes.setText(model.getServ_details());
    }


    private void setDate(final EditText editText, String from) {
        Calendar mcurrentDate = Calendar.getInstance();
        final int mYear = mcurrentDate.get(Calendar.YEAR);
        final int mMonth = mcurrentDate.get(Calendar.MONTH);
        final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog mDatePicker = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                /*      Your code   to get date and time    */
                selectedmonth = selectedmonth + 1;
                String month = String.valueOf(selectedmonth);
                String hDay = String.valueOf(selectedday);

                if (selectedmonth < 10) {
                    month = "0" + selectedmonth;
                }

                if (selectedday < 10) {
                    hDay = "0" + selectedday;
                }
                fullDate = selectedyear + "-" + month + "-" + hDay;
                if (Utils.getLang(AddTripActivity.this).equals("ar")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = sdf.parse(selectedyear + "-" + month + "-" + hDay);
                        SimpleDateFormat finalDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        finalDate = finalDateFormat.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
                    finalDate = fullDate;
                }
//                selectedyear + "-" + month + "-" + hDay
                editText.setText(finalDate);
                if (from.equals("departure")) {
                    startDate = fullDate;
                } else if (from.equals("arrival")) {
                    endDate = fullDate;
                }
            }
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDatePicker.show();

    }


    private void setTime(final EditText etTime, String from) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour;
                String minute;
                hour = "" + selectedHour;
                minute = "" + selectedMinute;
                fullTime = hour + ":" + minute + ":" + "00";
                if (Utils.getLang(AddTripActivity.this).equals("ar")) {
                    hour = getArabicNumbers("" + selectedHour);
                    minute = getArabicNumbers("" + selectedMinute);
                }

                etTime.setText(hour + ":" + minute);
                if (from.equals("departure")) {
                    startTime = fullTime;
                } else if (from.equals("arraival")) {
                    endTime = fullTime;
                }
            }

        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public String getArabicNumbers(String val) {

        String newValue = (((((((((((val + "")
                .replaceAll("1", "١")).replaceAll("2", "٢"))
                .replaceAll("3", "٣")).replaceAll("4", "٤"))
                .replaceAll("5", "٥")).replaceAll("6", "٦"))
                .replaceAll("7", "٧")).replaceAll("8", "٨"))
                .replaceAll("9", "٩")).replaceAll("0", "٠"));


        //  return " <font color=\"#D7AD3D\">"+builder.reverse().toString()+"</font> ";
        return newValue;

    }

    private void setUpValidation() {

        if (binding.etDeparturePoint.getText().toString().isEmpty()) {
            binding.etDeparturePoint.setError(getString(R.string.empty));
        } else if (binding.etArrivalPoint.getText().toString().isEmpty()) {
            binding.etArrivalPoint.setError(getString(R.string.empty));
        } else if (binding.etDepartureDate.getText().toString().isEmpty()) {
            binding.etDepartureDate.setError(getString(R.string.empty));
        } else if (binding.etDepartureTime.getText().toString().isEmpty()) {
            binding.etDepartureTime.setError(getString(R.string.empty));
        } else if (binding.etArrivalDate.getText().toString().isEmpty()) {
            binding.etArrivalDate.setError(getString(R.string.empty));
        } else if (binding.etArrivalTime.getText().toString().isEmpty()) {
            binding.etArrivalTime.setError(getString(R.string.empty));
        } else if (binding.etNotes.getText().toString().isEmpty()) {
            binding.etNotes.setError(getString(R.string.empty));
        } else if (binding.etDeparturePoint.getText().toString().equals(binding.etArrivalPoint.getText().toString()) & !carType.equals("2")) {
            Toast.makeText(this, getString(R.string.pleaseSelectDifferentCities), Toast.LENGTH_SHORT).show();
        } else {
            compareDate();
            if (!isValid) {
                Toast.makeText(this, getString(R.string.pleaseEnterDate), Toast.LENGTH_SHORT).show();
            } else {
                if (getIntent().getExtras().getString("from").equals("edit")) {
                    presenter.editTrip(this, storage.getId(), carType, "" + fromCountryID, "" + fromCityID, "" + toCountryID, "" + toCityID, "offer", tripType, endDate + " " + endTime, startDate + " " + startTime, binding.etNotes.getText().toString(), weightId
                            , "", "", "", "", null, model.getServ_id());
                } else {
                    presenter.addTrip(this, storage.getId(), carType, "" + fromCountryID, "" + fromCityID, "" + toCountryID, "" + toCityID, "offer", tripType, endDate + " " + endTime, startDate + " " + startTime, binding.etNotes.getText().toString(), weightId
                            , "", "", "", "", null);
                }
            }
        }
    }

    private void compareDate() {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdformat.parse(binding.etDepartureDate.getText().toString() + " " + binding.etDepartureTime.getText().toString());
            d2 = sdformat.parse(binding.etArrivalDate.getText().toString() + " " + binding.etArrivalTime.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("The date 1 is: " + sdformat.format(d1));
        System.out.println("The date 2 is: " + sdformat.format(d2));
        if (d1.compareTo(d2) > 0) {
            isValid = false;
        } else if (d1.compareTo(d2) < 0) {
//            Log.e("compare", "Date 1 occurs before Date 2");
            isValid = true;
        } else if (d1.compareTo(d2) == 0) {
//            Log.e("compare", "Both dates are equal");
            isValid = false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etDeparturePoint:
                type = "from";
                showPopupCountries();
                break;
            case R.id.etArrivalPoint:
                type = "to";
                showPopupCountries();
                break;
            case R.id.etArrivalDate:
                setDate(binding.etArrivalDate, "arrival");
                break;
            case R.id.etDepartureDate:
                setDate(binding.etDepartureDate, "departure");
                break;
            case R.id.etArrivalTime:
                setTime(binding.etArrivalTime, "arraival");
                break;
            case R.id.etDepartureTime:
                setTime(binding.etDepartureTime, "departure");
                break;
            case R.id.btnComplete:
                setUpValidation();
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
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
                    countriesAdapter = new CountriesAdapter(AddTripActivity.this, countriesModels);
                    rvCities.setAdapter(countriesAdapter);
                    rvCities.setLayoutManager(new LinearLayoutManager(AddTripActivity.this, LinearLayout.VERTICAL, false));
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
                                presenter.getCities(AddTripActivity.this, "" + fromCountryID);
                            } else {
                                presenter.getCities(AddTripActivity.this, "" + toCountryID);
                            }
                        }
                    });
                } else {
                    findAds(citiesList, s);
                    citiesAdapter = new CitiesAdapter(AddTripActivity.this, models);
                    rvCities.setAdapter(citiesAdapter);
                    rvCities.setLayoutManager(new LinearLayoutManager(AddTripActivity.this, LinearLayoutManager.VERTICAL, false));
                    citiesAdapter.notifyDataSetChanged();
                    citiesAdapter.onNameClic(new CitiesAdapter.NameClick() {
                        @Override
                        public void onNameClick(int pos) {
                            if (type.equals("from")) {
                                dialog.dismiss();
                                fromCityID = models.get(pos).getCity_id();
                                binding.etDeparturePoint.setText(models.get(pos).getCity_name());
                            } else {
                                toCityID = models.get(pos).getCity_id();
                                binding.etArrivalPoint.setText(models.get(pos).getCity_name());
                            }
                            dialog.dismiss();
                        }
                    });
                }
                return false;
            }
        });
    }

    public List<CitiesResponse.Return> findAds
            (List<CitiesResponse.Return> categories, String name) {
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

    public List<CountriesResponse.Return> findCountriesAds
            (List<CountriesResponse.Return> categories, String name) {
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
                    if ((fromCountryID == toCountryID) & (carType.equals("3"))) {
                        List<String> flights = new ArrayList<String>();
                        flights.add(getString(R.string.internationalTrips));

                        ArrayAdapter reportAdapter = new ArrayAdapter(AddTripActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, flights);
                        binding.spTripType.setAdapter(reportAdapter);
                        binding.spTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                tripType = "national";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {
                        List<String> flights = new ArrayList<String>();
                        flights.add(getString(R.string.internationalTrips));

                        ArrayAdapter reportAdapter = new ArrayAdapter(AddTripActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, flights);
                        binding.spTripType.setAdapter(reportAdapter);
                        binding.spTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                tripType = "international";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                }

                countriesList.clear();
                countriesAdapter.notifyDataSetChanged();
                if (type.equals("from")) {
                    presenter.getCities(AddTripActivity.this, "" + fromCountryID);
                } else {
                    presenter.getCities(AddTripActivity.this, "" + toCountryID);
                }
            }
        });
    }

    @Override
    public void getCities(final List<CitiesResponse.Return> returnList) {
        if (returnList != null) {
            if (returnList.size() > 0) {
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
                            binding.etDeparturePoint.setText(returnList.get(pos).getCity_name());
                        } else {
                            toCityID = returnList.get(pos).getCity_id();
                            binding.etArrivalPoint.setText(returnList.get(pos).getCity_name());
                        }
                        dialog.dismiss();
                    }
                });
            }
        } else {
            Toast.makeText(this, getString(R.string.noExistingCities), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addTrip(boolean isAdded) {
        if (isAdded) {
            if (getIntent().getExtras().getString("from").equals("edit")) {
                Toast.makeText(this, getString(R.string.tripsEdited), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MyTrips.class);
                intent.putExtra("type", "myTrips");
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, getString(R.string.tripIsAdded), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
            }
        }
    }

    @Override
    public void getTrips(List<TripsModelResponse.Returns> returns) {

    }

    @Override
    public void getWeights(final List<WeightsResponse.ReturnsEntity> returns) {
        binding.etWeight.setAdapter(new WeightsSpinnerAdapter(this, returns));


        binding.etWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                weightId = returns.get(i).getPrice_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (getIntent().getExtras().getString("from").equals("edit")) {
//            Log.e("servWeight",""+model.getServWeight(),)
            for (int i = 0; i < returns.size(); i++) {
                if (model.getServ_weight_name().equals(returns.get(i).getPrice_min_weight() + " / " + returns.get(i).getPrice_max_weight())) {
                    binding.etWeight.setSelection(i);
                }
            }
        }
    }

    @Override
    public void getCarTypes(final List<CarTypeResponse.ReturnsEntity> returns) {
        CarTypesAdapter carTypesAdapter = new CarTypesAdapter(this, returns, carType);
        binding.rvTypes.setAdapter(carTypesAdapter);
        if (getIntent().getExtras().getString("carTypeName") != null) {

            if (getIntent().getExtras().getString("carTypeName").equals("طيران") || getIntent().getExtras().getString("carTypeName").equals("plane")) {
                binding.liTripType.setVisibility(View.VISIBLE);
            } else {
                binding.liTripType.setVisibility(View.GONE);
            }
        } else {
            binding.liTripType.setVisibility(View.GONE);
        }
        binding.rvTypes.setLayoutManager(new GridLayoutManager(this, 3));
        carTypesAdapter.setCarTypeClick(new CarTypesAdapter.CarTypeClick() {
            @Override
            public void onCarTypeClick(int pos) {
                carType = returns.get(pos).getCartype_id();

                if (returns.get(pos).getCartype_name().equals("طيران") || returns.get(pos).getCartype_name().equals("plane")) {
                    binding.liTripType.setVisibility(View.VISIBLE);
                } else {
                    binding.liTripType.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void getOrderTypes(List<OrderTypeResponse.ReturnsEntity> returns) {

    }
}
