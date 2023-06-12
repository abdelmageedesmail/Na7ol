package com.art4muslim.na7ol.ui.chooseTripType;

import android.app.Dialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ActivityChooseAddingTypeBinding;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.addShipment.AddShipment;
import com.art4muslim.na7ol.ui.add_trip.AddTripActivity;
import com.art4muslim.na7ol.ui.joinTransporter.JoinAsTransporter;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import okhttp3.internal.Util;

public class ChooseAddingType extends AppCompatActivity implements View.OnClickListener, UserView {

    private ActivityChooseAddingTypeBinding binding;
    private String type;
    private Dialog dialog;
    private String carType;
    PrefrencesStorage storage;
    @Inject
    UserPresenter presenter;
    private String isTrans;
    private String carTypeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_adding_type);
        storage = new PrefrencesStorage(this);
        binding.ivCar.setOnClickListener(this);
        binding.ivPlane.setOnClickListener(this);
        binding.ivTrain.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        presenter.setView(this);
        presenter.getCarTypes(this);
        PrefrencesStorage storage = new PrefrencesStorage(this);
        presenter.getUserData(this, storage.getId());
//        if (Utils.getLang(this).equals("en")) {
//            binding.ivPlane.setRotation(270);
//            binding.ivCar.setRotation(270);
//            binding.ivTrain.setRotation(270);
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPlane:
                type = "plane";
                carType = "3";
                if (getIntent().getExtras().getString("type").equals("trip")) {
                    if (isTrans.equals("1")) {
                        Intent intent = new Intent(this, AddTripActivity.class);
                        intent.putExtra("from", "add");
                        intent.putExtra("carType", carType);
                        startActivity(intent);
                    } else {
                        showPopupDialog();
                    }

                } else {
                    Intent intent = new Intent(this, AddShipment.class);
                    intent.putExtra("from", "add");
                    intent.putExtra("carType", carType);
                    startActivity(intent);
                }


                break;
            case R.id.ivCar:
                type = "car";
                carType = "2";
                if (getIntent().getExtras().getString("type").equals("trip")) {
                    if (isTrans.equals("1")) {
                        Intent intent = new Intent(this, AddTripActivity.class);
                        intent.putExtra("from", "add");
                        intent.putExtra("carType", carType);
                        startActivity(intent);
                    } else {
                        showPopupDialog();
                    }
                } else {
                    Intent intent = new Intent(this, AddShipment.class);
                    intent.putExtra("from", "add");
                    intent.putExtra("carType", carType);
                    startActivity(intent);

                }
                break;
            case R.id.ivTrain:
                type = "train";
                carType = "1";

                if (getIntent().getExtras().getString("type").equals("trip")) {
                    if (isTrans.equals("1")) {
                        Intent intent = new Intent(this, AddTripActivity.class);
                        intent.putExtra("from", "add");
                        intent.putExtra("carType", carType);
                        startActivity(intent);
                    } else {
                        showPopupDialog();
                    }
                } else {
                    Intent intent = new Intent(this, AddShipment.class);
                    intent.putExtra("from", "add");
                    intent.putExtra("carType", carType);
                    startActivity(intent);
                }

                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnComplete:
                startActivity(new Intent(this, JoinAsTransporter.class));
                break;
            case R.id.ivClose:
                onBackPressed();
                break;
        }
    }

    private void showPopupDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_complete_data);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        Button btnComplete = dialog.findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        dialog.show();
    }

    @Override
    public void isTransporter(String isTransporter) {
        isTrans = isTransporter;
//        Toast.makeText(this, isTrans, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCarTypes(final List<CarTypeResponse.ReturnsEntity> returns) {
        CarTypesAdapter carTypesAdapter = new CarTypesAdapter(this, returns);
        binding.rvTypes.setAdapter(carTypesAdapter);
        binding.rvTypes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        carTypesAdapter.setCarTypeClick(new CarTypesAdapter.CarTypeClick() {
            @Override
            public void onCarTypeClick(int pos) {
                carType = returns.get(pos).getCartype_id();
                carTypeName = returns.get(pos).getCartype_name();
                if (getIntent().getExtras().getString("type").equals("trip")) {
                    if (isTrans.equals("1")) {
                        Intent intent = new Intent(ChooseAddingType.this, AddTripActivity.class);
                        intent.putExtra("from", "add");
                        intent.putExtra("carType", carType);
                        intent.putExtra("carTypeName", carTypeName);
                        startActivity(intent);
                    } else {
                        showPopupDialog();
                    }
                } else {
                    Intent intent = new Intent(ChooseAddingType.this, AddShipment.class);
                    intent.putExtra("from", "add");
                    intent.putExtra("carType", carType);
                    intent.putExtra("carTypeName", carTypeName);
                    startActivity(intent);
                }
            }
        });
    }
}
