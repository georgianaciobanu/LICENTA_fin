package com.example.proiect_licenta.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.ServiceDataModel;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.presenter.RequestsAdapter;
import com.example.proiect_licenta.presenter.ServiceAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class RequestsFragment extends Fragment {
    ArrayList<Request> requests;
    ListView listView;
    private static RequestsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cereri, container, false);

        Date currentTime = Calendar.getInstance().getTime();
        requests = new ArrayList<>();
        listView = (ListView)view.findViewById(R.id.list_requests);

        Serviciu serv1= new Serviciu();
        serv1.setDenumire("Reparatii aragaz");
        Serviciu serv2= new Serviciu();
        serv2.setDenumire("Schimb display iphone");

        User user= new User();
        user.setEmail("clientul1@email.ro");
        user.setUsername("Clientul 1");
        Request req = new Request();
        req.setClient(user);
        req.setStatus("confirmata");
        req.setDetalii("detaliile cererii 1");
        req.setDataProgramare(new Date());
        ArrayList<Serviciu> servicii1= new ArrayList<>();
        servicii1.add(serv1);
        req.setServicii(servicii1);

        req.setDataTrimiterii(currentTime);
        requests.add(req);

        User user2= new User();
        user2.setEmail("clientu21@email.ro");
        user2.setUsername("Clientul 2");
        Request req2 = new Request();
        req2.setClient(user2);
        req2.setStatus("respinsa");
        req2.setDetalii("detaliile cererii 2");
        req2.setDataProgramare(new Date());
        req2.setDataTrimiterii(currentTime);

        ArrayList<Serviciu> servicii2= new ArrayList<>();
        servicii2.add(serv2);
        req2.setServicii(servicii2);


        requests.add(req2);

        adapter = new RequestsAdapter(view.getContext(), requests);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itReq = new Intent(view.getContext(),RequestDetailsActivity.class);
                startActivity(itReq);
            }


        });

        return view;
    }
}