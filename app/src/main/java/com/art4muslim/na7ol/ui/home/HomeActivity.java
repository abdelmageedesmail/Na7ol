package com.art4muslim.na7ol.ui.home;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ActivityHomeBinding;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.NotificationModel;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.login.LoginActivity;
import com.art4muslim.na7ol.notifications.FCMRegistrationService;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.addShipment.OrderTypesAdapter;
import com.art4muslim.na7ol.ui.addShipment.WeightsSpinnerAdapter;
import com.art4muslim.na7ol.ui.add_trip.AddTripActivity;
import com.art4muslim.na7ol.ui.add_trip.CarTypesAdapter;
import com.art4muslim.na7ol.ui.add_trip.CitiesAdapter;
import com.art4muslim.na7ol.ui.add_trip.CountriesAdapter;
import com.art4muslim.na7ol.ui.chatFragment.ChatFragment;
import com.art4muslim.na7ol.ui.chooseTripType.ChooseAddingType;
import com.art4muslim.na7ol.ui.joinTransporter.JoinAsTransporter;
import com.art4muslim.na7ol.ui.myCharge.MyChargeActivity;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.notificationFragment.NotificationsAdapter;
import com.art4muslim.na7ol.ui.notificationFragment.NotificationsFragment;
import com.art4muslim.na7ol.ui.notificationFragment.NotificationsPresenter;
import com.art4muslim.na7ol.ui.notificationFragment.NotificationsView;
import com.art4muslim.na7ol.ui.orderFragment.OrderFragment;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.ui.priceOffers.PriceOffersActivity;
import com.art4muslim.na7ol.ui.profile.ProfileActivity;
import com.art4muslim.na7ol.ui.settings.SettingsActivity;
import com.art4muslim.na7ol.ui.tripFragment.TripFragment;
import com.art4muslim.na7ol.utils.HelperClass;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.UpdateViews;
import com.art4muslim.na7ol.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;
import okhttp3.internal.Util;

