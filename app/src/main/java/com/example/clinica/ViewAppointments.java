package com.example.clinica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinica.Login;

import java.util.ArrayList;

public class ViewAppointments extends AppCompatActivity {
    PeopleDatabase db = new PeopleDatabase(this);
    RecyclerView recyclerView;
    AppointmentsAdapter appointmentsAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Appointment> appointments = new ArrayList<>();
    String value;
    Login log_in = new Login();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_appointment);

        Intent intent = getIntent();
        value = intent.getStringExtra("twiceEmail");
        Log.i("email in appointments: ", String.valueOf(value));

        appointments = db.displayPatientAppointments(String.valueOf(value));
        Log.i("array size in view app", String.valueOf(appointments.size()));

        recyclerView = findViewById(R.id.recyclerViewAppointments);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        appointmentsAdapter = new AppointmentsAdapter(appointments);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(appointmentsAdapter);
        appointmentsAdapter.setOnItemClickListener(new AppointmentsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i("item", "clicked");
            }
        });
    }
}
