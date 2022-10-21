package com.example.clinica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    PeopleDatabase db = new PeopleDatabase(this);
    Login l = new Login();
    TextView nav_name;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_name =findViewById(R.id.nav_name);
        Intent intent = getIntent();
        nav_name.setText(intent.getStringExtra("UserName"));
        value = intent.getStringExtra("passedEmail");
        CardView bookDoctor, viewAppointment;
        bookDoctor = findViewById(R.id.book_doctor);
        viewAppointment = findViewById(R.id.active_appointments);
        bookDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChooseSpecialty.class);
                intent.putExtra("emailAgain", String.valueOf(value));
                startActivity(intent);
            }
        });

        viewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("email in homepage: ", String.valueOf(value));
                Intent i = new Intent(getApplicationContext(), ViewAppointments.class);
                i.putExtra("twiceEmail", String.valueOf(value));
                startActivity(i);
            }
        });

    }


    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }
    public void ClickHome(View view)  {
        recreate();
    }
    public void ClickLogout(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);}
    public void ClickUpdateProfile(View view) {
        Intent intent = new Intent(getApplicationContext(),aboutus.class);
startActivity(intent);

    }
}


