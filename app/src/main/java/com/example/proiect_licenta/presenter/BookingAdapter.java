package com.example.proiect_licenta.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.view.ServicesListActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BookingAdapter extends ArrayAdapter<Request> {

    String mTitle[] = {"Electrocasnice serviciu ", "Telefon/Tableta serviciu", "Masina serviciu", "PC/Laprop serviciu"};
    Float mPrice[] = {21f, 56f, 80f, 70.6f};
    int images[] = {R.drawable.electrocasnice, R.drawable.telefon, R.drawable.masina, R.drawable.pc};

    public BookingAdapter(Context context, ArrayList<Request> bookingList) {
        super(context, 0, bookingList);
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
                    R.layout.booking_item_list, parent, false
            );
        }



       final TextView tw_status = convertView.findViewById(R.id.tw_status);
        TextView tw_dataProgramarii = convertView.findViewById(R.id.tw_dataProgramarii);
        TextView tw_clientBD = convertView.findViewById(R.id.tw_clientBD);
        TextView tw_detalii = convertView.findViewById(R.id.tw_detalii);
        Button btn_anulare=convertView.findViewById(R.id.btn_anulareProg);
        ListView listView_servicii=convertView.findViewById(R.id.listView_servicii);


        Request currentItem = getItem(position);



        MyAdapter adapterServ = new MyAdapter(getContext(), mTitle, mPrice, images);


        if (currentItem != null) {


            listView_servicii.setAdapter(adapterServ);
            tw_status.setText(currentItem.getStatus());
            tw_dataProgramarii.setText(currentItem.getDataProgramare().toString());
            tw_clientBD.setText(currentItem.getClient().getUsername());
            tw_detalii.setText(currentItem.getDetalii());
        }
        btn_anulare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tw_status.setText("Anulata");
            }
        });
        return convertView;
    }

    public class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        Float mPrice[];
        int rImgs[];

        MyAdapter(Context c, String title[], Float mPrice[], int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.mPrice = mPrice;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(mPrice[position].toString());
            return row;
        }
    }

}
