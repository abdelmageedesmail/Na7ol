package com.art4muslim.na7ol.ui.chooseTripType;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarTypesAdapter extends RecyclerView.Adapter<CarTypesAdapter.MyHolder> {
    Context context;
    List<CarTypeResponse.ReturnsEntity> list;
    int oldPosition = 0;
    CarTypeClick carTypeClick;

    public CarTypesAdapter(@NonNull Context context, List<CarTypeResponse.ReturnsEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_adding_type, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    public void setCarTypeClick(CarTypeClick carTypeClick) {
        this.carTypeClick = carTypeClick;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        Picasso.with(context).load(list.get(i).getImageUrl()).into(myHolder.ivType);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPosition = myHolder.getAdapterPosition();
                if (carTypeClick != null) {
                    carTypeClick.onCarTypeClick(myHolder.getAdapterPosition());
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivType)
        ImageView ivType;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CarTypeClick {
        void onCarTypeClick(int pos);
    }
}
