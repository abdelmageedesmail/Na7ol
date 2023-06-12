package com.art4muslim.na7ol.ui.notificationFragment;

import android.annotation.SuppressLint;
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
import com.art4muslim.na7ol.internet.model.NotificationModel;
import com.art4muslim.na7ol.ui.chat.ChatActivity;
import com.art4muslim.na7ol.ui.myOrders.MyOrdersActivity;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.priceOffers.PriceOffersActivity;
import com.art4muslim.na7ol.utils.PrefrencesStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;

    List<NotificationModel.ReturnsEntity> arrayList;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    PrefrencesStorage storage;
    NotificationClick notificationClick;


    public NotificationsAdapter(@NonNull Context context, List<NotificationModel.ReturnsEntity> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        storage = new PrefrencesStorage(context);
    }

    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
        //  return position;
    }


    public void setNotificationClick(NotificationClick notificationClick) {
        this.notificationClick = notificationClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_notifications, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;

    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder myHolder, @SuppressLint("RecyclerView") final int i) {
        if (arrayList.get(i).getNotify_read().equals("0")) {
            ((MyHolder) myHolder).ivRead.setVisibility(View.VISIBLE);
        } else {
            ((MyHolder) myHolder).ivRead.setVisibility(View.GONE);
        }
        ((MyHolder) myHolder).tvDate.setText(arrayList.get(i).getCreated_since());
        ((MyHolder) myHolder).tvContent.setText(arrayList.get(i).getNotify_text());
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationClick != null) {
                    notificationClick.setOnNotificationClick(myHolder.getAdapterPosition());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (arrayList != null)
            return arrayList.size();
        else
            return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.ivRead)
        ImageView ivRead;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface NotificationClick {
        void setOnNotificationClick(int pos);
    }

}
