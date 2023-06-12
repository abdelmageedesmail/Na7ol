package com.art4muslim.na7ol.ui.priceOffers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.ui.myOrderDetails.MyOrderDetails;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PriceOfferAdapter extends RecyclerView.Adapter<PriceOfferAdapter.MyHolder> {
    Context context;
    List<AgreementsResponseModel.ReturnsEntity> list;
    PriceOfferClick priceOfferClick;

    public PriceOfferAdapter(@NonNull Context context, List<AgreementsResponseModel.ReturnsEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_price_offers, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    public void setPriceOfferClick(PriceOfferClick priceOfferClick) {
        this.priceOfferClick = priceOfferClick;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        if (!list.get(i).getFrom_user_image().isEmpty()) {
            Picasso.with(context).load(list.get(i).getFrom_user_image()).into(myHolder.ivProfile);
        }
        myHolder.tvName.setText(list.get(i).getFrom_user_name());
        myHolder.tvPrice.setText(list.get(i).getAgr_cost() + " SAR ");
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (priceOfferClick!=null){
                    priceOfferClick.onAcceptOfferClick(myHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivProfile)
        ImageView ivProfile;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.ratingUser)
        RatingBar ratingUser;
        @BindView(R.id.tvPrice)
        TextView tvPrice;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface PriceOfferClick {
        void onAcceptOfferClick(int position);
    }
}
