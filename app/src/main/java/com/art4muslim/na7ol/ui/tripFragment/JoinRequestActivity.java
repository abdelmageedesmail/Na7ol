package com.art4muslim.na7ol.ui.tripFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;


import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.DeliveryMethodModel;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.addShipment.AddShipment;
import com.art4muslim.na7ol.ui.addShipment.MultiOrderTypesAdapter;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.ui.orderFragment.TripsPresenter;
import com.art4muslim.na7ol.ui.orderFragment.TripsView;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class JoinRequestActivity extends AppCompatActivity implements TripsView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rdDocument)
    RadioButton rdDocument;
    @BindView(R.id.rdPackage)
    RadioButton rdPackage;
    @BindView(R.id.rdSpecified)
    RadioButton rdSpecified;
    @BindView(R.id.spDelivery)
    Spinner spDelivery;
    @BindView(R.id.etNotes)
    EditText etNotes;
    @BindView(R.id.ivShipmentImage)
    CircleImageView ivShipmentImage;
    @BindView(R.id.liAddImage)
    LinearLayout liAddImage;
    @BindView(R.id.etRecipientName)
    EditText etRecipientName;
    @BindView(R.id.etRecipientMobile)
    EditText etRecipientMobile;
    @BindView(R.id.btnComplete)
    Button btnComplete;
    @Inject
    TripsPresenter presenter;
    @BindView(R.id.rvOrderTypes)
    RecyclerView rvOrderTypes;
    @BindView(R.id.etPriceOffer)
    EditText etPriceOffer;

    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    private String deliveryId;
    PrefrencesStorage storage;
    private String servTypeID;

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
    private String imagepath, symbol, phoneCode;
    private File imageFile;
    private Dialog dialog;
    private AgreementsResponseModel.ReturnsEntity model;
    private String price;
    private String blockCharacterSet = "~#^|$%&*!@.";
    String arabicNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request);
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        storage = new PrefrencesStorage(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }

        phoneCode = ccp.getDefaultCountryCode();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(com.rilixtech.widget.countrycodepicker.Country selectedCountry) {
                phoneCode = selectedCountry.getPhoneCode();
            }
        });
        presenter.setView(this);
        presenter.getOrdersTypes(this);
        presenter.getWeights(this);
        if (getIntent().getExtras().getString("from").equals("edit")) {
            model = new Gson().fromJson(getIntent().getStringExtra("model"), new TypeToken<AgreementsResponseModel.ReturnsEntity>() {
            }.getType());
            fillData();
        } else {

        }

        presenter.getDeliveryMethod(this);
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

        etNotes.setFilters(new InputFilter[]{filter});

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
        if (model.getServ_delivery_type_id() != null) {
            if (model.getServ_delivery_type_id().equals("1")) {
                rdDocument.setChecked(true);
            } else if (model.getServ_delivery_type_id().equals("2")) {
                rdPackage.setChecked(true);
            } else if (model.getServ_delivery_type_id().equals("3")) {
                rdSpecified.setChecked(true);
            }
        }
        etNotes.setText(model.getAgr_details());
        if (!model.getImageUrl().isEmpty()) {
            Picasso.with(this).load(model.getImageUrl()).into(ivShipmentImage);
        }
        if (model.getServ_receiver_name() != null) {
            if (!model.getServ_receiver_name().isEmpty()) {
                etRecipientName.setText(model.getServ_receiver_name());
                etRecipientMobile.setText(model.getServ_receiver_mobile());
            } else {
                etRecipientName.setText(model.getAgr_receiver_name());
                etRecipientMobile.setText(model.getAgr_receiver_mobile());
            }
        }

    }


    @OnClick({R.id.ivBack, R.id.btnComplete, R.id.liAddImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
//                onBackPressed();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;
            case R.id.liAddImage:
                showPopUpCamera();
                break;
            case R.id.btnComplete:
                for (String s : MultiOrderTypesAdapter.orderTypes) {
                    if (MultiOrderTypesAdapter.orderTypes.size() > 0) {
                        servTypeID += s + "|";
                    } else {
                        servTypeID = s;
                    }

                }
                if (servTypeID == null) {
                    Toast.makeText(this, getString(R.string.pleaseSelectPackageType), Toast.LENGTH_SHORT).show();
                } else if (etPriceOffer.getText().toString().isEmpty()) {
                    etPriceOffer.setError(getString(R.string.empty));
                } else if (etPriceOffer.getText().toString().length() > 6) {
                    Toast.makeText(this, getString(R.string.priceMustBeLessThanFourDigit), Toast.LENGTH_SHORT).show();
                } else if (etNotes.getText().toString().isEmpty()) {
                    etNotes.setError(getString(R.string.empty));
                } else if (imageFile == null) {
                    Toast.makeText(this, getString(R.string.pleaseAddImage), Toast.LENGTH_SHORT).show();
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
                } else if (etRecipientMobile.getText().toString().length() < 9) {
                    Toast.makeText(this, getString(R.string.phoneError), Toast.LENGTH_SHORT).show();
                } else if (etRecipientMobile.getText().toString().length() > 12) {
                    Toast.makeText(this, getString(R.string.phoneError), Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(etPriceOffer.getText().toString()) < Double.parseDouble(price)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(getString(R.string.alert))
                            .setMessage(getString(R.string.offerMustBeGreaterThanLimit) + price)
                            .setPositiveButton(getString(R.string.done), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).show();
                } else {
                    presenter.joinTrip(this, getIntent().getExtras().getString("fromId"), storage.getId(), getIntent().getExtras().getString("servId"), getIntent().getExtras().getString("weight"), servTypeID, etNotes.getText().toString(), etRecipientName.getText().toString(), phoneCode + etRecipientMobile.getText().toString(), deliveryId, imageFile, etPriceOffer.getText().toString());
                }

                break;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


    private void showMessage() {
        dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.popup_success);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        tvMessage.setText(getString(R.string.joinedSuccess));
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinRequestActivity.this, MyOrdersActivity.class);
//                intent.putExtra("type", "myTrips");
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void getTrips(List<TripsModelResponse.Returns> list, boolean canLoadMore) {

    }

    @Override
    public void getFliterTrips(List<TripsModelResponse.Returns> list, boolean canLoadMore) {

    }

    @Override
    public void isJoined(boolean b) {
        if (b) {
            showMessage();
        } else {
            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getMethods(final List<DeliveryMethodModel.ReturnsEntity> returns) {
        if (getIntent().getExtras().getString("from").equals("edit")) {
            spDelivery.setAdapter(new DeliveryMethodAdapter(this, returns));
            spDelivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    deliveryId = returns.get(i).getType_id();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            for (int i = 0; i < returns.size(); i++) {
                if (model.getAgr_delivery_type_id().equals(returns.get(i).getType_id())) {
                    spDelivery.setSelection(i);
                }
            }
        } else {
            spDelivery.setAdapter(new DeliveryMethodAdapter(this, returns));
            spDelivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    deliveryId = returns.get(i).getType_id();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    @Override
    public void getCarTypes(List<CarTypeResponse.ReturnsEntity> returns) {

    }

    @Override
    public void getOrderTypes(final List<OrderTypeResponse.ReturnsEntity> returns) {
        MultiOrderTypesAdapter orderTypesAdapter = new MultiOrderTypesAdapter(this, returns, null);
        rvOrderTypes.setAdapter(orderTypesAdapter);
        rvOrderTypes.setLayoutManager(new GridLayoutManager(this, 3));
        orderTypesAdapter.setCarTypeClick(new MultiOrderTypesAdapter.CarTypeClick() {
            @Override
            public void onCarTypeClick(int pos) {
                servTypeID = returns.get(pos).getType_id();
            }
        });
    }

    @Override
    public void getWeights(List<WeightsResponse.ReturnsEntity> returns) {
        for (int i = 0; i < returns.size(); i++) {
            if (getIntent().getExtras().getString("weight").equals(returns.get(i).getPrice_id())) {
                price = returns.get(i).getPrice_price();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}