package com.example.proiect_licenta.view;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Request;
import com.example.proiect_licenta.model.Service;
import com.example.proiect_licenta.model.Serviciu;
import com.example.proiect_licenta.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RequestDetailsActivity extends AppCompatActivity {

    ListView listView_servicii;
    Button btn_Resping;
    TextView tw_pretTotalCalc;
    TextView tw_dataProgramarii;
    TextView tw_status;
    TextView tw_clientBD;
    TextView tw_detalii;
    TextView btn_Confirma;

    String mTitle[] = {"Electrocasnice serviciu ", "Telefon/Tableta serviciu", "Masina serviciu", "PC/Laprop serviciu"};
    Float mPrice[] = {21f, 56f, 80f, 70.6f};
    int images[] = {R.drawable.electrocasnice, R.drawable.telefon, R.drawable.masina, R.drawable.pc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_cerere);

        Date currentTime = Calendar.getInstance().getTime();

        listView_servicii=(ListView)findViewById(R.id.listView_servicii);
        btn_Resping=(Button)findViewById(R.id.btn_Resping);
        btn_Confirma=(Button)findViewById(R.id.btn_Confirma);
        tw_pretTotalCalc=(TextView)findViewById(R.id.tw_pretTotalCalc);
        tw_dataProgramarii=(TextView)findViewById(R.id.tw_dataProgramarii);
        tw_status=(TextView)findViewById(R.id.tw_status);
        tw_clientBD=(TextView)findViewById(R.id.tw_clientBD);
        tw_detalii=(TextView)findViewById(R.id.tw_detalii);

        MyAdapter adapterServ = new MyAdapter(getApplicationContext(), mTitle, mPrice, images);
        listView_servicii.setAdapter(adapterServ);
        User user= new User();
        user.setUsername("Clientul servicelui");
        Serviciu serv2= new Serviciu();
        serv2.setDenumire("Schimb display iphone");
        Request req= new Request();
        req.setDataProgramare(currentTime);
        req.setDetalii("detalii acestei cereri sunt aici");
        req.setStatus("trimisa spre validare");
        req.setClient(user);

        ArrayList<Serviciu> servicii= new ArrayList<>();
        servicii.add(serv2);
        req.setServicii(servicii);



        tw_dataProgramarii.setText(req.getDataProgramare().toString());
        tw_status.setText(req.getStatus());
        tw_clientBD.setText(req.getClient().getUsername());
        tw_detalii.setText(req.getDetalii());

        btn_Resping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tw_status.setText("respinsa");
            }
        });

        btn_Confirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tw_status.setText("confirmata");
            }
        });
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
