package com.art4muslim.na7ol.ui.orderDetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ActivityOrderDetailsBinding;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.ui.tripFragment.JoinRequestActivity;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.List;

import javax.inject.Inject;

public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener, OrderView {

    private Dialog dialog;
    TripsModelResponse.Returns model;
    ActivityOrderDetailsBinding binding;
    PrefrencesStorage storage;
    @Inject
    OrderPresenter presenter;
    private EditText etPrice;
    private String isTrans;
    private String price, symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
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
        binding.btnAddOffer.setOnClickListener(this);
        binding.ivShare.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        presenter.getWeights(this);
        model = new Gson().fromJson(getIntent().getStringExtra("model"), new TypeToken<TripsModelResponse.Returns>() {
        }.getType());
        fillViews();
        presenter.getUserData(this, storage.getId());
    }

    private void fillViews() {
        binding.tvFrom.setText(model.getFrom_city_name());
        binding.tvTo.setText(model.getTo_city_name());
        binding.tvType.setText(model.getOrder_serv_types());
        binding.tvWeight.setText(model.getServ_weight_name() + " KG");
        binding.tvDate.setText(model.getServ_time());
        binding.tvDetails.setText(model.getServ_details());
//        binding.tvAccessPoint.setText(model.);
        if (!model.getImageUrl().isEmpty()) {
            Picasso.with(this).load(model.getImageUrl()).into(binding.ivImage);
        }
        if (getIntent().getExtras().getString("from").equals("myOrder")) {
            binding.btnAddOffer.setVisibility(View.GONE);
        } else {
            binding.btnAddOffer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnAddOffer:
                if (storage.getId().equals(model.getServ_user_id())) {
                    Toast.makeText(this, getString(R.string.youCannotAddOfferTo), Toast.LENGTH_SHORT).show();
                } else {
                    showPopupOffer();
                }

                break;
            case R.id.btnSend:
                if (isTrans.equals("1")) {
                    if (etPrice.getText().toString().isEmpty()) {
                        etPrice.setError(getString(R.string.empty));
                    } else if (etPrice.getText().toString().length() > 6) {
                        Toast.makeText(this, getString(R.string.priceMustBeLessThanFourDigit), Toast.LENGTH_SHORT).show();
                    } else {
                        if (Double.parseDouble(etPrice.getText().toString()) < Double.parseDouble(price)) {
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
                            dialog.dismiss();
                            presenter.addOffer(this, storage.getId(), model.getServ_user_id(), "" + model.getServ_id(), model.getServWeight(), etPrice.getText().toString());
                        }
                    }


                } else {
                    Toast.makeText(this, getString(R.string.youMustJoinAsCarrier), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ivClose:
                dialog.dismiss();
                break;
            case R.id.ivShare:
                if (!model.getImageUrl().isEmpty()) {
                    shareItem(model.getImageUrl());
                } else {
                    shareTextUrl();
                }
                break;
        }
    }


    private void shareTextUrl() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        share.putExtra(Intent.EXTRA_TEXT, getString(R.string.departure_point) + " " + model.getFrom_city_name() + "\n" + getString(R.string.arrivalPoint) + " " + model.getTo_city_name() + "\n" + getString(R.string.flightMethod) + ":" + model.getCartype_name() + "\n" + getString(R.string.downloadOurApp) + " \n https://play.google.com/store/apps/details?id=com.art4muslim.na7ol");
        startActivity(Intent.createChooser(share, "Share link!"));
    }

    public void shareItem(final String url) {
        Picasso.with(getApplicationContext()).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                Uri uri = Uri.parse(url);
                //i.setType("*/*");
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                i.putExtra(Intent.EXTRA_TEXT, getString(R.string.departure_point) + " " + model.getFrom_city_name() + "\n" + getString(R.string.arrivalPoint) + " " + model.getTo_city_name() + "\n" + getString(R.string.flightMethod) + ":" + model.getCartype_name() + "\n" + getString(R.string.downloadOurApp) + " \n https://play.google.com/store/apps/details?id=com.art4muslim.na7ol");
                i.putExtra(Intent.EXTRA_TITLE, getString(R.string.app_name));
                startActivity(Intent.createChooser(i, "Share via"));
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

    private void showMessage() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_success);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        tvMessage.setText(getString(R.string.joinedSuccessToOrder));
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(OrderDetailsActivity.this, MyTrips.class);
                intent.putExtra("type", "myTrips");
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }


    private void showPopupOffer() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_offer);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        etPrice = dialog.findViewById(R.id.etPrice);
        Button btnSend = dialog.findViewById(R.id.btnSend);
//        CountryCurrencyButton button = dialog.findViewById(R.id.countryCurrency);
        btnSend.setOnClickListener(this);
        ivClose.setOnClickListener(this);
//        button.setOnClickListener(new CountryCurrencyPickerListener() {
//            @Override
//            public void onSelectCountry(Country country) {
//                if (country.getCurrency() == null) {
//                    Toast.makeText(OrderDetailsActivity.this,
//                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
//                            , Toast.LENGTH_SHORT).show();
//                } else {
//                    symbol = country.getCurrency().getSymbol();
//                    Toast.makeText(OrderDetailsActivity.this,
//                            symbol
//                            , Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onSelectCurrency(Currency currency) {
//
//            }
//        });
        dialog.show();
    }

    @Override
    public void isAdded(boolean isAdded) {
        if (isAdded) {
            showMessage();
        }
//        else {
//            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void isTransporter(String adv_driver_status) {
        isTrans = adv_driver_status;
    }

    @Override
    public void getWeights(List<WeightsResponse.ReturnsEntity> returns) {
        for (int i = 0; i < returns.size(); i++) {
            if (model.getServWeight().equals(returns.get(i).getPrice_id())) {
                price = returns.get(i).getPrice_price();
            }
        }
    }


}
