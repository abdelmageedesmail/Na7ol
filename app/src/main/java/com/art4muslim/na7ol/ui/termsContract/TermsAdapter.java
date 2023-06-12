package com.art4muslim.na7ol.ui.termsContract;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.databinding.ItemOrdersBinding;
import com.art4muslim.na7ol.internet.model.TermsResponseModel;
import com.art4muslim.na7ol.ui.myOrderDetails.MyOrderDetails;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.ui.priceOffers.PriceOffersActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.MyHolder> {
    Context context;

    List<TermsResponseModel.ReturnsEntity> list;
    public static List<String> terms = new ArrayList<>();
    public static ArrayList<String> typesId = new ArrayList<>();

    public TermsAdapter(@NonNull Context context, List<TermsResponseModel.ReturnsEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_terms, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, @SuppressLint("RecyclerView") final int i) {
        myHolder.chTextTerms.setText(list.get(i).getTerm_title());
        myHolder.chTextTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    typesId.add(list.get(i).getTerm_id());
                    terms = removeDuplicates(typesId);

                } else {
                    for (int x = 0; x < terms.size(); x++) {
                        if (list.get(myHolder.getAdapterPosition()).getTerm_id().equals(terms.get(x))) {
                            terms.remove(x);
                        }
//                        notifyDataSetChanged();
                    }
                }

            }
        });
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {

        // Create a new LinkedHashSet
        Set<T> set = new LinkedHashSet<>();


        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chTextTerms)
        CheckBox chTextTerms;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