public class HomeActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener,
        View.OnClickListener, HomeView, UpdateViews, NotificationsView {


    @Inject
    NotificationsPresenter notificationsPresenter;

    RadioButton rdPlan;
    RadioButton rdCars;
    RadioButton rdTrain;
    EditText etDeparturePoint;
    EditText etArrivalPoint;
    Spinner spTripType;
    LinearLayout liTripType;
    EditText etDepartureDate;
    EditText etDepartureTime;
    EditText etArrivalDate;
    EditText etArrivalTime;
    Button btnSearch;

    RadioButton rdDocument;
    RadioButton rdPackage;
    RadioButton rdSpecified;
    Spinner etWeight;
    boolean isValid;

    private MenuAdapter mMenuAdapter;
    PrefrencesStorage storage;
    private ViewHolder mViewHolder;
    private ArrayList<String> mTitles;
    private ActivityHomeBinding binding;
    private Dialog dialog;
    private Intent intent;
    private Dialog dialogTripSearch;
    private Dialog dialogOrderSearch;
    @Inject
    HomePresenter presenter;
    private String type;
    private RecyclerView rvCities;
    private List<CountriesResponse.Return> countriesList = new ArrayList<>();
    private CountriesAdapter countriesAdapter;
    private int fromCountryID, toCountryID, fromCityID, toCityID;
    private List<CitiesResponse.Return> citiesList = new ArrayList<>();
    private CitiesAdapter citiesAdapter;
    private String carType = "";
    private String servTypeID = "";
    private String weightId;
    private String isTransporter;
    private RecyclerView rvOrderTypes;
    private RecyclerView rvCarTypes;

    private List<CitiesResponse.Return> models = new ArrayList<>();
    private List<CountriesResponse.Return> countriesModels = new ArrayList<>();
    private SearchView searchView;
    private String fromCountryName, toCountryName, fromCityName, toCityName;

    String startDate, endDate, fullDate, startTime, endTime, fullTime;
    String finalDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        storage = new PrefrencesStorage(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        Log.e("homeLanguage", "" + Utils.getLang(this));
        notificationsPresenter.setView(this);
        notificationsPresenter.getNotifications(this, storage.getId());
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        Log.e("afterhomeLanguage", "" + Utils.getLang(this));

        presenter.setTripView(this);
        mViewHolder = new ViewHolder();
        presenter.getUserData(this, storage.getId());
        Log.e("IDDD", "" + storage.getId());
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("from").equals("notification")) {
                HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), new NotificationsFragment());
                binding.tvNotifications.setTextColor(Color.parseColor("#F6C21A"));
                binding.tvOrders.setTextColor(Color.BLACK);
                binding.tvTrips.setTextColor(Color.BLACK);
                binding.tvChat.setTextColor(Color.BLACK);

                binding.tvTitle.setText(getString(R.string.notifications));

                binding.ivTrip.setImageResource(R.drawable.ic_my_trips);
                binding.ivOrder.setImageResource(R.drawable.ic_my_orders);
                binding.ivNotification.setImageResource(R.drawable.ic_notification_selected);
                binding.ivChat.setImageResource(R.drawable.ic_chat);
            }
        } else {
            HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), new TripFragment());
            binding.tvTitle.setText(getString(R.string.trips));

        }
        startService(new Intent(this, FCMRegistrationService.class));


        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
        };
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        binding.liTrips.setOnClickListener(this);
        binding.liOrders.setOnClickListener(this);
        binding.liNotification.setOnClickListener(this);
        binding.liChats.setOnClickListener(this);
        binding.ivAdd.setOnClickListener(this);
        binding.ivFilter.setOnClickListener(this);
    }

    private void popupTripSearch() {
        dialogTripSearch = new Dialog(this);
        dialogTripSearch.setContentView(R.layout.popup_trip_search);
        dialogTripSearch.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogTripSearch.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        rdPlan = dialogTripSearch.findViewById(R.id.rdPlan);
        rdCars = dialogTripSearch.findViewById(R.id.rdCars);

        rdTrain = dialogTripSearch.findViewById(R.id.rdTrain);
        etDeparturePoint = dialogTripSearch.findViewById(R.id.etDeparturePoint);
        etArrivalPoint = dialogTripSearch.findViewById(R.id.etArrivalPoint);
        spTripType = dialogTripSearch.findViewById(R.id.spTripType);
        liTripType = dialogTripSearch.findViewById(R.id.liTripType);
        etDepartureDate = dialogTripSearch.findViewById(R.id.etDepartureDate);
        etDepartureTime = dialogTripSearch.findViewById(R.id.etDepartureTime);
        etArrivalDate = dialogTripSearch.findViewById(R.id.etArrivalDate);
        etArrivalTime = dialogTripSearch.findViewById(R.id.etArrivalTime);
        btnSearch = dialogTripSearch.findViewById(R.id.btnSearch);
        rvCarTypes = dialogTripSearch.findViewById(R.id.rvCarTypes);

        ImageView ivClose = dialogTripSearch.findViewById(R.id.ivClose);
        RelativeLayout relative = dialogTripSearch.findViewById(R.id.relative);

        presenter.getCarTypes(this);
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTripSearch.dismiss();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTripSearch.dismiss();
            }
        });
        etDeparturePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "from";
                showPopupCountries();
            }
        });

        etArrivalPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "to";
                showPopupCountries();
            }
        });

        etArrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(etArrivalDate);
            }
        });

        etDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(etDepartureDate);
            }
        });

        etDepartureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(etDepartureTime);
            }
        });
        etArrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(etArrivalTime);
            }
        });

        rdPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "3";
                }
            }
        });
        rdCars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "2";
                }
            }
        });
        rdTrain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "1";
                }
            }
        });

        if (!storage.getKey("fromCityName").equals("null")) {
            etDeparturePoint.setText(storage.getKey("fromCityName"));
            fromCityName = storage.getKey("fromCityName");
            fromCountryID = Integer.parseInt(storage.getKey("fromCountryId"));
            fromCityID = Integer.parseInt(storage.getKey("fromCityId"));
        }
        if (!storage.getKey("toCityName").equals("null")) {
            etArrivalPoint.setText(storage.getKey("toCityName"));
            toCityName = storage.getKey("toCityName");
            toCountryID = Integer.parseInt(storage.getKey("toCountryID"));
            toCityID = Integer.parseInt(storage.getKey("toCityID"));
        }
//        if (!storage.getKey("from_time").equals("null")) {
//            String from_time = storage.getKey("from_time");
//            String[] s = from_time.split(" ");
//            etDepartureDate.setText(s[0]);
//            etDepartureTime.setText(s[1]);
//        }
//        if (!storage.getKey("toTime").equals("null")) {
//            String toTime = storage.getKey("toTime");
//            String[] s = toTime.split(" ");
//            etArrivalDate.setText(s[0]);
//            etArrivalTime.setText(s[1]);
//        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carType.isEmpty()) {
                    Toast.makeText(HomeActivity.this, getString(R.string.pleaseSelctTravelType), Toast.LENGTH_SHORT).show();
                } else if (etDeparturePoint.getText().toString().isEmpty()) {
                    etDeparturePoint.setError(getString(R.string.empty));
                } else if (etArrivalPoint.getText().toString().isEmpty()) {
                    etArrivalPoint.setError(getString(R.string.empty));
                }
