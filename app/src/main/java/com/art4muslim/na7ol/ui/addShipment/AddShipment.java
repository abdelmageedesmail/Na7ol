package com.art4muslim.na7ol.ui.addShipment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;


import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ActivityAddTripBinding;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.add_trip.AddTripActivity;
import com.art4muslim.na7ol.ui.add_trip.CarTypesAdapter;
import com.art4muslim.na7ol.ui.add_trip.CitiesAdapter;
import com.art4muslim.na7ol.ui.add_trip.CountriesAdapter;
import com.art4muslim.na7ol.ui.add_trip.TripPresenter;
import com.art4muslim.na7ol.ui.add_trip.TripView;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.ui.joinTransporter.JoinAsTransporter;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddShipment extends AppCompatActivity implements TripView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rdPlan)
    RadioButton rdPlan;
    @BindView(R.id.rdCars)
    RadioButton rdCars;
    @BindView(R.id.rdTrain)
    RadioButton rdTrain;
    @BindView(R.id.etDeparturePoint)
    EditText etDeparturePoint;
    @BindView(R.id.etArrivalPoint)
    EditText etArrivalPoint;
    @BindView(R.id.rdDocument)
    RadioButton rdDocument;
    @BindView(R.id.rdPackage)
    RadioButton rdPackage;
    @BindView(R.id.rdSpecified)
    RadioButton rdSpecified;
    @BindView(R.id.etWeight)
    Spinner etWeight;
    @BindView(R.id.etDeliveryTime)
    EditText etDeliveryTime;
    @BindView(R.id.etNotes)
    EditText etNotes;
    @BindView(R.id.etRecipientName)
    EditText etRecipientName;
    @BindView(R.id.etRecipientMobile)
    EditText etRecipientMobile;
    @BindView(R.id.btnComplete)
    Button btnComplete;


    @Inject
    TripPresenter presenter;
    @BindView(R.id.ivShipmentImage)
    CircleImageView ivShipmentImage;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.liAddImage)
    LinearLayout liAddImage;
    @BindView(R.id.spTripType)
    Spinner spTripType;
    @BindView(R.id.liTripType)
    LinearLayout liTripType;
    @BindView(R.id.rvTypes)
    RecyclerView rvTypes;
    @BindView(R.id.rvOrderTypes)
    RecyclerView rvOrderTypes;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;

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
    private String servTypeID = "";
    private String servTypeIDs = "";


    private int selectedCam = 5;
    private int REQUEST_CAMERA = 0;
    public static Bitmap thumbnail;
    private static final int GALLERYCODE = 100;
    public static Bitmap bitmap;

    public static File destination;
    private ContentValues values;
    private Uri imageUri;
    private String imageurl;

    private static final int SELECT_FILE = 1;
    private Bitmap bm;
    private Uri selectedImageUri;
    private String selectedImagePath;
    private String substring;
    private String imagepath;
    private File imageFile;
    private String date;
    public static TripsModelResponse.Returns model;
    private String weightId;
    private String tripType, phoneCode;
    private List<CitiesResponse.Return> models = new ArrayList<>();
    private List<CountriesResponse.Return> countriesModels = new ArrayList<>();
    private SearchView searchView;
    String year;
    private String enDate;
    private String endTime, sendDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_shipment);
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        presenter.setTripView(this);
        presenter.getCarTypes(this);
        presenter.getOrdersTypes(this);
        if (getIntent().getExtras().getString("from").equals("edit")) {
            model = new Gson().fromJson(getIntent().getStringExtra("model"), new TypeToken<TripsModelResponse.Returns>() {
            }.getType());
            fillData();
        } else {
            carType = getIntent().getExtras().getString("carType");
            if (getIntent().getExtras().getString("carType").equals("3")) {
                rdPlan.setChecked(true);
                carType = "3";
                liTripType.setVisibility(View.VISIBLE);
            } else if (getIntent().getExtras().getString("carType").equals("2")) {
                rdCars.setChecked(true);
                carType = "2";
                liTripType.setVisibility(View.GONE);
            } else if (getIntent().getExtras().getString("carType").equals("1")) {
                rdTrain.setChecked(true);
                carType = "1";
                liTripType.setVisibility(View.GONE);
            }
        }
        presenter.getWeights(this);
        storage = new PrefrencesStorage(this);
        rdPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "3";
                    liTripType.setVisibility(View.VISIBLE);
                }
            }
        });
        rdCars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "2";
                    liTripType.setVisibility(View.GONE);

                }
            }
        });
        rdTrain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    carType = "1";
                    liTripType.setVisibility(View.GONE);

                }
            }
        });

        if (rdDocument.isChecked()) {
            servTypeID = "1";
        }
        if (rdPackage.isChecked()) {
            servTypeID = "2";
        }
        if (rdSpecified.isChecked()) {
            servTypeID = "3";
        }

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
        spTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        phoneCode = ccp.getDefaultCountryCode();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                phoneCode = selectedCountry.getPhoneCode();
            }
        });

    }

    private void fillData() {

        carType = model.getServ_cartype_id();
        tvTitle.setText(getString(R.string.editShipment));
        btnComplete.setText(getString(R.string.edit));
        if (model.getServ_cartype_id().equals("1")) {
            rdTrain.setChecked(true);
        } else if (model.getServ_cartype_id().equals("2")) {
            rdCars.setChecked(true);
        } else if (model.getServ_cartype_id().equals("3")) {
            rdPlan.setChecked(true);
        }

        if (model.getServ_order_type_id() != null) {
            String replace = model.getServ_order_type_id().replace("|", "");
            if (replace.equals("1")) {
                rdDocument.setChecked(true);
            } else if (replace.equals("2")) {
                rdPackage.setChecked(true);
            } else if (replace.equals("3")) {
                rdSpecified.setChecked(true);
            }
        }

//        .replace("|", "")
        servTypeID = model.getServ_order_type_id();
        servTypeIDs = model.getServ_order_type_id();
        fromCountryID = Integer.parseInt(model.getServ_from_country_id());
        toCountryID = Integer.parseInt(model.getServ_to_country_id());
        fromCityID = Integer.parseInt(model.getServ_from_city_id());
        toCityID = Integer.parseInt(model.getServ_to_city_id());
        etDeparturePoint.setText(model.getFrom_city_name());
        etArrivalPoint.setText(model.getTo_city_name());
        etDeliveryTime.setText(model.getServ_time());
        etNotes.setText(model.getServ_details());
        if (model.getServ_receiver_name() != null) {
            if (!model.getServ_receiver_name().isEmpty()) {
                etRecipientName.setText(model.getServ_receiver_name());
                etRecipientMobile.setText(model.getServ_receiver_mobile());
            }
        }
        if (!model.getImageUrl().isEmpty()) {
            Picasso.with(this).load(model.getImageUrl()).into(ivShipmentImage);
//            getImageFile(model.getImageUrl());
        }

    }


    private void setDate() {
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
                year = "" + selectedyear;
                enDate = year + "-" + month + "-" + hDay;
                if (Utils.getLang(AddShipment.this).equals("ar")) {
                    year = getArabicNumbers("" + year);
                    month = getArabicNumbers("" + month);
                    hDay = getArabicNumbers("" + hDay);

                }
                date = year + "-" + month + "-" + hDay;
                setTime();
            }
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDatePicker.show();
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

    private void setTime() {
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
                endTime = hour + ":" + minute + ":" + "00";
                if (Utils.getLang(AddShipment.this).equals("ar")) {
                    hour = getArabicNumbers("" + selectedHour);
                    minute = getArabicNumbers("" + selectedMinute);
                }
                sendDate = enDate + " " + endTime;
                etDeliveryTime.setText(date + " " + hour + ":" + minute + ":" + "00");
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void setUpValidation() {
        servTypeID = "";
        for (String s : MultiOrderTypesAdapter.orderTypes) {
            if (MultiOrderTypesAdapter.orderTypes.size() > 0) {
                servTypeID += s + "|";
            } else {
                servTypeID = s;
            }

        }
        if (carType == null) {
            Toast.makeText(this, getString(R.string.pleaseSelctTravelType), Toast.LENGTH_SHORT).show();
        } else if (etDeparturePoint.getText().toString().isEmpty()) {
            etDeparturePoint.setError(getString(R.string.empty));
        } else if (etArrivalPoint.getText().toString().isEmpty()) {
            etArrivalPoint.setError(getString(R.string.empty));
        } else if (etDeliveryTime.getText().toString().isEmpty()) {
            etDeliveryTime.setError(getString(R.string.empty));
        } else if (servTypeID == null) {
            Toast.makeText(this, getString(R.string.pleaseSelectPackageType), Toast.LENGTH_SHORT).show();
        } else if (etNotes.getText().toString().isEmpty()) {
            etNotes.setError(getString(R.string.empty));
        } else if (etRecipientName.getText().toString().isEmpty()) {
            etRecipientName.setError(getString(R.string.empty));
        } else if (checkName()) {
            Toast.makeText(this, getString(R.string.nameMustNotHaveNumber), Toast.LENGTH_SHORT).show();
        } else if (etRecipientName.getText().toString().length() < 3) {
            Toast.makeText(this, getString(R.string.nameMustBeGreaterThanThreeCha), Toast.LENGTH_SHORT).show();
        } else if (etRecipientName.getText().toString().length() > 50) {
            Toast.makeText(this, getString(R.string.nameMustBeGreaterThanlessCha), Toast.LENGTH_SHORT).show();
        } else if (etRecipientName.getText().toString().startsWith(" ")) {
            Toast.makeText(this, getString(R.string.nameMustNotStartWithSpace), Toast.LENGTH_SHORT).show();
        } else if (etRecipientMobile.getText().toString().isEmpty()) {
            etRecipientMobile.setError(getString(R.string.empty));
        } else if (servTypeID.isEmpty()) {
            Toast.makeText(this, getString(R.string.selectPackageType), Toast.LENGTH_SHORT).show();
        } else if (imageFile == null) {
            Toast.makeText(this, getString(R.string.youShouldAddShipmentImage), Toast.LENGTH_SHORT).show();
        } else if (etDeparturePoint.getText().toString().equals(etArrivalPoint.getText().toString()) & !carType.equals("2")) {
            Toast.makeText(this, getString(R.string.pleaseSelectDifferentCities), Toast.LENGTH_SHORT).show();
        } else if (etRecipientMobile.getText().toString().length() < 10) {
            Toast.makeText(this, getString(R.string.phoneError), Toast.LENGTH_SHORT).show();
        } else if (etRecipientMobile.getText().toString().length() > 15) {
            Toast.makeText(this, getString(R.string.phoneError), Toast.LENGTH_SHORT).show();
        } else {


            if (getIntent().getExtras().getString("from").equals("edit")) {
                presenter.editTrip(this, storage.getId(), carType, "" + fromCountryID, "" + fromCityID, "" + toCountryID, "" + toCityID, "order", tripType, "", ""
                        , etNotes.getText().toString(), weightId, servTypeID, etRecipientName.getText().toString(), phoneCode + etRecipientMobile.getText().toString(), sendDate, imageFile, model.getServ_id());
            } else {
                presenter.addTrip(this, storage.getId(), carType, "" + fromCountryID, "" + fromCityID, "" + toCountryID, "" + toCityID, "order", tripType, "", ""
                        , etNotes.getText().toString(), weightId, servTypeID, etRecipientName.getText().toString(), phoneCode + etRecipientMobile.getText().toString(), sendDate, imageFile);
            }
        }
    }

    private boolean checkName() {
        boolean hasNum = false;
        if (etRecipientName.getText().toString().contains("0") || etRecipientName.getText().toString().contains("1") || etRecipientName.getText().toString().contains("2") || etRecipientName.getText().toString().contains("3") || etRecipientName.getText().toString().contains("4") || etRecipientName.getText().toString().contains("5") || etRecipientName.getText().toString().contains("6") || etRecipientName.getText().toString().contains("7") || etRecipientName.getText().toString().contains("8") || etRecipientName.getText().toString().contains("9")) {
            hasNum = true;
        } else {
            hasNum = false;
        }
        return hasNum;
    }


    private void setUpEditValidation() {
        servTypeID = "";
        for (String s : MultiOrderTypesAdapter.orderTypes) {
            if (MultiOrderTypesAdapter.orderTypes.size() > 0) {
                servTypeID += s + "|";
            } else {
                servTypeID = s;
            }

        }
        if (carType == null) {
            Toast.makeText(this, getString(R.string.pleaseSelctTravelType), Toast.LENGTH_SHORT).show();
        } else if (etDeparturePoint.getText().toString().isEmpty()) {
            etDeparturePoint.setError(getString(R.string.empty));
        } else if (etArrivalPoint.getText().toString().isEmpty()) {
            etArrivalPoint.setError(getString(R.string.empty));
        } else if (etDeliveryTime.getText().toString().isEmpty()) {
            etDeliveryTime.setError(getString(R.string.empty));
        } else if (servTypeID == null) {
            Toast.makeText(this, getString(R.string.pleaseSelectPackageType), Toast.LENGTH_SHORT).show();
        } else if (etNotes.getText().toString().isEmpty()) {
            etNotes.setError(getString(R.string.empty));
        } else if (etRecipientName.getText().toString().isEmpty()) {
            etRecipientName.setError(getString(R.string.empty));
        } else if (etRecipientMobile.getText().toString().isEmpty()) {
            etRecipientMobile.setError(getString(R.string.empty));
        } else if (servTypeID == null) {
            Toast.makeText(this, getString(R.string.selectPackageType), Toast.LENGTH_SHORT).show();
        } else if (etDeparturePoint.getText().toString().equals(etArrivalPoint.getText().toString())) {
            Toast.makeText(this, getString(R.string.pleaseSelectDifferentCities), Toast.LENGTH_SHORT).show();
        } else {
            presenter.editTrip(this, storage.getId(), carType, "" + fromCountryID, "" + fromCityID, "" + toCountryID, "" + toCityID, "order", tripType, "", ""
                    , etNotes.getText().toString(), weightId, servTypeID, etRecipientName.getText().toString(), etRecipientMobile.getText().toString(), etDeliveryTime.getText().toString(), imageFile, model.getServ_id());

        }
    }

    public void getImageFile(final String url) {
        Picasso.with(getApplicationContext()).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Uri localBitmapUri = getLocalBitmapUri(bitmap);
                imageFile = new File(localBitmapUri.getPath());

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
//            Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider", createImageFile());
            bmpUri = FileProvider.getUriForFile(this, "com.art4muslim.na7ol.provider", file);
//            Uri outputFileUri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID, newfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @OnClick({R.id.etDeparturePoint, R.id.etArrivalPoint, R.id.ivBack, R.id.btnComplete, R.id.liAddImage, R.id.etDeliveryTime})
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
            case R.id.liAddImage:
                showPopUpCamera();
                break;
            case R.id.etDeliveryTime:
                setDate();
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnComplete:
                if (getIntent().getExtras().getString("from").equals("edit")) {
                    setUpEditValidation();
                } else {
                    setUpValidation();
                }

                break;
        }
    }


    private void selectImage() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_FILE);

    }


    private void showPopUpCamera() {
        final CharSequence[] items = {getString(R.string.pickPicture), getString(R.string.chooseFromG),
                getString(R.string.cancle)};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddShipment.this);
        builder.setTitle(getString(R.string.addPhoto));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.pickPicture))) {
                    values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    imageUri = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, selectedCam);
