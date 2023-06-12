package com.art4muslim.na7ol.ui.addShipment;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.ui.notificationSettings.NotificationsSettings;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultiOrderTypesAdapter extends RecyclerView.Adapter<MultiOrderTypesAdapter.MyHolder> {
    Context context;
    List<OrderTypeResponse.ReturnsEntity> list;
    int oldPosition = -1;
    CarTypeClick carTypeClick;
    private static RadioButton lastChecked = null;
    String orderType;
    List<String> orderTypesId = new ArrayList<>();
    private String servTypeId;
    private String oldServId;

    public static ArrayList<String> orderTypesIds = new ArrayList<>();
    public static List<String> orderTypes = new ArrayList<>();


    public MultiOrderTypesAdapter(@NonNull Context context, List<OrderTypeResponse.ReturnsEntity> list, String orderType) {
        this.context = context;
        this.list = list;
        this.orderType = orderType;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_types, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    public void setCarTypeClick(CarTypeClick carTypeClick) {
        this.carTypeClick = carTypeClick;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        myHolder.rdType.setText(list.get(i).getType_title());

        myHolder.rdType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    servTypeId = list.get(myHolder.getAdapterPosition()).getType_id();
                    Log.e("servIDd", "" + servTypeId);
                    orderTypesIds.add(servTypeId);
                    orderTypes = removeDuplicates(orderTypesIds);
                } else {
                    for (int x = 0; x < orderTypes.size(); x++) {
                        if (list.get(myHolder.getAdapterPosition()).getType_id().equals(orderTypes.get(x))) {
                            orderTypes.remove(x);
                        }
//                        notifyDataSetChanged();
                    }
                }
            }
        });

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                oldPosition = myHolder.getAdapterPosition();
//                myHolder.rdShape.setImageResource(R.drawable.ic_rec);
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

//        if (oldPosition == myHolder.getAdapterPosition()) {
//            myHolder.rdShape.setImageResource(R.drawable.ic_radio_on_button);
//        } else {
//            myHolder.rdShape.setImageResource(R.drawable.ic_rec);
//        }
//
//        if (orderType != null) {
//            if (list.get(i).getType_id().equals(orderType)) {
//                myHolder.rdShape.setImageResource(R.drawable.ic_radio_on_button);
//                orderType = null;
//            }
//        }


        if (NotificationsSettings.arryList != null) {
            if (NotificationsSettings.arryList.size() > 0) {
                for (int x = 0; x < NotificationsSettings.arryList.size(); x++) {
                    if (NotificationsSettings.arryList.get(x).equals(list.get(myHolder.getAdapterPosition()).getType_id())) {
                        myHolder.rdType.setChecked(true);
                    } else {
                        myHolder.rdType.setChecked(false);
                    }
                }
            }
        }

        if (orderType != null) {
            String serv_order_type_id = orderType;
            if (serv_order_type_id != null) {
                Log.e("orderTypeId", "" + list.get(i).getType_id() + "..." + orderType.replace("|", ""));
                if (orderType.replace("||", "").replace("|", "").contains(list.get(i).getType_id())) {
                    myHolder.rdType.setChecked(true);
                    serv_order_type_id = null;
                }
            }
        }
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
        //        @BindView(R.id.rdShape)
//        ImageView rdShape;
//        @BindView(R.id.tvTitle)
//        TextView tvTitle;//
        @BindView(R.id.rdType)
        CheckBox rdType;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CarTypeClick {
        void onCarTypeClick(int pos);
    }
}
