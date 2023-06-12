package com.art4muslim.na7ol.ui.myTrips;

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
import com.art4muslim.na7ol.databinding.ItemMyTripsBinding;
import com.art4muslim.na7ol.ui.addShipment.AddShipment;
import com.art4muslim.na7ol.ui.add_trip.AddTripActivity;
import com.art4muslim.na7ol.ui.joinRequests.JoinRequestsActivity;
import com.art4muslim.na7ol.ui.myOrders.MyOrderAdapter;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.ui.tripDetails.TripDetails;
import com.art4muslim.na7ol.ui.tripFragment.TripAdapter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTripsAdapter extends RecyclerView.Adapter<MyTripsAdapter.MyHolder> {
    Context context;

    private ItemMyTripsBinding binding;
    TripAdapter.ItemClick itemClick;
    List<TripsModelResponse.Returns> list;
    UpdateTrip updateTrip;

    public MyTripsAdapter(@NonNull Context context, List<TripsModelResponse.Returns> returns) {
        this.context = context;
        this.list = returns;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_trips, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    public void onItemClick(TripAdapter.ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void setUpdateTrip(UpdateTrip updateTrip) {
        this.updateTrip = updateTrip;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
//        if (list.get(i).getServ_cartype_id().equals("2")) {
//            myHolder.ivType.setImageResource(R.drawable.ic_train);
//        } else if (list.get(i).getServ_cartype_id().equals("1")) {
//            myHolder.ivType.setImageResource(R.drawable.ic_car);
//        } else {
//            myHolder.ivType.setImageResource(R.drawable.ic_plan);
//        }
        if (!list.get(i).getCartype_icon().isEmpty()) {
            Picasso.with(context).load(list.get(i).getCartype_icon()).into(myHolder.ivType);
        }
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (itemClick != null) {
//                    itemClick.onItemClick(myHolder.getAdapterPosition());
//                }

                Gson gsonObj = new Gson();
                String jsonStr = gsonObj.toJson(list.get(myHolder.getAdapterPosition()));
                Intent intent = new Intent(context, TripDetails.class);
                intent.putExtra("model", jsonStr);
                context.startActivity(intent);
            }
        });
        String[] s = list.get(i).getServ_from_time().split(" ");
        String[] s1 = list.get(i).getServ_to_time().split(" ");

        myHolder.tvFrom.setText(list.get(i).getFrom_city_name());
        myHolder.tvDate.setText(s[0]);
        myHolder.tvDateTo.setText(s1[0]);
        myHolder.tvTo.setText(list.get(i).getTo_city_name());
        myHolder.tvWeight.setText(list.get(i).getServ_weight_name() + " KG");
        myHolder.frOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, myHolder.frOptions);
                if (list.get(myHolder.getAdapterPosition()).getServ_status().equals("new")) {
                    popup.inflate(R.menu.trip_menu_pending);
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
                                    Intent intent1 = new Intent(context, AddTripActivity.class);
                                    intent1.putExtra("from", "edit");
                                    Gson gsonObj = new Gson();
                                    String jsonStr = gsonObj.toJson(list.get(myHolder.getAdapterPosition()));
                                    intent1.putExtra("model", jsonStr);
                                    context.startActivity(intent1);
                                    return true;
                                case R.id.tvPriceOffer:
                                    Intent intent = new Intent(context, JoinRequestsActivity.class);
                                    intent.putExtra("servId", "" + list.get(myHolder.getAdapterPosition()).getServ_id());
                                    context.startActivity(intent);
                                    return true;
                            }
                            return false;
                        }
                    });

                } else if (list.get(myHolder.getAdapterPosition()).getServ_status().equals("accepted")) {
                    popup.inflate(R.menu.trip_menu_active);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.tvCancel:

                                    return true;
                                case R.id.tvChat:
//                                    Intent intent = new Intent(context, ChatActivity.class);
//                                    intent.putExtra("to", list.get(myHolder.getAdapterPosition()).getAgr_to_id());
//                                    intent.putExtra("userName", list.get(myHolder.getAdapterPosition()).getTo_user_name());
//                                    intent.putExtra("userImage", list.get(myHolder.getAdapterPosition()).getTo_user_image());
//                                    intent.putExtra("fromImage", list.get(myHolder.getAdapterPosition()).getFrom_user_image());
//                                    intent.putExtra("tripId", list.get(myHolder.getAdapterPosition()).getServ_id());
//                                    context.startActivity(intent);
                                    return true;
                                case R.id.tvTerms:
//                                    Intent intent1 = new Intent(context, TermsActivity.class);
//                                    intent1.putExtra("tripId", "" + list.get(myHolder.getAdapterPosition()).getServ_id());
//                                    intent1.putExtra("driverId", "" + list.get(myHolder.getAdapterPosition()).getAgr_from_id());
//                                    context.startActivity(intent1);
                                    return true;
                            }
                            return false;
                        }
                    });
                } else if (list.get(myHolder.getAdapterPosition()).getServ_status().equals("complete")) {
                    popup.inflate(R.menu.trip_menu_done);
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
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvWeight)
        TextView tvWeight;
        @BindView(R.id.ivType)
        ImageView ivType;
        @BindView(R.id.tvTo)
        TextView tvTo;
        @BindView(R.id.tvDateTo)
        TextView tvDateTo;
        @BindView(R.id.frOptions)
        FrameLayout frOptions;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ItemClick {
        void onItemClick(int pos);
    }


    public interface UpdateTrip {
        void onCancelClick(int pos);
    }
}
