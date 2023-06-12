package com.art4muslim.na7ol.ui.joinRequests;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JoinRequestAdapter extends RecyclerView.Adapter<JoinRequestAdapter.MyHolder> {
    Context context;
    List<AgreementsResponseModel.ReturnsEntity> list;
    PriceOfferClick priceOfferClick;


    public JoinRequestAdapter(@NonNull Context context, List<AgreementsResponseModel.ReturnsEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_joining_request, viewGroup, false);
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
        myHolder.rating.setRating(list.get(i).getUser_rating());
        if (!list.get(i).getImageUrl().isEmpty()) {
            Picasso.with(context).load(list.get(i).getImageUrl()).into(myHolder.ivShipmentImage);
        }
        myHolder.tvType.setText(list.get(i).getOrder_types());
        myHolder.tvMeeting.setText(list.get(i).getAgr_delivery_type_name());
        myHolder.tvDetails.setText(list.get(i).getAgr_package_details());
        myHolder.tvRecipcientName.setText(list.get(i).getAgr_receiver_name());
        myHolder.tvPriceOffer.setText(list.get(i).getAgr_cost());
        myHolder.tvPhone.setText(list.get(i).getAgr_receiver_mobile());
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (priceOfferClick != null) {
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
        @BindView(R.id.rating)
        RatingBar rating;
        @BindView(R.id.v3)
        View v3;
        @BindView(R.id.ivShipmentImage)
        ImageView ivShipmentImage;
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.tvMeeting)
        TextView tvMeeting;
        @BindView(R.id.tvPriceOffer)
        TextView tvPriceOffer;
        @BindView(R.id.tvDetails)
        TextView tvDetails;
        @BindView(R.id.tvRecipcientName)
        TextView tvRecipcientName;
        @BindView(R.id.tvPhone)
        TextView tvPhone;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface PriceOfferClick {
        void onAcceptOfferClick(int position);
    }
}
