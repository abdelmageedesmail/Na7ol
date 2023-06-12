package com.art4muslim.na7ol.ui.tripFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.DeliveryMethodModel;

import java.util.List;

/**
 * Created by abdelmageed on 20/09/17.
 */

public class DeliveryMethodAdapter extends BaseAdapter {

    Context c;
    List<DeliveryMethodModel.ReturnsEntity> objects;

    public DeliveryMethodAdapter(Context context, List<DeliveryMethodModel.ReturnsEntity> objects) {
        super();
        this.c = context;
        this.objects = objects;
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        DeliveryMethodModel.ReturnsEntity cur_obj = objects.get(position);
        //  LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = LayoutInflater.from(c).inflate(R.layout.custome_spinner, parent, false);
        TextView label = (TextView) row.findViewById(R.id.name);
        label.setText(cur_obj.getType_title());

        return row;
    }
}