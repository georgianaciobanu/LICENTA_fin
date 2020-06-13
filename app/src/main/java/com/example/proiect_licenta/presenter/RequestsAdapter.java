package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.ServiceDataModel;

import java.text.DateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class RequestsAdapter extends ArrayAdapter<Request> {

    int rImages[] = {R.drawable.electrocasnice, R.drawable.telefon, R.drawable.masina, R.drawable.pc};

    public RequestsAdapter(Context context, ArrayList<Request> requestsList) {
        super(context, 0, requestsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.requests_item_list, parent, false
            );
        }

        convertView.setBackgroundColor(position % 2 == 0 ? ContextCompat.getColor(getContext(),R.color.paleblue)  : ContextCompat.getColor(getContext(),R.color.teacherActivGradientStop));

        TextView tw_status = (TextView)convertView.findViewById(R.id.tw_status);
        TextView tw_numeServiciu = (TextView)convertView.findViewById(R.id.tw_numeServiciu);
        TextView tw_dataTrimiterii = (TextView)convertView.findViewById(R.id.tw_dataTrimiterii);
        TextView tw_nrServicii = (TextView)convertView.findViewById(R.id.tw_nrServicii);
        Request currentItem = getItem(position);

        if (currentItem != null) {

            tw_numeServiciu.setText(currentItem.getServicii().get(0).getDenumire());
            tw_status.setText(currentItem.getStatus());
            tw_nrServicii.setText(String.valueOf(currentItem.getServicii().size()));
            String dateToDisplay= DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(currentItem.getDataTrimiterii());

            tw_dataTrimiterii.setText(dateToDisplay);

        }

        return convertView;
    }

}
