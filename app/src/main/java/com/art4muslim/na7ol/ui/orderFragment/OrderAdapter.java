package com.art4muslim.na7ol.ui.orderFragment;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ItemOrdersBinding;
import com.art4muslim.na7ol.ui.orderDetails.OrderDetailsActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {
    Context context;
    String type;

    private ItemOrdersBinding binding;
    List<TripsModelResponse.Returns> list;

    public OrderAdapter(@NonNull Context context, List<TripsModelResponse.Returns> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orders, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
//        if (type == 1) {
//            myHolder.ivType.setImageResource(R.drawable.ic_train);
//        } else if (type == 2) {
//            myHolder.ivType.setImageResource(R.drawable.ic_car);
//        } else {
//            myHolder.ivType.setImageResource(R.drawable.ic_plan);
//        }

        if (!type.isEmpty()) {
            Picasso.with(context).load(type).into(myHolder.ivType);
        }
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gsonObj = new Gson();
                String jsonStr = gsonObj.toJson(list.get(myHolder.getAdapterPosition()));
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("model", jsonStr);
                intent.putExtra("from", "order");
                context.startActivity(intent);
            }
        });

        myHolder.tvFrom.setText(list.get(i).getFrom_city_name());
        myHolder.tvType.setText(list.get(i).getOrder_types());
        myHolder.tvTo.setText(list.get(i).getTo_city_name());
        myHolder.tvWeight.setText(list.get(i).getServ_weight_name() + " KG");
        if (!list.get(i).getImageUrl().isEmpty()) {
            Picasso.with(context).load(list.get(i).getImageUrl()).into(myHolder.ivImage);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.tvWeight)
        TextView tvWeight;
        @BindView(R.id.ivType)
        ImageView ivType;
        @BindView(R.id.tvTo)
        TextView tvTo;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
