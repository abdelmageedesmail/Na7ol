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
import com.art4muslim.na7ol.ui.chat.ChatActivity;
import com.art4muslim.na7ol.ui.myOrderDetails.MyOrderDetails;
import com.art4muslim.na7ol.ui.priceOffers.PriceOffersActivity;
import com.art4muslim.na7ol.ui.termsContract.TermsActivity;
import com.art4muslim.na7ol.ui.tripFragment.JoinRequestActivity;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAgreementAdapter extends RecyclerView.Adapter<MyAgreementAdapter.MyHolder> {
    Context context;
    private ItemOrdersBinding binding;
    List<AgreementsResponseModel.ReturnsEntity> list;
    UpdateAgreement updateAgreement;
    AddRate addRate;
    PrefrencesStorage storage;
    String from;

    public MyAgreementAdapter(@NonNull Context context, List<AgreementsResponseModel.ReturnsEntity> list, String from) {
        this.context = context;
        this.list = list;
        this.from = from;
        storage = new PrefrencesStorage(context);
    }

    public void setUpdateAgreement(UpdateAgreement updateAgreement) {
        this.updateAgreement = updateAgreement;
    }

    public void setAddRate(AddRate addRate) {
        this.addRate = addRate;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_orders, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
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
                Gson gsonObj = new Gson();
                String jsonStr = gsonObj.toJson(list.get(myHolder.getAdapterPosition()));
                Intent intent = new Intent(context, MyOrderDetails.class);
                intent.putExtra("model", jsonStr);
//                intent.putExtra("id", returns.get(myHolder.getAdapterPosition()).getPlaceId());
//                intent.putExtra("from", "storeList");
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

        if (list.get(myHolder.getAdapterPosition()).getAgr_status().equals("complete")) {
            myHolder.frOptions.setVisibility(View.GONE);
        } else {
            myHolder.frOptions.setVisibility(View.VISIBLE);
        }

        myHolder.frOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, myHolder.frOptions);
                if (list.get(myHolder.getAdapterPosition()).getAgr_status().equals("pending")) {
                    popup.inflate(R.menu.join_request_menu_pending);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.tvCancel:
                                    if (updateAgreement != null) {
                                        updateAgreement.onUpdateAgreement(myHolder.getAdapterPosition());
                                    }
                                    return true;
                                case R.id.tvEditOrder:
                                    Gson gsonObj = new Gson();
                                    String jsonStr = gsonObj.toJson(list.get(myHolder.getAdapterPosition()));
                                    Intent intent = new Intent(context, JoinRequestActivity.class);
                                    intent.putExtra("model", jsonStr);
                                    intent.putExtra("from", "edit");
                                    context.startActivity(intent);
                                    return true;
                            }
                            return false;
                        }
                    });

                } else if (list.get(myHolder.getAdapterPosition()).getAgr_status().equals("accepted")) {
                    if (from.equals("myOrders")) {
                        popup.inflate(R.menu.order_menu_active);
                    } else {
                        popup.inflate(R.menu.join_request_menu_active);
                    }


                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.tvCancel:
                                    if (updateAgreement != null) {
                                        updateAgreement.onUpdateAgreement(myHolder.getAdapterPosition());
                                    }
                                    return true;
                                case R.id.tvChat:
                                    Intent intent = new Intent(context, ChatActivity.class);
                                    if (storage.getId().equals(list.get(myHolder.getAdapterPosition()).getAgr_from_id())) {
                                        intent.putExtra("to", list.get(myHolder.getAdapterPosition()).getAgr_to_id());
                                        intent.putExtra("userName", list.get(myHolder.getAdapterPosition()).getAgr_receiver_name());
                                        intent.putExtra("userImage", list.get(myHolder.getAdapterPosition()).getTo_user_image());
                                    } else {
                                        intent.putExtra("to", list.get(myHolder.getAdapterPosition()).getAgr_from_id());
                                        intent.putExtra("userName", list.get(myHolder.getAdapterPosition()).getUser_name());
                                        intent.putExtra("userImage", list.get(myHolder.getAdapterPosition()).getFrom_user_image());
                                    }

                                    intent.putExtra("fromImage", list.get(myHolder.getAdapterPosition()).getTo_user_image());
                                    intent.putExtra("tripId", list.get(myHolder.getAdapterPosition()).getServ_id());
                                    context.startActivity(intent);
                                    return true;
                                case R.id.tvPayment:
                                    Intent intent1 = new Intent(context, TransferToAccountActivity.class);
                                    intent1.putExtra("billAmount", list.get(myHolder.getAbsoluteAdapterPosition()).getAgr_cost());
                                    intent1.putExtra("from", "orderPrice");
                                    context.startActivity(intent1);
                                    return true;
                            }
                            return false;
                        }
                    });
                }
//                else if (list.get(myHolder.getAdapterPosition()).getAgr_status().equals("complete")) {
//                    popup.inflate(R.menu.join_request_menu_done);
//                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            switch (item.getItemId()) {
//                                case R.id.tvRate:
//                                    if (addRate != null) {
//                                        addRate.onAddRate(myHolder.getAdapterPosition());
//                                    }
//                                    return true;
//
//                            }
//                            return false;
//                        }
//                    });
//                }

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

    public interface UpdateAgreement {
        void onUpdateAgreement(int pos);
    }

    public interface AddRate {
        void onAddRate(int pos);
    }
}
