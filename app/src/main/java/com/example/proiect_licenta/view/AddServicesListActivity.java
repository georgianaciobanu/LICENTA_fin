package com.example.proiect_licenta.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.model.Product;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.presenter.FirebaseFunctions;
import com.example.proiect_licenta.presenter.ProductsAdaptor;
import com.example.proiect_licenta.model.ProductsItem;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Serviciu;

import java.util.ArrayList;

public class AddServicesListActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    ArrayList<ProductsItem> listaProduse;
    ProductsAdaptor mAdapter;
    Spinner spinner;
    Button completeProfile;
    Button add;
    EditText pret;
    EditText denumire;
    EditText detalii;
    String clickedProdus;
    Service currentService;
    ArrayList<Serviciu> servicii;
    int req=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services_list);

        servicii=new ArrayList<Serviciu>();
        completeProfile = (Button) findViewById(R.id.BTNprofileRegisterService4);

        if(getCallingActivity()!=null) {
            if (getCallingActivity().getClassName().equals("com.example.proiect_licenta.view.ServicesListActivity")) {
                req = 0;
                Intent i = getIntent();
                currentService = (Service) i.getSerializableExtra("Service");
                completeProfile.setVisibility(View.INVISIBLE);
            }
        }else{
            Intent i = getIntent();
            currentService = (Service) i.getSerializableExtra("Service");
        }


        initList();
        add = (Button) findViewById(R.id.BTN_adaugaService);
        pret = (EditText) findViewById(R.id.et_pretServiciu);
        denumire = (EditText) findViewById(R.id.et_denumireServiciu);
        detalii = (EditText) findViewById(R.id.et_detaliiServiciu);

        denumire.setText("denumirea unui serviciu");
        detalii.setText("detalii");
        pret.setText("23.4");

        detalii.setOnFocusChangeListener(this);
        denumire.setOnFocusChangeListener(this);
        pret.setOnFocusChangeListener(this);

        spinner = findViewById(R.id.spinnerCategorii);
        mAdapter = new ProductsAdaptor(this, listaProduse);
        spinner.setAdapter(mAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProductsItem clickedItem = (ProductsItem) parent.getItemAtPosition(position);
                clickedProdus = clickedItem.getProdus();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        completeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentService.setSevicii(servicii);
                goToLocationScreenService();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String denumireS = denumire.getText().toString();
                String pretS = pret.getText().toString();
                String detaliiS = detalii.getText().toString();
                if (denumireS.isEmpty() || pretS.isEmpty() || detaliiS.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Completarea campurilor este obligatorie", Toast.LENGTH_LONG).show();
                } else {

                    setServicesList(denumireS, pretS, detaliiS, clickedProdus);
                    pret.getText().clear();
                    denumire.getText().clear();
                    detalii.getText().clear();
                }
            }


        });
    }

    public void goToLocationScreenService() {

        Intent it = new Intent(this, SearchLocationActivity.class);
        it.putExtra("Service", currentService);
        startActivity(it);


    }

    private void initList() {
        listaProduse = new ArrayList<>();
        for(String produs :currentService.getProduse()){
            if(produs.equals("ELECTROCASNICE")) {
                listaProduse.add(new ProductsItem("ELECTROCASNICE", R.drawable.electrocasnice));
            }

            if(produs.equals("TELEFON/TABLETA")) {
                listaProduse.add(new ProductsItem("TELEFON/TABLETA", R.drawable.telefon));
            }
            if(produs.equals("MASINA")) {
                listaProduse.add(new ProductsItem("MASINA", R.drawable.masina));
            }
            if(produs.equals("PC/LAPTOP")) {
                listaProduse.add(new ProductsItem("PC/LAPTOP", R.drawable.pc));
            }
        }

    }

    public void setServicesList(String denumire, String pret, String detalii, String produs) {


        Serviciu serv = new Serviciu();
        serv.setDenumire(denumire);
        serv.setDetalii(detalii);
        serv.setPret(Double.valueOf(pret));
        serv.setProdus(produs);



            if (serv != null) {
                if (req == 0) {
                    currentService.getSevicii().add(serv);
                    FirebaseFunctions.updateServicii(currentService.getServiceId(), currentService.getSevicii());

                } else {

                    servicii.add(serv);
                }
            } else
                Toast.makeText(AddServicesListActivity.this, "Seviciu nu a fost introdus", Toast.LENGTH_LONG).show();

        }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        EditText e = (EditText) v;
        if (!hasFocus) {
            if (e.getText().toString().isEmpty()) {
                e.setBackgroundResource(R.drawable.edittext_style_error);
                e.setError("Completati toate campurile");
            } else {
                e.setError(null);
                e.setBackgroundResource(R.drawable.edittext_style);
            }
        }
    }
}
