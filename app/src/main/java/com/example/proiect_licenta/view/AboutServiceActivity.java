package com.example.proiect_licenta.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.model.PhysicalLocation;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.presenter.TrimiteCerereActivity;

import java.util.ArrayList;

public class AboutServiceActivity extends AppCompatActivity {
    Button cerere;
    TextView listaServicii;
    TextView btnAdresa;
    TextView proprietar;
    TextView program;
    TextView telefon;
    TextView email;
    TextView descriere;
    TextView nume;
    Service currentService;
    ArrayList<Serviciu> servicii= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_service);
        cerere = (Button) findViewById(R.id.BTN_timiteCerere);
        proprietar = (TextView) findViewById(R.id.tw_proprietarService);
        program = (TextView) findViewById(R.id.tw_programService);
        descriere = (TextView) findViewById(R.id.tw_descriereService);
        telefon = (TextView) findViewById(R.id.tw_telefonService);
        email = (TextView) findViewById(R.id.tw_mailService);
        nume = (TextView) findViewById(R.id.tw_NumeService);

        listaServicii = (TextView) findViewById(R.id.tw_listaServicii);
        btnAdresa = (TextView) findViewById(R.id.tw_adresaService);

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


         currentService = new Service();

        currentService.setDescriere("Service GSM.info promoveaza servicii si solutii de intretinere, reparatie si reconditionare, pentru dizpozitivele smartphone, telefoane GSM, tablete GPS-uri si alte echipamente mobile.");
        currentService.setEmail("servicegsm@gmil.com");
        currentService.setNumeService("Service GSM.info");
        currentService.setPass("passgsm1234");
        currentService.setProprietar("Vasile Mircea");
        currentService.setProgram("luni-vineri: 10-18");
        currentService.setTelefon("0723.50.30.50");
        currentService.setUsername("servicegsm");
        currentService.setSevicii(servicii);

        final PhysicalLocation serviceLocation = new PhysicalLocation();

        serviceLocation.setAdresa("Bulevardul Regina Elisabeta Nr.35, Ap.6, Et.1, Interfon 006, Sector 5, Bucuresti");
        serviceLocation.setLatitudine(44.434744);
        serviceLocation.setLogitudine(26.0954323);

        final PhysicalLocation cientLocation = new PhysicalLocation();

        cientLocation.setAdresa("currentLocation");
        cientLocation.setLatitudine(44.403052);
        cientLocation.setLogitudine(26.0531606);

        setInfoService(currentService, serviceLocation);

        cerere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCerere();
            }
        });

        listaServicii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToListaServicii();
            }
        });
        btnAdresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // goToMaps(22.304634,73.161070,23.022242,72.548844);
                goToMaps(cientLocation.getLatitudine(),
                        cientLocation.getLogitudine(),
                        serviceLocation.getLatitudine(),
                        serviceLocation.getLogitudine());
            }
        });
        ArrayList<String> prod =new ArrayList<>();
        prod.add("MASINA");
        prod.add("ELECTROCASNICE");
        currentService.setLoc(serviceLocation);
        currentService.setProduse(prod);

    }

    public void goToCerere() {
        Intent itReq = new Intent(getApplicationContext(), TrimiteCerereActivity.class);
        itReq.putExtra("CurrentService",currentService);
        startActivity(itReq);



    }

    public void goToListaServicii() {
        Intent it = new Intent(this, ServicesListActivity.class);
        startActivity(it);

    }

    public void goToMaps(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {

        String uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void setInfoService(Service currentService, PhysicalLocation servLoc) {

        nume.setText(currentService.getNumeService());
        descriere.setText(currentService.getDescriere());
        program.setText(currentService.getProgram());
        proprietar.setText(currentService.getProprietar());
        telefon.setText(currentService.getTelefon());
        email.setText(currentService.getEmail());
        btnAdresa.setText(servLoc.getAdresa());

    }
}
