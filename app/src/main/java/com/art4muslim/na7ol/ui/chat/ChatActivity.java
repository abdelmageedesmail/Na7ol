package com.art4muslim.na7ol.ui.chat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;


import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.ChatModel;
import com.art4muslim.na7ol.internet.model.MessagesResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.utils.GPSTracker;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements ChatView, OnMapReadyCallback {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rvChat)
    RecyclerView rvChat;
    @BindView(R.id.ivSend)
    ImageView ivSend;
    @BindView(R.id.etChat)
    EditText etChat;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @Inject
    ChatPresenter presenter;
    GPSTracker mGps;
    public static Bundle savedInstance;


    GoogleMap gMap;
    private double lat, lng;
    private Bundle mapViewBundle;


    private ChatAdapter chatAdapter;
    private StorageReference ref;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayList<ChatModel> list = new ArrayList<>();

    //    private List<ChatModelResponse.Returns> list = new ArrayList<>();
    PrefrencesStorage prefrencesStorage;
    private EditText etPrice;
    private Dialog dialog;

    private ContentValues values;
    public static Bitmap thumbnail;
    private Uri imageUri;
    private static final int SELECT_FILE = 1;
    int REQUEST_CAMERA = 0;
    private String imageurl;
    private File imageFile;
    private Uri selectedImageUri;
    private String selectedImagePath;
    private Bitmap bm;
    private String substring;
    private String imagepath;
    private File destination;
    private SimpleDateFormat dateFormatter;

    private Dialog imageDialog;
    private String chatKey;
    private String toId;
    private ArrayList<String> listData = new ArrayList<>();
    private File destFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        savedInstance = savedInstanceState;
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        mGps = new GPSTracker(this);
        presenter.setView(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        prefrencesStorage = new PrefrencesStorage(this);
        toId = getIntent().getExtras().getString("to");
        getIntentData();
        if (getIntent().getExtras().getString("isAdmin") != null) {
            if (getIntent().getExtras().getString("isAdmin").equals("1")) {
                presenter.getMessages(this, prefrencesStorage.getId(), "0");
            }
        } else {
            init();
        }

    }

    private void getIntentData() {
        tvTitle.setText(getIntent().getExtras().getString("userName"));
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

    private void showPopUpChooseImage() {
        imageDialog = new Dialog(this);
        imageDialog.setContentView(R.layout.popup_choose_image);
        ImageView ivClose = imageDialog.findViewById(R.id.ivClose);
        LinearLayout liCamera = imageDialog.findViewById(R.id.liCamera);
        LinearLayout liGallery = imageDialog.findViewById(R.id.liGallery);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.dismiss();
            }
        });

        liCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_CAMERA);
