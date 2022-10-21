package com.example.clinica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DisplayDoctors extends AppCompatActivity {
    PeopleDatabase db = new PeopleDatabase(this);
    ArrayList<Doctors> validDoctors = new ArrayList<>();
    DoctorsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String value, name, pMail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_doctors);
        Intent intent = getIntent();
        value = intent.getStringExtra("specialization");
        pMail = intent.getStringExtra("patientEmail");
        Log.i("value: ", String.valueOf(value));
        System.out.println(db.getSpecificDoctors(value).getCount());
        validDoctors = db.displaySpecificDoctorsData(String.valueOf(value));
        Log.i("array size", String.valueOf(validDoctors.size()));
        adapter = new DoctorsAdapter(validDoctors);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDoctors);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DoctorsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                name = validDoctors.get(position).getName();
                Log.i("doctorname", name);
                Intent intent = new Intent(getApplicationContext(), ReserveAppointment.class);
                intent.putExtra("drName",name);
                intent.putExtra("pEmail", pMail);
                startActivity(intent);
            }
        });
    }
}
