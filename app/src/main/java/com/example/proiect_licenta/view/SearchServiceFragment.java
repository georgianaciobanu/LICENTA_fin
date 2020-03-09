package com.example.proiect_licenta.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.view.SearchedServiceActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchServiceFragment extends Fragment {
    Button cauta;
    ListView listView;
    ArrayList<String> produseSelectate;
    CheckBox cb_pc;
    CheckBox cb_elc;
    CheckBox cb_mas;
    CheckBox cb_tel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        listView = (ListView) view.findViewById(R.id.listViewService);
        cauta = (Button) view.findViewById(R.id.btn_search);
        cb_pc=(CheckBox)view.findViewById(R.id.Cbox_PC);
        cb_elc=(CheckBox)view.findViewById(R.id.Cbox_Electrocasnice);
        cb_mas=(CheckBox)view.findViewById(R.id.Cbox_Masina);
        cb_tel=(CheckBox)view.findViewById(R.id.Cbox_Telefon);
        produseSelectate= new ArrayList<>();

        cauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_pc.isChecked())
                    produseSelectate.add(cb_pc.getText().toString());
                if(cb_elc.isChecked())
                    produseSelectate.add(cb_elc.getText().toString());
                if(cb_mas.isChecked())
                    produseSelectate.add(cb_mas.getText().toString());
                if(cb_tel.isChecked())
                    produseSelectate.add(cb_tel.getText().toString());

                goToServicesList();
            }
        });








        return view;
    }

    //TODO: cauta in functie de produse si distanta


    public void goToServicesList() {
        Intent it = new Intent(getActivity(), SearchedServiceActivity.class);
        it.putExtra("produseSelectate",produseSelectate);
        startActivity(it);
    }
}