//                else if (etDepartureDate.getText().toString().isEmpty()) {
//                    etDepartureDate.setError(getString(R.string.empty));
//                } else if (etDepartureTime.getText().toString().isEmpty()) {
//                    etDepartureTime.setError(getString(R.string.empty));
//                } else if (etArrivalDate.getText().toString().isEmpty()) {
//                    etArrivalDate.setError(getString(R.string.empty));
//                } else if (etArrivalTime.getText().toString().isEmpty()) {
//                    etArrivalTime.setError(getString(R.string.empty));
//                }
                else {
//                    compareDate();
//                    if (!isValid) {
//                        Toast.makeText(HomeActivity.this, getString(R.string.pleaseEnterDate), Toast.LENGTH_SHORT).show();
//                    } else {
                    dialogTripSearch.dismiss();
                    binding.tvTrips.setTextColor(Color.parseColor("#F6C21A"));
                    binding.tvOrders.setTextColor(Color.BLACK);
                    binding.tvNotifications.setTextColor(Color.BLACK);
                    binding.tvChat.setTextColor(Color.BLACK);

                    binding.tvTitle.setText(getString(R.string.trips));

                    binding.ivTrip.setImageResource(R.drawable.ic_trip_selected);
                    binding.ivOrder.setImageResource(R.drawable.ic_my_orders);
                    binding.ivNotification.setImageResource(R.drawable.ic_notification);
                    binding.ivChat.setImageResource(R.drawable.ic_chat);

                    TripFragment fragment = new TripFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("fromCountryId", "" + fromCountryID);
                    bundle.putString("toCountryID", "" + toCountryID);
                    bundle.putString("carType", "" + carType);
                    bundle.putString("fromCityId", "" + fromCityID);
                    bundle.putString("toCityID", "" + toCityID);
                    bundle.putString("from_time", "" + startDate + " " + startTime);
                    bundle.putString("toTime", "" + endDate + " " + endTime);
                    fragment.setArguments(bundle);

                    storage.storeKey("from", "trip");
                    storage.storeKey("fromCountryId", "" + fromCountryID);
                    storage.storeKey("fromCountryName", "" + fromCountryName);
                    storage.storeKey("toCountryID", "" + toCountryID);
                    storage.storeKey("toCountryName", "" + toCountryName);
                    storage.storeKey("carType", "" + carType);
                    storage.storeKey("fromCityId", "" + fromCityID);
                    storage.storeKey("fromCityName", "" + fromCityName);
                    storage.storeKey("toCityID", "" + toCityID);
                    storage.storeKey("toCityName", "" + toCityName);
                    storage.storeKey("from_time", "" + etDepartureDate.getText().toString() + " " + etDepartureTime.getText().toString());
                    storage.storeKey("toTime", "" + etArrivalDate.getText().toString() + " " + etArrivalTime.getText().toString());

                    HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), fragment);
