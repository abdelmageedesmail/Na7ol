package com.art4muslim.na7ol.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.UserResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.ui.register.RegisterationActivity;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity implements EditDataView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.btnEditImage)
    Button btnEditImage;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnEditPassword)
    Button btnEditPassword;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @Inject
    ProfilePresenter presenter;
    PrefrencesStorage storage;
    private Uri imageUri;
    private static final int SELECT_FILE = 1;
    int REQUEST_CAMERA = 0;
    private String imageurl, phoneCode;
    private File imageFile;
    private Uri selectedImageUri;
    private String selectedImagePath;
    private Bitmap bm;
    private String substring, imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        phoneCode = ccp.getDefaultCountryCode();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                phoneCode = selectedCountry.getPhoneCode();
            }
        });
        fillView();
        preventCopy();
    }

    private void preventCopy() {
        etName.setLongClickable(false);
        etLastName.setLongClickable(false);
        etPassword.setLongClickable(false);

        etName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(ProfileActivity.this, getString(R.string.copyPrevented), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        etLastName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(ProfileActivity.this, getString(R.string.copyPrevented), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        etPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(ProfileActivity.this, getString(R.string.copyPreventedPassword), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        etEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(ProfileActivity.this, getString(R.string.copyPreventedPassword), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    private void fillView() {
        if (storage.getKey("name").contains(" ")) {
            String[] split = storage.getKey("name").split(" ");
            etName.setText(split[0]);
            etLastName.setText(split[1]);
        } else {
            etName.setText(storage.getKey("name"));
        }

        etEmail.setText(storage.getKey("email"));
        etPhone.setText(storage.getKey("phone"));
        if (!storage.getKey("photo").isEmpty()) {
            Picasso.with(this).load(storage.getKey("photo")).into(ivProfile);
        }
    }

    @OnClick({R.id.ivBack, R.id.btnEditImage, R.id.btnRegister, R.id.btnEditPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnEditImage:
                selectImage();
                break;
            case R.id.btnEditPassword:
                if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError(getString(R.string.empty));
                } else {
                    presenter.editPass(this, storage.getId(), etPassword.getText().toString());
                }

                break;
            case R.id.btnRegister:
                setupValidation();
                break;
        }
    }

    private void setupValidation() {
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
        } else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError(getString(R.string.empty));
        }else if (!isValidEmail(etEmail.getText().toString())) {
            Toast.makeText(this, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
        }  else if (etPhone.getText().toString().isEmpty()) {
            etPhone.setError(getString(R.string.empty));
        } else {
            if (imageFile != null) {
                presenter.editData(this, storage.getId(), etName.getText().toString() + " " + etLastName.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString(), imageFile);
            } else {
                presenter.editData(this, storage.getId(), etName.getText().toString() + " " + etLastName.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString(), null);
            }

        }
    }

    public static boolean isValidEmail(String target) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (target.matches(emailPattern))
            return true;
        else
            return false;
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

    private void selectImage() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_FILE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            }
        }
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
            Log.e("exeption", "" + e.getMessage());
        }

        if (finalImage != null) {
            ivProfile.setImageBitmap(finalImage);
        } else {
            ivProfile.setImageBitmap(bm);
        }

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    @Override
    public void getUserData(UserResponse.ReturnsEntity returns) {
        storage.storeKey("name", returns.getAdv_name());
        storage.storeKey("city", returns.getAdv_city_id());
        storage.storeKey("id", returns.getAdv_id());
        storage.storeKey("email", returns.getAdv_email());
        storage.storeKey("phone", returns.getAdv_mobile());
        storage.storeKey("photo", returns.getImageUrl());
        storage.storeKey("isTransporter", "" + returns.getAdv_driver_status());
    }

    @Override
    public void isUpdated(boolean b) {
        if (b) {
            Toast.makeText(this, getString(R.string.profileUpdatedSuccessfully), Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, ProfileActivity.class));
//            finish();
            startActivity(getIntent());
            finish();
        } else {
            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getCountries(List<CountriesResponse.Return> returns) {

    }
}