//                        selectedCam += 1;
                    Log.e("selected", "" + selectedCam);


                } else if (items[item].equals(getString(R.string.chooseFromG))) {
                    selectImage();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == selectedCam)
                onCaptureImageResult(data, requestCode);
        }
    }

    private void onCaptureImageResult(Intent data, int requestCode) {
        try {
            thumbnail = MediaStore.Images.Media.getBitmap(
                    getContentResolver(), imageUri);
            imageurl = getRealPathFromURI(imageUri);
            imageFile = new File(imageurl);
            destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");

            imageFile = new File(imageurl);

            ivShipmentImage.setImageBitmap(thumbnail);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bmb = null;
        selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        selectedImagePath = cursor.getString(column_index);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        substring = selectedImagePath.substring(selectedImagePath.lastIndexOf(".") + 1);

        imagepath = selectedImageUri.getPath();
        imageFile = new File(selectedImagePath);


        Bitmap finalImage = null;
        try {
            ExifInterface ei = new ExifInterface(selectedImagePath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            Log.e("orientation", orientation + "");
            //finalImage = rotateImage(bm, 270);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    finalImage = rotateImage(bm, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    finalImage = rotateImage(bm, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    finalImage = rotateImage(bm, 270);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (finalImage != null) {
            ivShipmentImage.setImageBitmap(finalImage);
        } else {
            ivShipmentImage.setImageBitmap(bm);
        }

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
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
                    countriesAdapter = new CountriesAdapter(AddShipment.this, countriesModels);
                    rvCities.setAdapter(countriesAdapter);
                    rvCities.setLayoutManager(new LinearLayoutManager(AddShipment.this, LinearLayoutManager.VERTICAL, false));
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
                                presenter.getCities(AddShipment.this, "" + fromCountryID);
                            } else {
                                presenter.getCities(AddShipment.this, "" + toCountryID);
                            }
                        }
                    });
                } else {
                    findAds(citiesList, s);
                    citiesAdapter = new CitiesAdapter(AddShipment.this, models);
                    rvCities.setAdapter(citiesAdapter);
                    rvCities.setLayoutManager(new LinearLayoutManager(AddShipment.this, LinearLayoutManager.VERTICAL, false));
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

                        ArrayAdapter reportAdapter = new ArrayAdapter(AddShipment.this,
                                android.R.layout.simple_spinner_dropdown_item, flights);
                        spTripType.setAdapter(reportAdapter);
                        spTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                        ArrayAdapter reportAdapter = new ArrayAdapter(AddShipment.this,
                                android.R.layout.simple_spinner_dropdown_item, flights);
                        spTripType.setAdapter(reportAdapter);
                        spTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    presenter.getCities(AddShipment.this, "" + fromCountryID);
                } else {
                    presenter.getCities(AddShipment.this, "" + toCountryID);
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
                            etDeparturePoint.setText(returnList.get(pos).getCity_name());
                        } else {
                            toCityID = returnList.get(pos).getCity_id();
                            etArrivalPoint.setText(returnList.get(pos).getCity_name());
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
                Toast.makeText(this, getString(R.string.shipmentEdited), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MyOrdersActivity.class));
                finish();
            } else {
                Toast.makeText(this, getString(R.string.orderAddedSuccessfully), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
            }

        }
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
        if (getIntent().getExtras().getString("from").equals("edit")) {
            for (int i = 0; i < returns.size(); i++) {
                if (model.getServ_weight_name().equals(returns.get(i).getPrice_min_weight() + " / " + returns.get(i).getPrice_max_weight())) {
                    etWeight.setSelection(i);
                }
            }
        }
    }

    @Override
    public void getCarTypes(final List<CarTypeResponse.ReturnsEntity> returns) {
        CarTypesAdapter carTypesAdapter = new CarTypesAdapter(this, returns, carType);
        rvTypes.setAdapter(carTypesAdapter);
        if (getIntent().getExtras().getString("carTypeName") != null) {
            if (getIntent().getExtras().getString("carTypeName").equals("طيران") || getIntent().getExtras().getString("carTypeName").equals("plane")) {
                liTripType.setVisibility(View.VISIBLE);
            } else {
                liTripType.setVisibility(View.GONE);
            }
        } else {
            liTripType.setVisibility(View.GONE);
        }

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
        orderTypesAdapter.setCarTypeClick(new MultiOrderTypesAdapter.CarTypeClick() {
            @Override
            public void onCarTypeClick(int pos) {
                servTypeID = returns.get(pos).getType_id();
                Log.e("servTypeIddd", "" + servTypeID);
            }
        });
    }
}