//                    }

                }

            }
        });


        dialogTripSearch.show();
    }

    private void compareDate() {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdformat.parse(etDepartureDate.getText().toString() + " " + etDepartureTime.getText().toString());
            d2 = sdformat.parse(etArrivalDate.getText().toString() + " " + etArrivalTime.getText().toString());
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

    private void popupOrderSearch() {
        dialogOrderSearch = new Dialog(this);
        dialogOrderSearch.setContentView(R.layout.popup_order_search);
        dialogOrderSearch.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialogOrderSearch.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        rdPlan = dialogOrderSearch.findViewById(R.id.rdPlan);
        rdCars = dialogOrderSearch.findViewById(R.id.rdCars);
        rvOrderTypes = dialogOrderSearch.findViewById(R.id.rvOrderTypes);
        rvCarTypes = dialogOrderSearch.findViewById(R.id.rvCarTypes);
        rdTrain = dialogOrderSearch.findViewById(R.id.rdTrain);
        rdDocument = dialogOrderSearch.findViewById(R.id.rdDocument);
        rdPackage = dialogOrderSearch.findViewById(R.id.rdPackage);
        rdSpecified = dialogOrderSearch.findViewById(R.id.rdSpecified);
        etWeight = dialogOrderSearch.findViewById(R.id.etWeight);
        etDeparturePoint = dialogOrderSearch.findViewById(R.id.etDeparturePoint);
        etArrivalPoint = dialogOrderSearch.findViewById(R.id.etArrivalPoint);
        btnSearch = dialogOrderSearch.findViewById(R.id.btnSearch);
        ImageView ivClose = dialogOrderSearch.findViewById(R.id.ivClose);
        RelativeLayout relative = dialogOrderSearch.findViewById(R.id.relative);
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOrderSearch.dismiss();
            }
        });
        presenter.getWeights(this);
        presenter.getOrdersTypes(this);
        presenter.getCarTypes(this);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOrderSearch.dismiss();
            }
        });

        etDeparturePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "from";
                showPopupCountries();
            }
        });

        etArrivalPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "to";
                showPopupCountries();
            }
        });
        rdPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "3";
                }
            }
        });
        rdCars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "2";
                }
            }
        });
        rdTrain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "1";
                }
            }
        });


        rdDocument.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                servTypeID = "1";
            }
        });
        rdPackage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                servTypeID = "2";
            }
        });
        rdSpecified.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                servTypeID = "3";
            }
        });


        if (!storage.getKey("fromCityNameOrder").equals("null")) {
            etDeparturePoint.setText(storage.getKey("fromCityNameOrder"));
            fromCityName = storage.getKey("fromCityNameOrder");
            fromCountryID = Integer.parseInt(storage.getKey("fromOrderCountryId"));
            fromCityID = Integer.parseInt(storage.getKey("fromOrderCityId"));
        }
        if (!storage.getKey("toCityNameOrder").equals("null")) {
            etArrivalPoint.setText(storage.getKey("toCityNameOrder"));
            toCityName = storage.getKey("toCityNameOrder");
            toCountryID = Integer.parseInt(storage.getKey("toOrderCountryID"));
            toCityID = Integer.parseInt(storage.getKey("toOrderCityID"));
        }


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carType.isEmpty()) {
                    Toast.makeText(HomeActivity.this, getString(R.string.pleaseSelctTravelType), Toast.LENGTH_SHORT).show();
                } else if (etDeparturePoint.getText().toString().isEmpty()) {
                    etDeparturePoint.setError(getString(R.string.empty));
                } else if (etArrivalPoint.getText().toString().isEmpty()) {
                    etArrivalPoint.setError(getString(R.string.empty));
                }
