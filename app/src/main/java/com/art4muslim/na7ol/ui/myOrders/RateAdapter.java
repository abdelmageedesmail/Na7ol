package com.art4muslim.na7ol.ui.myOrders;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.RateResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.MyHolder> {
    Context context;
    List<RateResponse.ReturnsEntity> list;


    public RateAdapter(@NonNull Context context, List<RateResponse.ReturnsEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rate, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.tvName.setText(list.get(i).getUser_name());
        myHolder.rating.setRating(Integer.parseInt(list.get(i).getRate_number()));
        myHolder.tvComment.setText(list.get(i).getRate_review());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.rating)
        RatingBar rating;
        @BindView(R.id.tvComment)
        TextView tvComment;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
