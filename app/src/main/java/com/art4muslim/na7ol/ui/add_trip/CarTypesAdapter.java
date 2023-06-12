package com.art4muslim.na7ol.ui.add_trip;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.ui.addShipment.AddShipment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarTypesAdapter extends RecyclerView.Adapter<CarTypesAdapter.MyHolder> {
    Context context;
    List<CarTypeResponse.ReturnsEntity> list;
    int oldPosition = -1;
    CarTypeClick carTypeClick;
    private static RadioButton lastChecked = null;
    String carType;
    List<String> carTypesId = new ArrayList<>();

    public CarTypesAdapter(@NonNull Context context, List<CarTypeResponse.ReturnsEntity> list, String carType) {
        this.context = context;
        this.list = list;
        this.carType = carType;
//        for (int i = 0; i < list.size(); i++) {
//            carTypesId.add(list.get(i).getCartype_id());
//        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_types_adding, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    public void setCarTypeClick(CarTypeClick carTypeClick) {
        this.carTypeClick = carTypeClick;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        myHolder.tvTitle.setText(list.get(i).getCartype_name());

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPosition = myHolder.getAdapterPosition();
                myHolder.rdShape.setImageResource(R.drawable.ic_rec);
                if (carTypeClick != null) {
                    carTypeClick.onCarTypeClick(myHolder.getAdapterPosition());
                }
                notifyDataSetChanged();
            }
        });
        //        myHolder.rdType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                oldPosition = myHolder.getAdapterPosition();
//
//                notifyDataSetChanged();
//            }
//        });

//        myHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton checked_rb = (RadioButton) group.findViewById(checkedId);
//                if (lastChecked != null) {
//                    lastChecked.setChecked(false);
//                }
//                //store the clicked radiobutton
//                lastChecked = checked_rb;
//                if (carTypeClick != null) {
//                    carTypeClick.onCarTypeClick(myHolder.getAdapterPosition());
//                }
//            }
//        });

        if (oldPosition == myHolder.getAdapterPosition()) {
            myHolder.rdShape.setImageResource(R.drawable.ic_radio_on_button);
        } else {
            myHolder.rdShape.setImageResource(R.drawable.ic_rec);
        }


//        myHolder.rdType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RadioButton checked_rb = (RadioButton) group.findViewById(checkedId);
//                if (lastCheckedRB != null) {
//                    lastCheckedRB.setChecked(false);
//                }
//                //store the clicked radiobutton
//                lastCheckedRB = checked_rb;
//
//
//                notifyDataSetChanged();
//            }
//        });

//        if (AddShipment.model != null) {
//            for (int x = 0; x < carTypesId.size(); x++) {
//                if (list.get(i).getCartype_id().equals(AddShipment.model.getServ_cartype_id())) {
//                    myHolder.rdType.setChecked(true);
//                }
//            }
//        }
        if (carType != null) {
            if (list.get(i).getCartype_id().equals(carType)) {
                myHolder.rdShape.setImageResource(R.drawable.ic_radio_on_button);
                carType = null;
            }
        }


//        if (i == 0 && myHolder.rdType.isChecked()) {
//            lastChecked = myHolder.rdType;
//            oldPosition = 0;
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rdShape)
        ImageView rdShape;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CarTypeClick {
        void onCarTypeClick(int pos);
    }
}
