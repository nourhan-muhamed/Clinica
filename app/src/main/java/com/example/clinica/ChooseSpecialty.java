package com.example.clinica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChooseSpecialty extends AppCompatActivity {
    SpecializationsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Specialization> allSpecializations = new ArrayList<>();
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_specializations);
        Intent intent = getIntent();
        String tempEmail = intent.getStringExtra("emailAgain");
        Log.i("size", String.valueOf(initializeArray().size()));
        System.out.println(tempEmail);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSpecialization);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new SpecializationsAdapter(allSpecializations);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SpecializationsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                s = allSpecializations.get(position).getSpec();
                Log.i("s = ", s);
                Intent intent = new Intent(getApplicationContext(),DisplayDoctors.class);
                intent.putExtra("specialization", s);
                intent.putExtra("patientEmail", tempEmail);
                startActivity(intent);
            }
        });

    }

    public ArrayList<Specialization> initializeArray(){
        allSpecializations.add(new Specialization("specialization"));
        allSpecializations.add(new Specialization("Ophthalmology (Eyes)"));
        allSpecializations.add(new Specialization("Phoniatrics"));
        allSpecializations.add(new Specialization("Physiotherapy and sport injuries"));
        allSpecializations.add(new Specialization("Dietitian"));
        allSpecializations.add(new Specialization("Pediatrics and Newborn"));
        allSpecializations.add(new Specialization("Psychiatrist"));
        allSpecializations.add(new Specialization("Bones"));
        allSpecializations.add(new Specialization("Dermatologist"));
        return allSpecializations;
    }

}
