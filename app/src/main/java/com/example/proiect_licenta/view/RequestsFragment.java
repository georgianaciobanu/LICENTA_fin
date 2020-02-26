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
    ArrayList<Serviciu> servicii= new ArrayList<>();
    ListView listView;
    private static RequestsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cereri, container, false);

        Date currentTime = Calendar.getInstance().getTime();
        requests = new ArrayList<>();
        listView = (ListView)view.findViewById(R.id.list_requests);

        Serviciu serv= new Serviciu();
        serv.setDenumire("Reparatie display telefon");
        serv.setDetalii("samsung s9 - 3 zile");
        serv.setPret(67.5);
        serv.setProdus("TELEFON/TABLETA");

        Serviciu serv2= new Serviciu();
        serv2.setDenumire("Schimb cuva masina de spalat");
        serv2.setDetalii("marca bosh - 2 zile - deplasare la domiciliu");
        serv2.setPret(145.6);
        serv2.setProdus("ELECTROCASNICE");

        Serviciu serv3= new Serviciu();
        serv3.setDenumire("Reparatie touchscreen tableta");
        serv3.setDetalii("lenovo - 2 zile");
        serv3.setPret(44.7);
        serv3.setProdus("TELEFON/TABLETA");

        Serviciu serv4= new Serviciu();
        serv4.setDenumire("Distributie");
        serv4.setDetalii("schimb ulei, placute");
        serv4.setPret(168d);
        serv4.setProdus("MASINA");

        Serviciu serv5= new Serviciu();
        serv5.setDenumire("Montare placa video");
        serv5.setDetalii("nvidia geforce 940mx");
        serv5.setPret(98.3);
        serv5.setProdus("PC/LAPTOP");

        servicii.add(serv);
        servicii.add(serv2);
        servicii.add(serv3);
        servicii.add(serv4);
        servicii.add(serv5);

        User user= new User();
        user.setEmail("clientul1@email.ro");
        user.setUsername("Clientul 1");
        Request req = new Request();
        req.setClient(user);
        req.setStatus("confirmata");
        req.setDetalii("detaliile cererii 1");
        req.setDataProgramare(new Date());

        req.setServicii(servicii);

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


        req2.setServicii(servicii);


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