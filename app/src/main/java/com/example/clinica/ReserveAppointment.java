package com.example.clinica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReserveAppointment extends AppCompatActivity {

    PeopleDatabase db = new PeopleDatabase(this);
    SlotsAdapter adapter;
    ArrayList<Timeslot> availableSlots;
    RecyclerView.LayoutManager layoutManager;
    String reservedTime, patient, doctor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_slots);
        Intent intent = getIntent();
        doctor = intent.getStringExtra("drName");
        patient = intent.getStringExtra("pEmail");
        Log.i("dr mail", String.valueOf(doctor));
        Log.i("patient", String.valueOf(patient));
        availableSlots = db.displaySlotData(doctor);
        adapter = new SlotsAdapter(availableSlots);
        layoutManager = new GridLayoutManager(this, 2);
        Button reserveButton = (Button) findViewById(R.id.reserveButton);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSlots);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SlotsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                reservedTime = availableSlots.get(position).getSlotTime();
            }
        });
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.changeSlotStatus(doctor,reservedTime);
                db.addAppointment(patient,doctor,reservedTime,"1");
                Date currentTime = Calendar.getInstance().getTime();
                System.out.println(currentTime);
            }
        });
    }
}
