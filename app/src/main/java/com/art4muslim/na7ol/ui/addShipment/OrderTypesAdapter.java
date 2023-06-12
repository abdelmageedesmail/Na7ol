package com.art4muslim.na7ol.ui.addShipment;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderTypesAdapter extends RecyclerView.Adapter<OrderTypesAdapter.MyHolder> {
    Context context;
    List<OrderTypeResponse.ReturnsEntity> list;
    int oldPosition = -1;
    CarTypeClick carTypeClick;
    private static RadioButton lastChecked = null;
    String orderType;
    List<String> orderTypesId = new ArrayList<>();
    private String servTypeId;
    private String oldServId;
    public static List<String> orderTypes = new ArrayList<>();


    public OrderTypesAdapter(@NonNull Context context, List<OrderTypeResponse.ReturnsEntity> list, String orderType) {
        this.context = context;
        this.list = list;
        this.orderType = orderType;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_types_adding, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    public void setCarTypeClick(CarTypeClick carTypeClick) {
        this.carTypeClick = carTypeClick;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        myHolder.tvTitle.setText(list.get(i).getType_title());
//        myHolder.rdType.setText(list.get(i).getType_title());
//
//
//        myHolder.rdType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    servTypeId = list.get(myHolder.getAdapterPosition()).getType_id();
//                    Log.e("servIDd", "" + servTypeId);
//                    orderTypes.add(servTypeId);
//
//                }
//            }
//        });

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPosition = myHolder.getAdapterPosition();
                myHolder.rdShape.setImageResource(R.drawable.ic_rec);
                if (carTypeClick != null) {
                    carTypeClick.onCarTypeClick(myHolder.getAdapterPosition());
                }
                notifyDataSetChanged();
            }
        });

//        if (i == 0 && myHolder.rdType.isChecked()) {
//            lastChecked = myHolder.rdType;
//            oldPosition = 0;
//        }
//        myHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton checked_rb = (RadioButton) group.findViewById(checkedId);
//                if (lastChecked != null) {
//                    lastChecked.setChecked(false);
//                }
//                //store the clicked radiobutton
//                lastChecked = checked_rb;
//                if (carTypeClick != null) {
//                    carTypeClick.onCarTypeClick(myHolder.getAdapterPosition());
//                }
//            }
//        });

//        myHolder.rdType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RadioButton cb = (RadioButton) view;
//                int clickedPos = myHolder.getAdapterPosition();
//
//                if (cb.isChecked()) {
//                    if (lastChecked != null) {
//                        lastChecked.setChecked(false);
//                    }
//
//                    lastChecked = cb;
//                    oldPosition = clickedPos;
//                } else
//                    lastChecked = null;
//
//                if (carTypeClick != null) {
//                    carTypeClick.onCarTypeClick(myHolder.getAdapterPosition());
//                }
//            }
//        });

        if (oldPosition == myHolder.getAdapterPosition()) {
            myHolder.rdShape.setImageResource(R.drawable.ic_radio_on_button);
        } else {
            myHolder.rdShape.setImageResource(R.drawable.ic_rec);
        }

        if (orderType != null) {
            if (list.get(i).getType_id().equals(orderType)) {
                myHolder.rdShape.setImageResource(R.drawable.ic_radio_on_button);
                orderType = null;
            }
        }


//        if (orderType!= null) {
//            String serv_order_type_id = AddShipment.model.getServ_order_type_id();
//            if (serv_order_type_id != null) {
//                Log.e("orderTypeId", "" + list.get(i).getType_id() + "..." + AddShipment.model.getServ_order_type_id().replace("|", ""));
//                if (list.get(i).getType_id().equals(AddShipment.model.getServ_order_type_id().replace("|", ""))) {
//                    myHolder.rdShape.setImageResource(R.drawable.ic_radio_on_button);
//                    serv_order_type_id = null;
//                }
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rdShape)
        ImageView rdShape;
        @BindView(R.id.tvTitle)
        TextView tvTitle;//
//        @BindView(R.id.rdType)
//        CheckBox rdType;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CarTypeClick {
        void onCarTypeClick(int pos);
    }
}
