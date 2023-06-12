package com.art4muslim.na7ol.ui.joinTransporter;

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
import android.os.Handler;
import android.provider.MediaStore;


import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.login.LoginActivity;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.SplashActivity;
import com.art4muslim.na7ol.ui.add_trip.CitiesAdapter;
import com.art4muslim.na7ol.ui.add_trip.CountriesAdapter;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class JoinAsTransporter extends AppCompatActivity implements JoinTransporterView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etNationalId)
    EditText etNationalId;
    @BindView(R.id.etCity)
    EditText etCity;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.btnJoin)
    Button btnJoin;
    @Inject
    JoinTransporterPresenter presenter;
    @BindView(R.id.ivProfile)
    CircleImageView ivProfile;
    @BindView(R.id.ivShipmentImage)
    CircleImageView ivShipmentImage;
    @BindView(R.id.liAddImage)
    LinearLayout liAddImage;
    private Dialog dialog;
    RecyclerView rvCities;
    private List<CountriesResponse.Return> countriesList = new ArrayList<>();
    private CountriesAdapter countriesAdapter;
    private int fromCountryID;
    private List<CitiesResponse.Return> citiesList = new ArrayList<>();
    private CitiesAdapter citiesAdapter;
    private int fromCityID;

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
    private PrefrencesStorage storage;
    private String imageType;
    private File idFile;
    Runnable run;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_as_transporter);

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
        storage = new PrefrencesStorage(this);
        setTextOnly();
    }

    private void setTextOnly() {
        InputFilter[] Textfilters = new InputFilter[1];
        Textfilters[0] = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end > start) {

//                    char[] acceptedChars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '.','أ','ا','ب',};
                    char[] acceptedChars = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};

                    for (int index = start; index < end; index++) {
                        if (new String(acceptedChars).contains(String.valueOf(source.charAt(index)))) {
                            return "";
                        }
                    }
                }
                return null;
            }

        };
        etName.setFilters(Textfilters);
    }

    @OnClick({R.id.ivBack, R.id.btnJoin, R.id.etCity, R.id.tvEditImage, R.id.liAddImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.etCity:
                showPopupCountries();
                break;
            case R.id.btnJoin:
                setUpValidation();
                break;
            case R.id.tvEditImage:
                selectImage("image");
                break;
            case R.id.liAddImage:
//                selectImage("id");
                showPopUpCamera();
                break;
        }
    }


    private void setUpValidation() {
        if (etName.getText().toString().isEmpty()) {
            etName.setError(getString(R.string.empty));
        } else if (checkName()) {
            Toast.makeText(this, getString(R.string.nameMustNotHaveNumber), Toast.LENGTH_SHORT).show();
        } else if (etName.getText().toString().length() < 3) {
            Toast.makeText(this, getString(R.string.nameMustBeGreaterThanThreeCha), Toast.LENGTH_SHORT).show();
        } else if (etName.getText().toString().length() > 50) {
            Toast.makeText(this, getString(R.string.nameMustBeGreaterThanlessCha), Toast.LENGTH_SHORT).show();
        } else if (etName.getText().toString().startsWith(" ")) {
            Toast.makeText(this, getString(R.string.nameMustNotStartWithSpace), Toast.LENGTH_SHORT).show();
        } else if (etNationalId.getText().toString().isEmpty()) {
            etNationalId.setError(getString(R.string.empty));
        } else if (etNationalId.getText().toString().length() < 9) {
            Toast.makeText(this, getString(R.string.enterValidIdentityId), Toast.LENGTH_SHORT).show();
        } else if (etNationalId.getText().toString().length() > 25) {
            Toast.makeText(this, getString(R.string.enterValidIdentityId), Toast.LENGTH_SHORT).show();
        } else if (etCity.getText().toString().isEmpty()) {
            etCity.setError(getString(R.string.empty));
        } else if (idFile == null) {
            Toast.makeText(this, getString(R.string.pleaseAddImage), Toast.LENGTH_SHORT).show();
        } else {
            presenter.joinAsTransporter(this, storage.getId(), etName.getText().toString() + " " + etLastName.getText().toString(), "" + fromCityID, etNationalId.getText().toString(), idFile);
        }
    }

    private boolean checkName() {
        boolean hasNum = false;
        if (etName.getText().toString().contains("0") || etName.getText().toString().contains("1") || etName.getText().toString().contains("2") || etName.getText().toString().contains("3") || etName.getText().toString().contains("4") || etName.getText().toString().contains("5") || etName.getText().toString().contains("6") || etName.getText().toString().contains("7") || etName.getText().toString().contains("8") || etName.getText().toString().contains("9")) {
            hasNum = true;
        } else {
            hasNum = false;
        }
        return hasNum;
    }


    private void selectImage(String type) {
        imageType = type;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinAsTransporter.this);
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
                    selectImage("id");
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

            idFile = new File(imageurl);

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
        if (imageType.equals("image")) {
            imageFile = new File(selectedImagePath);
        } else {
            idFile = new File(selectedImagePath);
        }


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
            if (imageType.equals("image")) {
                ivProfile.setImageBitmap(finalImage);
            } else {
                ivShipmentImage.setImageBitmap(finalImage);
            }
        } else {
            if (imageType.equals("image")) {
                ivProfile.setImageBitmap(bm);
            } else {
                ivShipmentImage.setImageBitmap(bm);
            }

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
        rvCities = dialog.findViewById(R.id.rvCities);
        presenter.getCountries(this);
        dialog.show();
    }

    private void showPopupJoin() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_join_success);
        dialog.setCanceledOnTouchOutside(false);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(JoinAsTransporter.this, HomeActivity.class));
                finish();
            }
        });
        run = new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(JoinAsTransporter.this, HomeActivity.class));
                finish();
            }
        }

        ;
        handler.postDelayed(run, 3000);

        dialog.show();
    }

    @Override
    public void getCountries(final List<CountriesResponse.Return> returnList) {
        countriesList = returnList;
        countriesAdapter = new CountriesAdapter(this, returnList);
        rvCities.setAdapter(countriesAdapter);
        rvCities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        countriesAdapter.onNameClic(new CountriesAdapter.NameClick() {
            @Override
            public void onNameClick(int pos) {
                dialog.cancel();
                fromCountryID = returnList.get(pos).getCountry_id();
                countriesList.clear();
                countriesAdapter.notifyDataSetChanged();
                presenter.getCities(JoinAsTransporter.this, "" + fromCountryID);
            }
        });
    }

    @Override
    public void getCities(final List<CitiesResponse.Return> returnList) {
        if (returnList != null) {
            if (returnList.size() > 0) {

                dialog.show();
                citiesList = returnList;
                citiesAdapter = new CitiesAdapter(this, returnList);
                rvCities.setAdapter(citiesAdapter);
                rvCities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                citiesAdapter.onNameClic(new CitiesAdapter.NameClick() {
                    @Override
                    public void onNameClick(int pos) {
                        dialog.dismiss();
                        fromCityID = returnList.get(pos).getCity_id();
                        etCity.setText(returnList.get(pos).getCity_name());

                        dialog.dismiss();
                    }
                });

            }
        }
    }

    @Override
    public void isJoined(boolean isAdded) {
        if (isAdded) {
            Toast.makeText(this, getString(R.string.youJoinedAsTransporter), Toast.LENGTH_SHORT).show();
//            onBackPressed();
            showPopupJoin();
        }
    }

    @OnClick(R.id.ivShipmentImage)
    public void onViewClicked() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(JoinAsTransporter.this, HomeActivity.class));
        finish();
    }
}