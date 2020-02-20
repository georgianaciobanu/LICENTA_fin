package com.example.proiect_licenta.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;
import com.example.proiect_licenta.presenter.BookingAdapter;
import com.example.proiect_licenta.presenter.RequestsAdapter;
import com.example.proiect_licenta.view.ServicesListActivity.MyAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class BookingFragment extends Fragment {

    ArrayList<Request> bookingList;
    ListView listView;
    private  BookingAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programari, container, false);


        Date currentTime = Calendar.getInstance().getTime();
        bookingList = new ArrayList<>();
        listView = (ListView)view.findViewById(R.id.list_booking);

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
        ArrayList<Serviciu> servicii=new ArrayList<>();
        servicii.add(serv1);
        servicii.add(serv2);
        req.setServicii(servicii);
        req.setDataTrimiterii(currentTime);
        bookingList.add(req);

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

        bookingList.add(req2);

        adapter = new BookingAdapter(view.getContext(), bookingList);
        listView.setAdapter(adapter);

        return view;
    }


}