//                else if (etDepartureDate.getText().toString().isEmpty()) {
//                    etDepartureDate.setError(getString(R.string.empty));
//                }
//                else if (etDepartureTime.getText().toString().isEmpty()) {
//                    etDepartureTime.setError(getString(R.string.empty));
//                } else if (etArrivalDate.getText().toString().isEmpty()) {
//                    etArrivalDate.setError(getString(R.string.empty));
//                } else if (etArrivalTime.getText().toString().isEmpty()) {
//                    etArrivalTime.setError(getString(R.string.empty));
//                }
                else {
                    dialogOrderSearch.dismiss();
                    binding.tvOrders.setTextColor(Color.parseColor("#F6C21A"));
                    binding.tvTrips.setTextColor(Color.BLACK);
                    binding.tvNotifications.setTextColor(Color.BLACK);
                    binding.tvChat.setTextColor(Color.BLACK);

                    binding.tvTitle.setText(getString(R.string.orders));

                    binding.ivTrip.setImageResource(R.drawable.ic_my_trips);
                    binding.ivOrder.setImageResource(R.drawable.ic_order_selected);
                    binding.ivNotification.setImageResource(R.drawable.ic_notification);
                    binding.ivChat.setImageResource(R.drawable.ic_chat);
                    OrderFragment fragment = new OrderFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("fromCountryId", "" + fromCountryID);
                    bundle.putString("toCountryID", "" + toCountryID);
                    bundle.putString("carType", "" + carType);
                    bundle.putString("fromCityId", "" + fromCityID);
                    bundle.putString("toCityID", "" + toCityID);
                    bundle.putString("servType", "" + servTypeID);
                    bundle.putString("weightId", "" + weightId);
                    fragment.setArguments(bundle);

                    storage.storeKey("toCityNameOrder", toCityName);
                    storage.storeKey("from", "order");
                    storage.storeKey("fromCityNameOrder", fromCityName);
                    storage.storeKey("fromOrderCountryId", "" + fromCountryID);
                    storage.storeKey("toOrderCountryID", "" + toCountryID);
                    storage.storeKey("carTypeOrder", "" + carType);
                    storage.storeKey("fromOrderCityId", "" + fromCityID);
                    storage.storeKey("toOrderCityID", "" + toCityID);
                    storage.storeKey("servTypeOrder", "" + servTypeID);
                    storage.storeKey("weightIdOrder", "" + weightId);

                    HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), fragment);
                }

            }
        });
        dialogOrderSearch.show();
    }


    private void setDate(final EditText editText) {
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
                if (Utils.getLang(HomeActivity.this).equals("ar")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = sdf.parse(selectedyear + "-" + month + "-" + hDay);
                        SimpleDateFormat finalDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        finalDate = finalDateFormat.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
//                selectedyear + "-" + month + "-" + hDay
                editText.setText(finalDate);
                if (editText.getText().toString().equals(etDepartureDate.getText().toString())) {
                    startDate = fullDate;
                    editText.setText(startDate);
                } else {
                    endDate = fullDate;
                    editText.setText(endDate);

                }
            }
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDatePicker.show();
    }

    private void setTime(final EditText etTime) {
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
                if (Utils.getLang(HomeActivity.this).equals("ar")) {
                    hour = getArabicNumbers("" + selectedHour);
                    minute = getArabicNumbers("" + selectedMinute);
                }

                etTime.setText(hour + ":" + minute);
                if (etTime.getText().toString().equals(etDepartureTime.getText().toString())) {
                    startTime = fullTime;
                } else {
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

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private void handleDrawer() {
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, mViewHolder.mDuoDrawerLayout, binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewHolder.mDuoDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        drawerToggle.syncState();


    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(this, mTitles, isTransporter);
        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {

    }

    @Override
    public void onHeaderClicked() {

    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        if (isTransporter.equals("1")) {
            switch (position) {

                case 0:
                    startActivity(new Intent(this, ProfileActivity.class));
                    break;
                case 1:
                    showPopupDialog();
                    mViewHolder.mDuoDrawerLayout.closeDrawer();
                    break;
                case 2:
                    Intent intent = new Intent(this, MyTrips.class);
                    intent.putExtra("type", "myTrips");
                    startActivity(intent);
                    break;
//            case 4:
//                Intent i = new Intent(this, MyTrips.class);
//                i.putExtra("type", "join");
//                startActivity(i);
//                break;
                case 3:
                    startActivity(new Intent(this, MyOrdersActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(this, MyChargeActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(this, SettingsActivity.class));
                    break;
                case 6:
                    HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), new NotificationsFragment());
                    binding.tvNotifications.setTextColor(Color.parseColor("#F6C21A"));
                    binding.tvOrders.setTextColor(Color.BLACK);
                    binding.tvTrips.setTextColor(Color.BLACK);
                    binding.tvChat.setTextColor(Color.BLACK);

                    binding.tvTitle.setText(getString(R.string.notifications));

                    binding.ivTrip.setImageResource(R.drawable.ic_my_trips);
                    binding.ivOrder.setImageResource(R.drawable.ic_my_orders);
                    binding.ivNotification.setImageResource(R.drawable.ic_notification_selected);
                    binding.ivChat.setImageResource(R.drawable.ic_chat);
                    mViewHolder.mDuoDrawerLayout.closeDrawer();
                    break;
                case 7:
                    Utils.shareApp(this);
                    break;
            }
        } else {
            switch (position) {

                case 0:
                    startActivity(new Intent(this, ProfileActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(this, JoinAsTransporter.class));
                    break;
                case 2:
                    showPopupDialog();
                    mViewHolder.mDuoDrawerLayout.closeDrawer();
                    break;
//                case 3:
//                    Intent intent = new Intent(this, MyTrips.class);
//                    intent.putExtra("type", "myTrips");
//                    startActivity(intent);
//                    break;
//            case 4:
//                Intent i = new Intent(this, MyTrips.class);
//                i.putExtra("type", "join");
//                startActivity(i);
//                break;
                case 3:
                    startActivity(new Intent(this, MyOrdersActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(this, MyChargeActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(this, SettingsActivity.class));
                    break;
                case 6:
                    HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), new NotificationsFragment());
                    binding.tvNotifications.setTextColor(Color.parseColor("#F6C21A"));
                    binding.tvOrders.setTextColor(Color.BLACK);
                    binding.tvTrips.setTextColor(Color.BLACK);
                    binding.tvChat.setTextColor(Color.BLACK);

                    binding.tvTitle.setText(getString(R.string.notifications));

                    binding.ivTrip.setImageResource(R.drawable.ic_my_trips);
                    binding.ivOrder.setImageResource(R.drawable.ic_my_orders);
                    binding.ivNotification.setImageResource(R.drawable.ic_notification_selected);
                    binding.ivChat.setImageResource(R.drawable.ic_chat);
                    mViewHolder.mDuoDrawerLayout.closeDrawer();
                    break;
                case 7:
                    Utils.shareApp(this);
                    break;
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.liTrips:
                HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), new TripFragment());
                binding.tvTrips.setTextColor(Color.parseColor("#F6C21A"));
                binding.tvOrders.setTextColor(Color.BLACK);
                binding.tvNotifications.setTextColor(Color.BLACK);
                binding.tvChat.setTextColor(Color.BLACK);

                binding.tvTitle.setText(getString(R.string.trips));

                binding.ivTrip.setImageResource(R.drawable.ic_trip_selected);
                binding.ivOrder.setImageResource(R.drawable.ic_my_orders);
                binding.ivNotification.setImageResource(R.drawable.ic_notification);
                binding.ivChat.setImageResource(R.drawable.ic_chat);
                break;
            case R.id.liOrders:
                if (isTransporter.equals("1")) {
                    HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), new OrderFragment());
                    binding.tvOrders.setTextColor(Color.parseColor("#F6C21A"));
                    binding.tvTrips.setTextColor(Color.BLACK);
                    binding.tvNotifications.setTextColor(Color.BLACK);
                    binding.tvChat.setTextColor(Color.BLACK);

                    binding.tvTitle.setText(getString(R.string.orders));

                    binding.ivTrip.setImageResource(R.drawable.ic_my_trips);
                    binding.ivOrder.setImageResource(R.drawable.ic_order_selected);
                    binding.ivNotification.setImageResource(R.drawable.ic_notification);
                    binding.ivChat.setImageResource(R.drawable.ic_chat);
                } else {
                    startActivity(new Intent(this, MyOrdersActivity.class));
                }


                break;
            case R.id.ivAdd:
//                if (isTransporter.equals("1")) {
                showPopupDialog();
//                } else {
//                    showPopupCompleteDialog();
//                }

                break;
            case R.id.liNotification:
                HelperClass.replaceFragmentWithoutBack(getSupportFragmentManager().beginTransaction(), new NotificationsFragment());
                binding.tvNotifications.setTextColor(Color.parseColor("#F6C21A"));
                binding.tvOrders.setTextColor(Color.BLACK);
                binding.tvTrips.setTextColor(Color.BLACK);
                binding.tvChat.setTextColor(Color.BLACK);
                binding.tabBadge.setVisibility(View.GONE);
                binding.tvTitle.setText(getString(R.string.notifications));

                binding.ivTrip.setImageResource(R.drawable.ic_my_trips);
                binding.ivOrder.setImageResource(R.drawable.ic_my_orders);
                binding.ivNotification.setImageResource(R.drawable.ic_notification_selected);
                binding.ivChat.setImageResource(R.drawable.ic_chat);
                break;
            case R.id.liChats:
                HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), new ChatFragment());
                binding.tvChat.setTextColor(Color.parseColor("#F6C21A"));
                binding.tvOrders.setTextColor(Color.BLACK);
                binding.tvNotifications.setTextColor(Color.BLACK);
                binding.tvTrips.setTextColor(Color.BLACK);

                binding.tvTitle.setText(getString(R.string.chats));

                binding.ivTrip.setImageResource(R.drawable.ic_my_trips);
                binding.ivOrder.setImageResource(R.drawable.ic_my_orders);
                binding.ivNotification.setImageResource(R.drawable.ic_notification);
                binding.ivChat.setImageResource(R.drawable.ic_chat_selected);
                break;
            case R.id.btnAddShipment:
                intent = new Intent(this, ChooseAddingType.class);
                intent.putExtra("type", "shipment");
                startActivity(intent);
                break;
            case R.id.btnAddTrip:
                if (isTransporter.equals("1")) {
                    intent = new Intent(this, ChooseAddingType.class);
                    intent.putExtra("type", "trip");
                    startActivity(intent);
                } else {
                    showPopupCompleteDialog();
                }

                break;
            case R.id.ivClose:
                dialog.dismiss();
                break;

            case R.id.ivFilter:
                if (binding.tvTitle.getText().toString().equals(getString(R.string.trips))) {
                    popupTripSearch();
                } else {
                    popupOrderSearch();
                }

                break;

        }
    }

    private void showPopupCompleteDialog() {
        dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.popup_complete_data);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        Button btnComplete = dialog.findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, JoinAsTransporter.class));

            }
        });
        ivClose.setOnClickListener(this);
        dialog.show();
    }

    private void showPopupDialog() {
        dialog = new Dialog(this);
//        PopupAddShipmentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.popup_add_shipment, null, false);
//        setContentView(binding.getRoot());
//        binding.btnAddShipment.setOnClickListener(this);
//        binding.btnAddTrip.setOnClickListener(this);
        dialog.setContentView(R.layout.popup_add_shipment);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        CardView btnAddShipment = dialog.findViewById(R.id.btnAddShipment);
        Button btnAddTrip = dialog.findViewById(R.id.btnAddTrip);
        btnAddShipment.setOnClickListener(this);
        btnAddTrip.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        dialog.show();
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
                    countriesAdapter = new CountriesAdapter(HomeActivity.this, countriesModels);
                    rvCities.setAdapter(countriesAdapter);
                    rvCities.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false));
                    countriesAdapter.notifyDataSetChanged();
                    countriesAdapter.onNameClic(new CountriesAdapter.NameClick() {
                        @Override
                        public void onNameClick(int pos) {
                            dialog.cancel();
                            if (type.equals("from")) {
                                fromCountryName = countriesModels.get(pos).getCountry_name();
                                fromCountryID = countriesModels.get(pos).getCountry_id();
                            } else {
                                toCountryName = countriesModels.get(pos).getCountry_name();
                                toCountryID = countriesModels.get(pos).getCountry_id();

                            }
                            countriesList.clear();
                            countriesAdapter.notifyDataSetChanged();
                            if (type.equals("from")) {
                                presenter.getCities(HomeActivity.this, "" + fromCountryID);
                            } else {
                                presenter.getCities(HomeActivity.this, "" + toCountryID);
                            }
                        }
                    });
                } else {
                    findAds(citiesList, s);
                    citiesAdapter = new CitiesAdapter(HomeActivity.this, models);
                    rvCities.setAdapter(citiesAdapter);
                    rvCities.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false));
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
    public void getCountries(final List<CountriesResponse.Return> returnList) {
        countriesList = returnList;
        getSearchResult("countries");
        countriesAdapter = new CountriesAdapter(this, returnList);
        rvCities.setAdapter(countriesAdapter);
        rvCities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        countriesAdapter.onNameClic(new CountriesAdapter.NameClick() {
            @Override
            public void onNameClick(int pos) {
                dialog.cancel();
                if (type.equals("from")) {
                    fromCountryName = returnList.get(pos).getCountry_name();
                    fromCountryID = returnList.get(pos).getCountry_id();
                } else {
                    toCountryName = returnList.get(pos).getCountry_name();
                    toCountryID = returnList.get(pos).getCountry_id();

                }
                countriesList.clear();
                countriesAdapter.notifyDataSetChanged();
                if (type.equals("from")) {
                    presenter.getCities(HomeActivity.this, "" + fromCountryID);
                } else {
                    presenter.getCities(HomeActivity.this, "" + toCountryID);
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
                            fromCityName = returnList.get(pos).getCity_name();
                            fromCityID = returnList.get(pos).getCity_id();
                            etDeparturePoint.setText(returnList.get(pos).getCity_name());
                        } else {
                            toCityName = returnList.get(pos).getCity_name();
                            toCityID = returnList.get(pos).getCity_id();
                            etArrivalPoint.setText(returnList.get(pos).getCity_name());
                        }
                        dialog.dismiss();
                    }
                });
            }
        }
    }

    @Override
    public void addTrip(boolean isAdded) {

    }

    @Override
    public void getTrips(List<TripsModelResponse.Returns> returns) {

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
    }

    @Override
    public void isTransporter(String adv_driver_status) {
        isTransporter = adv_driver_status;
        storage.storeKey("isTransporter", adv_driver_status);
        if (adv_driver_status.equals("1")) {
            mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuDrawerTrans)));
        } else {
            mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuDrawer)));

        }
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        handleDrawer();
        handleMenu();
    }

    @Override
    public void getOrderTypes(final List<OrderTypeResponse.ReturnsEntity> returns) {
        OrderTypesAdapter orderTypesAdapter = new OrderTypesAdapter(this, returns, storage.getKey("servTypeOrder"));
        rvOrderTypes.setAdapter(orderTypesAdapter);
        rvOrderTypes.setLayoutManager(new GridLayoutManager(this, 3));
        orderTypesAdapter.setCarTypeClick(new OrderTypesAdapter.CarTypeClick() {
            @Override
            public void onCarTypeClick(int pos) {
                servTypeID = returns.get(pos).getType_id();
            }
        });
    }

    @Override
    public void getCarTypes(final List<CarTypeResponse.ReturnsEntity> returns) {
        carType = returns.get(0).getCartype_id();
        CarTypesAdapter carTypesAdapter;
        if (!storage.getKey("from").equals("null")) {
            if (storage.getKey("from").equals("order")) {
                carTypesAdapter = new CarTypesAdapter(this, returns, storage.getKey("carTypeOrder"));
            } else {
                carTypesAdapter = new CarTypesAdapter(this, returns, storage.getKey("carType"));
            }
        } else {
            carTypesAdapter = new CarTypesAdapter(this, returns, "0");
        }

        rvCarTypes.setAdapter(carTypesAdapter);
        rvCarTypes.setLayoutManager(new GridLayoutManager(this, 3));
        carTypesAdapter.setCarTypeClick(new CarTypesAdapter.CarTypeClick() {
            @Override
            public void onCarTypeClick(int pos) {
                carType = returns.get(pos).getCartype_id();
            }
        });
        for (int i = 0; i < returns.size(); i++) {
            if (storage.getKey("carType").equals(returns.get(i).getCartype_id())) {

            }
        }
    }

    @Override
    public void updateView(String type) {
        if (type.equals("trips")) {
            binding.tvTrips.setTextColor(Color.parseColor("#F6C21A"));
            binding.tvOrders.setTextColor(Color.BLACK);
            binding.tvNotifications.setTextColor(Color.BLACK);
            binding.tvChat.setTextColor(Color.BLACK);

            binding.tvTitle.setText(getString(R.string.trips));

            binding.ivTrip.setImageResource(R.drawable.ic_trip_selected);
            binding.ivOrder.setImageResource(R.drawable.ic_my_orders);
            binding.ivNotification.setImageResource(R.drawable.ic_notification);
            binding.ivChat.setImageResource(R.drawable.ic_chat);

        } else if (type.equals("chat")) {
            binding.tvChat.setTextColor(Color.parseColor("#F6C21A"));
            binding.tvOrders.setTextColor(Color.BLACK);
            binding.tvNotifications.setTextColor(Color.BLACK);
            binding.tvTrips.setTextColor(Color.BLACK);

            binding.tvTitle.setText(getString(R.string.chats));

            binding.ivTrip.setImageResource(R.drawable.ic_my_trips);
            binding.ivOrder.setImageResource(R.drawable.ic_my_orders);
            binding.ivNotification.setImageResource(R.drawable.ic_notification);
            binding.ivChat.setImageResource(R.drawable.ic_chat_selected);
        } else {
            binding.tvOrders.setTextColor(Color.parseColor("#F6C21A"));
            binding.tvTrips.setTextColor(Color.BLACK);
            binding.tvNotifications.setTextColor(Color.BLACK);
            binding.tvChat.setTextColor(Color.BLACK);

            binding.tvTitle.setText(getString(R.string.orders));

            binding.ivTrip.setImageResource(R.drawable.ic_my_trips);
            binding.ivOrder.setImageResource(R.drawable.ic_order_selected);
            binding.ivNotification.setImageResource(R.drawable.ic_notification);
            binding.ivChat.setImageResource(R.drawable.ic_chat);
        }
    }

    public class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;
        ImageView ivProfile;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = findViewById(R.id.toolbar);
