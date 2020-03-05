package com.example.proiect_licenta.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.model.OnGetDataListener;
import com.example.proiect_licenta.model.PhysicalLocation;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.ServicesListAdapter;
import com.example.proiect_licenta.presenter.TrimiteCerereActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    OnGetDataListener listenerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_service);

        Intent i = getIntent();

        currentService=(Service) i.getSerializableExtra("CurrentService");
        cerere = (Button) findViewById(R.id.BTN_timiteCerere);
        proprietar = (TextView) findViewById(R.id.tw_proprietarService);
        program = (TextView) findViewById(R.id.tw_programService);
        descriere = (TextView) findViewById(R.id.tw_descriereService);
        telefon = (TextView) findViewById(R.id.tw_telefonService);
        email = (TextView) findViewById(R.id.tw_mailService);
        nume = (TextView) findViewById(R.id.tw_NumeService);

        listaServicii = (TextView) findViewById(R.id.tw_listaServicii);
        btnAdresa = (TextView) findViewById(R.id.tw_adresaService);
        final PhysicalLocation adresaMea= new PhysicalLocation();
        adresaMea.setAdresa("Bloc 37, Strada Lerești, București");
        adresaMea.setLatitudine(44.403052);
        adresaMea.setLogitudine(26.0531606);




                        if(currentService!=null){
                           setInfoService(currentService);

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
                                    goToMaps(adresaMea.getLatitudine(),
                                            adresaMea.getLogitudine(),
                                            currentService.getLoc().getLatitudine(),
                                            currentService.getLoc().getLogitudine());
                                }
                            });

                        }

                    }




    public void goToCerere() {
        Intent itReq = new Intent(getApplicationContext(), TrimiteCerereActivity.class);
        itReq.putExtra("CurrentService",currentService);
        startActivityForResult(itReq,0);



    }

    public void goToListaServicii() {
        Intent it = new Intent(this, ServicesListActivity.class);
        it.putExtra("Servicii",currentService.getSevicii());
        startActivity(it);

    }

    public void goToMaps(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {

        String uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void setInfoService(Service currentService) {

        nume.setText(currentService.getNumeService());
        descriere.setText(currentService.getDescriere());
        program.setText(currentService.getProgram());
        proprietar.setText(currentService.getProprietar());
        telefon.setText(currentService.getTelefon());
        email.setText(currentService.getEmail());
        btnAdresa.setText(currentService.getLoc().getAdresa());

    }



}
