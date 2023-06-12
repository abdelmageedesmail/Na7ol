package com.art4muslim.na7ol.ui.tripFragment;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarTypesAdapter extends RecyclerView.Adapter<CarTypesAdapter.MyHolder> {
    Context context;
    List<CarTypeResponse.ReturnsEntity> list;
    int oldPosition = 0;
    String type;
    CarTypeClick carTypeClick;

    public CarTypesAdapter(@NonNull Context context, List<CarTypeResponse.ReturnsEntity> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_types, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    public void setCarTypeClick(CarTypeClick carTypeClick) {
        this.carTypeClick = carTypeClick;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        myHolder.tvType.setText(list.get(i).getCartype_name());
        myHolder.liType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPosition = myHolder.getAdapterPosition();
                if (carTypeClick != null) {
                    carTypeClick.onCarTypeClick(myHolder.getAdapterPosition());
                }
                notifyDataSetChanged();
            }
        });
        if (type.equals(list.get(i).getCartype_id())) {
            oldPosition = myHolder.getAdapterPosition();
        }
        if (oldPosition == myHolder.getAdapterPosition()) {
            myHolder.tvType.setTextColor(Color.BLACK);
            myHolder.vType.setBackgroundColor(Color.BLACK);
        } else {
            myHolder.tvType.setTextColor(Color.parseColor("#919191"));
            myHolder.vType.setBackgroundColor(Color.parseColor("#919191"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.vType)
        View vType;
        @BindView(R.id.liType)
        LinearLayout liType;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CarTypeClick {
        void onCarTypeClick(int pos);
    }
}
