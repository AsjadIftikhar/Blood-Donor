package com.example.blooddonors;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView search_icon;
    private ImageView back;
    private ImageView call;
    private EditText search_text;
    private TextView app_name;
    private FloatingActionButton floatingActionButton;

    public static DonorDbInterface donorDbInterface;
    public static ArrayList<Donor> donors;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        back = findViewById(R.id.back);
        call = findViewById(R.id.Call);
        search_icon = findViewById(R.id.search_icon);
        search_text = findViewById(R.id.search_text);
        app_name = findViewById(R.id.app_name);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddForm.class);
                startActivity(intent);
            }
        });
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text.setVisibility(View.VISIBLE);
                app_name.setVisibility(View.INVISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text.setVisibility(View.INVISIBLE);
                app_name.setVisibility(View.VISIBLE);
                search_text.getText().clear();
            }
        });

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString());
            }
        });
        Donor d = (Donor) getIntent().getSerializableExtra("donor");
        if (d != null) {
            Donor.save(d, donorDbInterface);
        }
//        donorDbInterface = new DonorDbSqlite(this);

        donorDbInterface = new DonorDbFireBase(new DonorDbFireBase.DataObserver() {
            @Override
            public void update() {
                onResume();
            }
        });

//        donors = Donor.load(donorDbInterface);
//        populateList();


        recyclerView = findViewById(R.id.recycleview);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CustomAdapter(donors, this);
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onResume(){
        super.onResume();

        donors = Donor.load(donorDbInterface);

        recyclerView = findViewById(R.id.recycleview);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CustomAdapter(donors, this);
        recyclerView.setAdapter(mAdapter);

    }

    public void populateList() {

        String[] names = {"Asjad", "Zain", "Tayyab", "Muaaz", "Hamza", "Farooq", "Farhaan", "Taimoor", "Ahmed", "Butt", "Faisal", "Panda"};
        String[] address = {"Lahore", "Karachi", "Faisalabad", "Chiniot", "ISB", "Peshawar", "Hydrabad", "Multan", "Dubai", "Sahiwal", "Sargodha", "Quetta", "Gawadar"};
        String[] avail = {"Ready to Donate", "Can't Donate Now!"};
        String[] group = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        Donor donor;
        Boolean st = Boolean.TRUE;


        for (int i = 0; i < 20; i++) {
            donor = new Donor(names[i % 11], st, address[i % 13], avail[i % 2], "123", group[i % 8]);
            st = !st;
            Donor.save(donor, donorDbInterface);
        }

        donors = Donor.load(donorDbInterface);

    }
}
