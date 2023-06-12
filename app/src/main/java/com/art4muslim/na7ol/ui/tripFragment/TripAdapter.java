package com.art4muslim.na7ol.ui.tripFragment;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ItemTripsBinding;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyHolder> {
    Context context;
    String type;
    private ItemTripsBinding binding;
    ItemClick itemClick;
    boolean isFiltered;
    List<TripsModelResponse.Returns> list;

    public TripAdapter(@NonNull Context context, List<TripsModelResponse.Returns> returns, String type) {
        this.context = context;
        this.list = returns;
        this.type = type;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_trips, viewGroup, false);
        MyHolder myHolder = new MyHolder(binding);
        return myHolder;
    }

    public void onItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
//        if (type == 1) {
//            myHolder.binding.ivType.setImageResource(R.drawable.ic_train);
//        } else if (type == 2) {
//            myHolder.binding.ivType.setImageResource(R.drawable.ic_car);
//        } else {
//            myHolder.binding.ivType.setImageResource(R.drawable.ic_plan);
//        }
        if (!type.isEmpty()) {
            Picasso.with(context).load(type).into(binding.ivType);
        }

        myHolder.binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onItemClick(myHolder.getAdapterPosition());
                }
            }
        });
        String[] s = list.get(i).getServ_from_time().split(" ");
        String[] s1 = list.get(i).getServ_to_time().split(" ");

        myHolder.binding.tvFrom.setText(list.get(i).getFrom_city_name());
        myHolder.binding.tvDate.setText(s[0]);
        myHolder.binding.tvDateTo.setText(s1[0]);
        myHolder.binding.tvTo.setText(list.get(i).getTo_city_name());
        myHolder.binding.tvWeight.setText(list.get(i).getServ_weight_name() + " KG");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ItemTripsBinding binding;

        public MyHolder(@NonNull ItemTripsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ItemClick {
        void onItemClick(int pos);
    }
}
