package com.art4muslim.na7ol.ui.myOrders;

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
import android.widget.TextView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ItemOrdersBinding;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.ui.addShipment.AddShipment;
import com.art4muslim.na7ol.ui.myOrderDetails.MyOrderDetails;
import com.art4muslim.na7ol.ui.orderDetails.OrderDetailsActivity;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.ui.priceOffers.PriceOffersActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyHolder> {
    Context context;
    private ItemOrdersBinding binding;
    List<TripsModelResponse.Returns> list;
    UpdateTrip updateTrip;

    public MyOrderAdapter(@NonNull Context context, List<TripsModelResponse.Returns> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_orders, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    public void setUpdateTrip(UpdateTrip updateTrip) {
        this.updateTrip = updateTrip;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
//        if (list.get(i).getServ_cartype_id().equals("1")) {
//            myHolder.ivType.setImageResource(R.drawable.ic_train);
//        } else if (list.get(i).getServ_cartype_id().equals("2")) {
//            myHolder.ivType.setImageResource(R.drawable.ic_car);
//        } else {
//            myHolder.ivType.setImageResource(R.drawable.ic_plan);
//        }
        if (!list.get(i).getCartype_icon().isEmpty()){
            Picasso.with(context).load(list.get(i).getCartype_icon()).into(myHolder.ivType);
        }
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gsonObj = new Gson();
                String jsonStr = gsonObj.toJson(list.get(myHolder.getAdapterPosition()));
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("model", jsonStr);
                intent.putExtra("from", "myOrder");
                context.startActivity(intent);
                MyOrdersActivity.appCompatActivity.finish();
            }
        });

        myHolder.tvFrom.setText(list.get(i).getFrom_city_name());
        myHolder.tvType.setText(list.get(i).getOrder_types());
        myHolder.tvTo.setText(list.get(i).getTo_city_name());
        myHolder.tvWeight.setText(list.get(i).getServ_weight_name() + " KG");
        if (list.get(i).getImageUrl() != null) {
            if (!list.get(i).getImageUrl().isEmpty()) {
                Picasso.with(context).load(list.get(i).getImageUrl()).into(myHolder.ivImage);
            }
        }

        myHolder.frOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, myHolder.frOptions);
                if (list.get(myHolder.getAdapterPosition()).getServ_status().equals("new")) {
                    popup.inflate(R.menu.order_menu_pending);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.tvCancel:
                                    if (updateTrip != null) {
                                        updateTrip.onCancelClick(myHolder.getAdapterPosition());
                                    }
                                    return true;
                                case R.id.tvEditOrder:
                                    Intent intent1 = new Intent(context, AddShipment.class);
                                    intent1.putExtra("from", "edit");
                                    Gson gsonObj = new Gson();
                                    String jsonStr = gsonObj.toJson(list.get(myHolder.getAdapterPosition()));
                                    intent1.putExtra("model", jsonStr);
                                    context.startActivity(intent1);
                                    return true;
                                case R.id.tvPriceOffer:
                                    Intent intent = new Intent(context, PriceOffersActivity.class);
                                    intent.putExtra("servId", "" + list.get(myHolder.getAdapterPosition()).getServ_id());
                                    context.startActivity(intent);
                                    return true;
                            }
                            return false;
                        }
                    });

                } else if (list.get(myHolder.getAdapterPosition()).getServ_status().equals("accepted")) {
                    popup.inflate(R.menu.order_menu_active);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.tvCancel:

                                    return true;
                                case R.id.tvChat:

                                    return true;
                                case R.id.tvTerms:

                                    return true;
                            }
                            return false;
                        }
                    });
                } else if (list.get(myHolder.getAdapterPosition()).getServ_status().equals("complete")) {
                    popup.inflate(R.menu.order_menu_done);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.tvRate:
                                    return true;

                            }
                            return false;
                        }
                    });
                }

                popup.show();
            }
        });
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
        @BindView(R.id.frOptions)
        FrameLayout frOptions;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface UpdateTrip {
        void onCancelClick(int pos);
    }
}
