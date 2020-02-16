package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.ServiceDataModel;

import java.util.ArrayList;


public class ServiceAdapter extends ArrayAdapter<ServiceDataModel> {

    public ServiceAdapter(Context context, ArrayList<ServiceDataModel> serviceList) {
        super(context, 0, serviceList);
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
                    R.layout.searched_service_row_item, parent, false
            );
        }

        ImageView imageViewService = convertView.findViewById(R.id.item_info);
        ImageView loc = convertView.findViewById(R.id.loc_info);
        TextView textViewName = convertView.findViewById(R.id.servicename);
        TextView adresa = convertView.findViewById(R.id.adresa_bd);
        ServiceDataModel currentItem = getItem(position);

        if (currentItem != null) {

            textViewName.setText(currentItem.getName());
            adresa.setText(currentItem.getAdresa());

        }

        return convertView;
    }
}

//public class ServiceAdapter extends ArrayAdapter<ServiceDataModel> {
//
//    private ArrayList<ServiceDataModel> dataSet;
//    Context mContext;
//
//
//
//    // View lookup cache
//    private static class ViewHolder {
//        TextView txtName;
//        TextView txtAdresa;
//
//    }
//
//    public ServiceAdapter(ArrayList<ServiceDataModel> data, Context context) {
//        super(context, R.layout.searched_service_row_item, data);
//        this.dataSet = data;
//        this.mContext=context;
//
//    }
//
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        ServiceDataModel dataModel = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        ViewHolder viewHolder; // view lookup cache stored in tag
//
//        final View result;
//
//        if (convertView == null) {
//
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.searched_service_row_item, parent, false);
//            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
//            viewHolder.txtAdresa = (TextView) convertView.findViewById(R.id.et_adresa);
//
//
//            result=convertView;
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//            result=convertView;
//        }
//
//
//        viewHolder.txtName.setText(dataModel.getName());
//        viewHolder.txtAdresa.setText(dataModel.getAdresa());
//
//        // Return the completed view to render on screen
//        return convertView;
//    }
//}