//            TextView logout = mDuoMenuView.findViewById(R.id.duo_view_footer_text);
            TextView name = mDuoMenuView.findViewById(R.id.duo_view_header_text_title);
            Button tvLoginState = mDuoMenuView.findViewById(R.id.tvLoginState);
            TextView email = mDuoMenuView.findViewById(R.id.duo_view_header_text_sub_title);
            ImageView ivClear = mDuoMenuView.findViewById(R.id.ivClear);
            ivProfile = mDuoMenuView.findViewById(R.id.ivProfile);
            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                mDuoDrawerLayout.setGravity(Gravity.END);
            }

            ivClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDuoDrawerLayout.closeDrawer();
                }
            });

            tvLoginState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!storage.isFirstTimeLogin()) {
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        storage.setFirstTimeLogin(false);
//                        storage.clearSharedPref();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            });

            if (!storage.isFirstTimeLogin()) {
                name.setText(getString(R.string.welcome));
                tvLoginState.setText(getString(R.string.login));
//                ivProfile.setImageResource(R.drawable.ic_user);
            } else {
                tvLoginState.setText(getString(R.string.logout));
                name.setText(storage.getKey("name"));
                String image = storage.getKey("photo");
                Log.e("photo", "" + image);

                if (!image.isEmpty()) {
                    Picasso.with(HomeActivity.this).load("" + image).into(ivProfile);
                } else {
                    ivProfile.setImageResource(R.drawable.login_header);
                }
            }
        }
    }

    @Override
    public void getNotifications(final List<NotificationModel.ReturnsEntity> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getNotify_read().contains("0")) {
                binding.tabBadge.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void isRead(boolean b) {
        if (b) {

        }
    }

    @Override
    public void statusUpdate(boolean b) {

    }

    @Override
    public void isDeleted(boolean isDeleted) {

    }
}