//                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePicture, REQUEST_CAMERA);
            }
        });

        liGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        imageDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
                uploadImage(selectedImageUri);
            } else {
                onCaptureImageResult(data);
//                imageUri = data.getData();
//                uploadImage(imageUri);
            }
            imageDialog.dismiss();

        }
    }

    private void onCaptureImageResult(Intent data) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            thumbnail = MediaStore.Images.Media.getBitmap(
                    getContentResolver(), imageUri);

            imageurl = getRealPathFromURI(imageUri);
            imageFile = new File(imageurl);

            Bitmap bitmapImage = BitmapFactory.decodeFile(imageurl);
            int nh = (int) (bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
            Uri imageUri = getImageUri(this, scaled);
            uploadImage(imageUri);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 50;
//            Bitmap bmpSample = BitmapFactory.decodeFile(imageUri.getPath(), options);
//
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            byte[] byteArray = out.toByteArray();
//            String s = new String(byteArray, "UTF-8");
//            Uri uri = Uri.parse(s);
//            uploadImage(uri);
            destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
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

//        if (finalImage != null) {
//            ivProfile.setImageBitmap(finalImage);
//        } else {
//            ivProfile.setImageBitmap(bm);
//        }

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    @OnClick({R.id.ivSend, R.id.ivImage, R.id.ivBack, R.id.ivMarker})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivSend:
                if (etChat.getText().toString().isEmpty()) {
                    etChat.setError(getString(R.string.empty));
                } else {
                    if (getIntent().getExtras().getString("isAdmin") != null) {
                        presenter.sendMessage(ChatActivity.this, prefrencesStorage.getId(), "0", etChat.getText().toString(), null, "");
//                    etChat.setText("");
                    } else {
                        HashMap<String, Object> map = new HashMap<>();
                        Long tsLong = System.currentTimeMillis() / 1000;
                        String ts = tsLong.toString();
                        map.put("date", "" + ts);
                        map.put("from", prefrencesStorage.getId());
                        map.put("message", etChat.getText().toString());
                        map.put("to", getIntent().getExtras().getString("to"));
                        map.put("lat", "");
                        map.put("lon", "");
                        map.put("toName", getIntent().getExtras().getString("userName"));
                        map.put("toImage", getIntent().getExtras().getString("userImage"));
                        map.put("fromImage", getIntent().getExtras().getString("fromImage"));
                        map.put("file", "");
                        map.put("fileType", "0");
                        map.put("orderId", getIntent().getExtras().getString("tripId"));
                        database.getReference("chat").child(chatKey).child("messages").child(getIntent().getExtras().getString("tripId")).push().setValue(map);
                        presenter.addChatNotifications(ChatActivity.this, prefrencesStorage.getId(), getIntent().getExtras().getString("to"), etChat.getText().toString(), getIntent().getExtras().getString("tripId"));
                        presenter.sendMessage(ChatActivity.this, prefrencesStorage.getId(), getIntent().getExtras().getString("to"), etChat.getText().toString(), null, getIntent().getExtras().getString("tripId"));

                    }
                }
//                presenter.sendMessage(ChatActivity.this, prefrencesStorage.getId(), getIntent().getExtras().getString("to"), etChat.getText().toString(), null, getIntent().getExtras().getString("tripId"));
//                presenter.sendMessage(this, prefrencesStorage.getId(), getIntent().getExtras().getString("to"), "", imageFile, "", getIntent().getExtras().getString("tripId"));
                etChat.setText("");
                break;
            case R.id.ivImage:
                showPopUpChooseImage();
                break;
            case R.id.ivMarker:
                showPopUpLocation();
                break;

        }
    }


    private void showPopUpLocation() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_set_location);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        MapView map_view = dialog.findViewById(R.id.map_view);
        map_view.onCreate(savedInstance);
        map_view.getMapAsync(this);
        map_view.onResume();
        Button btnSend = dialog.findViewById(R.id.btnSend);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                HashMap<String, Object> map = new HashMap<>();
                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                map.put("date", "" + ts);
                map.put("from", prefrencesStorage.getId());
                map.put("message", "");
                map.put("to", getIntent().getExtras().getString("to"));
                map.put("lat", "" + lat);
                map.put("lon", "" + lng);
                map.put("toName", getIntent().getExtras().getString("userName"));
                map.put("toImage", getIntent().getExtras().getString("userImage"));
                map.put("fromImage", getIntent().getExtras().getString("fromImage"));
                map.put("file", "");
                map.put("fileType", "0");
                map.put("orderId", getIntent().getExtras().getString("tripId"));
                database.getReference("chat").child(chatKey).child("messages").child(getIntent().getExtras().getString("tripId")).push().setValue(map);
                presenter.addChatNotifications(ChatActivity.this, prefrencesStorage.getId(), getIntent().getExtras().getString("to"), "sent location", getIntent().getExtras().getString("tripId"));
                presenter.sendMessage(ChatActivity.this, prefrencesStorage.getId(), getIntent().getExtras().getString("to"), "sent location", null, getIntent().getExtras().getString("tripId"));

            }
        });

        dialog.show();
    }

    private void selectLocation() {
        gMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCameraIdle() {
                //get latlng at the center by calling
                final LatLng midLatLng = gMap.getCameraPosition().target;
//                final BitmapDescriptor iconLocation = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);
                lat = midLatLng.latitude;
                lng = midLatLng.longitude;

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        LatLng ny = new LatLng(mGps.getLatitude(), mGps.getLongitude());
        gMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        gMap.clear();
        gMap.getUiSettings().setMapToolbarEnabled(false);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ny).zoom(14).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap.setMyLocationEnabled(true);
        selectLocation();
    }


    private void uploadImage(Uri imageUri) {
        if (imageUri != null) {
            final ProgressDialog progressDialog2 = new ProgressDialog(this);
            progressDialog2.setTitle(getString(R.string.loading));
            progressDialog2.setCancelable(false);
            progressDialog2.show();

            final StorageReference ref = storageReference.child(UUID.randomUUID().toString());
            ref.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        progressDialog2.dismiss();

                        Uri downloadUri = task.getResult();
                        pushingImageMessage(downloadUri.toString());
                    } else {
                        progressDialog2.dismiss();

                        Toast.makeText(ChatActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void pushingImageMessage(String fileString) {
        HashMap<String, Object> map = new HashMap<>();
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        map.put("date", "" + ts);
        map.put("from", prefrencesStorage.getId());
        map.put("message", "");
        map.put("to", getIntent().getExtras().getString("to"));
        map.put("lat", "");
        map.put("lon", "");
        map.put("toName", getIntent().getExtras().getString("userName"));
        map.put("toImage", getIntent().getExtras().getString("userImage"));
        map.put("fromImage", getIntent().getExtras().getString("fromImage"));
        map.put("file", fileString);
        map.put("fileType", "1");
        map.put("orderId", getIntent().getExtras().getString("tripId"));
        database.getReference("chat").child(chatKey).child("messages").child(getIntent().getExtras().getString("tripId")).push().setValue(map);
        presenter.sendMessage(ChatActivity.this, prefrencesStorage.getId(), getIntent().getExtras().getString("to"), getString(R.string.attachment), null, getIntent().getExtras().getString("tripId"));
//        presenter.sendMessage(this, prefrencesStorage.getId(), getIntent().getExtras().getString("to"), "", imageFile, "", getIntent().getExtras().getString("tripId"));
        presenter.addChatNotifications(ChatActivity.this, prefrencesStorage.getId(), getIntent().getExtras().getString("to"), "attachment", getIntent().getExtras().getString("tripId"));

    }


    private void init() {
        if (chatKey == null) {
            chatKey = prefrencesStorage.getId() + "_" + toId;
        }

        rvChat.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this, list);
        rvChat.setAdapter(chatAdapter);
        database.getReference().child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Object value = dataSnapshot.getValue();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String parent = childSnapshot.getKey();
                        listData.add(parent);
                    }

                    for (int i = 0; i < listData.size(); i++) {
                        if (listData.get(i).equals(prefrencesStorage.getId() + "_" + toId) || listData.get(i).equals(toId + "_" + prefrencesStorage.getId())) {
                            String[] split1 = listData.get(i).split("_");
                            String id = prefrencesStorage.getId();
                            if (split1[1].equals(id)) {
                                chatKey = toId + "_" + id;
                            } else {
                                chatKey = id + "_" + toId;
                            }
                            Log.e("allSnapparent", listData.get(i));
                        }
                    }
                    getChatInfo();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        sendChatData();
        Log.e("key", "" + chatKey);
    }


    private void getChatInfo() {
        Log.e("tripId", "" + getIntent().getExtras().getString("tripId"));
        database.getReference("chat").child(chatKey).child("messages").child(getIntent().getExtras().getString("tripId")).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                list.add(dataSnapshot.getValue(ChatModel.class));
                chatAdapter.notifyDataSetChanged();
                if (list.size() != 0) {
                    rvChat.scrollToPosition(list.size() - 1);
                } else {

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void messageSent(boolean b) {
        if (b) {
            if (getIntent().getExtras().getString("isAdmin") != null) {
                if (getIntent().getExtras().getString("isAdmin").equals("1")) {
                    list.clear();
                    if (chatAdapter != null) {
                        chatAdapter.notifyDataSetChanged();
                    }
                    presenter.getMessages(this, prefrencesStorage.getId(), "0");
                }
            }
        }

    }

    @Override
    public void getChats(List<MessagesResponse.ReturnsEntity> returns) {
        rvChat.setAdapter(new ChatAdminAdapter(this, returns));
        rvChat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


}
