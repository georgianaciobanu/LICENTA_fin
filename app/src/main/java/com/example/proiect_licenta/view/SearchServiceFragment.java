package com.example.proiect_licenta.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.view.SearchedServiceActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchServiceFragment extends Fragment {
    Button cauta;
    ListView listView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        listView = (ListView) view.findViewById(R.id.listViewService);
        cauta = (Button) view.findViewById(R.id.btn_search);
        cauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToServicesList();
            }
        });
        return view;
    }

    public void goToServicesList() {
        Intent it = new Intent(getActivity(), SearchedServiceActivity.class);
        startActivity(it);
    }
}
