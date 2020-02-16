package com.example.proiect_licenta.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiect_licenta.R;

public class ServicesListActivity extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Electrocasnice serviciu ", "Telefon/Tableta serviciu", "Masina serviciu", "PC/Laprop serviciu"};
    Float mPrice[] = {21f,56f,80f,70.6f};
    int images[] = {R.drawable.electrocasnice, R.drawable.telefon, R.drawable.masina, R.drawable.pc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicii);

        listView = findViewById(R.id.listView);

        MyAdapter adapter = new MyAdapter(this, mTitle, mPrice, images);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    Toast.makeText(ServicesListActivity.this, "21f", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText(ServicesListActivity.this, "56f", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText(ServicesListActivity.this, "80f", Toast.LENGTH_SHORT).show();
                }
                if (position ==  0) {
                    Toast.makeText(ServicesListActivity.this, "70.6f", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        Float mPrice[];
        int rImgs[];

        MyAdapter (Context c, String title[], Float mPrice[], int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.mPrice = mPrice;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
