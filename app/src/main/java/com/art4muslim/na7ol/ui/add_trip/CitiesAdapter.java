package com.art4muslim.na7ol.ui.add_trip;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.CitiesResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.MyHolder> {
    Context context;
    List<CitiesResponse.Return> returns;
    NameClick nameClick;

    public CitiesAdapter(@NonNull Context context, List<CitiesResponse.Return> returns) {
        this.context = context;
        this.returns = returns;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_spinner, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    public void onNameClic(NameClick nameClick) {
        this.nameClick = nameClick;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        myHolder.name.setText(returns.get(i).getCity_name());
        myHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameClick != null) {
                    nameClick.onNameClick(myHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return returns.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface NameClick {
        void onNameClick(int pos);
    }
}
