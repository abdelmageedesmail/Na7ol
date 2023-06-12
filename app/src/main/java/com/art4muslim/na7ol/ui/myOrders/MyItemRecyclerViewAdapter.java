package com.art4muslim.na7ol.ui.myOrders;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.utils.OnListFragmentInteractionListener;
import com.myfatoorah.sdk.model.initiatepayment.PaymentMethod;
import com.squareup.picasso.Picasso;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.MyHolder> {

    Context context;
    ArrayList<PaymentMethod> mValues;
    ArrayList<Boolean> listSelected = new ArrayList<>();
    OnListFragmentInteractionListener listener;
    GetTransferData transferData;
    int oldPosition = -1;

    public MyItemRecyclerViewAdapter(@NonNull Context context, ArrayList<PaymentMethod> mValues, OnListFragmentInteractionListener listener) {
        this.context = context;
        this.mValues = mValues;
        this.listener = listener;
        for (int i = 0; i < mValues.size(); i++) {
            listSelected.add(false);
        }

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

//
//    public void changeSelected(int position) {
//        for (int i = 0; i < mValues.size(); i++) {
////            listSelected.get(i) = i == position
//            notifyDataSetChanged();
//        }
//
//    }

    public void setTransferData(GetTransferData transferData) {
        this.transferData = transferData;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        PaymentMethod paymentMethod = mValues.get(i);
        Picasso.with(context)
                .load(paymentMethod.getImageUrl()).into(myHolder.image);
        if (listSelected.get(i)) {
            myHolder.cbSelected.setVisibility(View.VISIBLE);
        } else {
            myHolder.cbSelected.setVisibility(View.GONE);
        }
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPosition = myHolder.getAdapterPosition();
                if (transferData != null) {
//                    listener.onListFragmentInteraction(myHolder.getAdapterPosition(), paymentMethod);
                    transferData.getPaymentId(paymentMethod, myHolder.getAdapterPosition());
                }
                notifyDataSetChanged();
            }
        });
        if (oldPosition == myHolder.getAdapterPosition()) {
            myHolder.cbSelected.setVisibility(View.VISIBLE);
        } else {
            myHolder.cbSelected.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        AppCompatImageView image;
        @BindView(R.id.cbSelected)
        ImageView cbSelected;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface GetTransferData {
        void getPaymentId(PaymentMethod paymentMethod, int pos);
    }
}
