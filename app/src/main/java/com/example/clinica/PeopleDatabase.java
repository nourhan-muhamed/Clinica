package com.example.clinica;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.*;

public class PeopleDatabase extends SQLiteOpenHelper {
    private static String databaseName = "allUsersDatabase";

    //private static String createUsersTable = "create table users(email text primary key, username text, password text not null, typeIndicator integer)";

    private static String createPatientsTable = "create table patients(patientEmail text primary key, username text not null, " +
            "password text not null, age text, phone text unique, gender text)";

    private static String createDoctorsTable = "create table doctors(doctorEmail text primary key, username text not null," +
            "password text not null, age integer,gender text,booking_price integer,phone text, workplace_address text," +
            "specialization text, city text, starting_hours text, closing_hours text, slot_time text,MembershipType integer)";

    private static String createAppointmentsTable = "create table appointments(patient_email text not null references patients(patientEmail), " +
            "doctor_email text not null references doctors(doctorEmail), dateAndTime text,Status text, " +
            "primary key (patient_email, doctor_email))";

    private static String createSlotsTable = "create table slots(doctor text, slot text, availability integer)";

    private static SQLiteDatabase AppDataBase;

    ArrayList<Appointment> appointments = new ArrayList<>();
    public ArrayList<Timeslot> emptySlots = new ArrayList<>();

    public PeopleDatabase(Context context) {
        super(context, databaseName, null, 1);
        this.AppDataBase = AppDataBase;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createPatientsTable);
        sqLiteDatabase.execSQL(createDoctorsTable);
        sqLiteDatabase.execSQL(createAppointmentsTable);
        sqLiteDatabase.execSQL(createSlotsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists patients");
        sqLiteDatabase.execSQL("drop table if exists doctors");
        sqLiteDatabase.execSQL("drop table if exists appointments");
        sqLiteDatabase.execSQL("drop table if exists slots ");
        onCreate(sqLiteDatabase);
    }

    public void addPatient(String username, String email, String password, String age, String phoneNo, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        /*ContentValues newUser = new ContentValues();
        newUser.put("email", email);
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("typeIndicator", 1);
        db.insert("users", null, newUser);*/
        ContentValues newPatient = new ContentValues();
        newPatient.put("username", username);
        newPatient.put("patientEmail", email);
        newPatient.put("password", password);
        newPatient.put("age", age);
        newPatient.put("phone", phoneNo);
        newPatient.put("gender", gender);
        db.insert("patients", null, newPatient);
        Log.i("My Activity", "Record inserted");
        db.close();
    }

    public boolean exist(String EMail, String Tablename) {
        AppDataBase = getReadableDatabase();
        if (Tablename.equals("patients")) {
            Cursor cursor = AppDataBase.rawQuery("select * from patients where patientEmail =?  ", new String[]{EMail});
            if (cursor.getCount() > 0) {
                return true;
            }
        } else if (Tablename.equals("doctors")) {
            Cursor cursor = AppDataBase.rawQuery("select * from doctors where doctorEmail =? ", new String[]{EMail});
            if (cursor.getCount() > 0) {
                return true;
            }
        }

        return false;
    }
    public int[] getAdminStatictics(){
        AppDataBase=getReadableDatabase();
        Cursor usersCur=AppDataBase.rawQuery("select patientEmail from patients",null);
        Cursor doctorsCur=AppDataBase.rawQuery("select doctorEmail from doctors",null);

        int[] activeNumber=new int[2];
        activeNumber[0]=usersCur.getCount();
        activeNumber[1]=doctorsCur.getCount();
        return activeNumber;
    }

    public void addDoctor(String username, String email, String password, String age, String phoneNo, String gender,
                          int price, String start, String finish, int slot, String workplace, String specialty, String city,int MembershipType) {
        SQLiteDatabase db = this.getWritableDatabase();
        /*ContentValues newUser = new ContentValues();
        newUser.put("email", email);
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("typeIndicator", 2);
        db.insert("users", null, newUser);*/
        ContentValues newDoctor = new ContentValues();
        newDoctor.put("username", username);
        newDoctor.put("doctorEmail", email);
        newDoctor.put("password", password);
        newDoctor.put("age", age);
        newDoctor.put("phone", phoneNo);
        newDoctor.put("gender", gender);
        newDoctor.put("starting_hours", start);
        newDoctor.put("closing_hours", finish);
        newDoctor.put("slot_time", slot);
        newDoctor.put("booking_price", price);
        newDoctor.put("workplace_address", workplace);
        newDoctor.put("specialization", specialty);
        newDoctor.put("city", city);
        newDoctor.put("MembershipType",MembershipType);
        db.insert("doctors", null, newDoctor);
        Log.i("Doctor", "Record inserted");
        db.close();
    }
  /*  public void UpdatePersonalData(String newName,String NewPassword){
        AppDataBase= getWritableDatabase();
        AppDataBase.execSQL("update users set Name = ? where email =?",new String[] {newName,loggedInUser.getEmail()});
        AppDataBase.execSQL("update users set Password = ? where email =?",new String[] {NewPassword,loggedInUser.getEmail()});
        AppDataBase.close();
    }*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void calcAppointment(String docEmail, String start, String end, double slot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues s = new ContentValues();

        /*SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = df.parse(start);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, 10);
        String newTime = df.format(cal.getTime());*/
        double slotInHours = slot / 60;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        int iterations = (int) (((Integer.parseInt(end.substring(0, 2))) - (Integer.parseInt(start.substring(0, 2)))) / slotInHours);
        //Log.i("itertaions", String.valueOf(iterations));
        String temp;
        for (int i = 0; i < iterations; ++i) {
            temp = start;
            LocalTime lt = LocalTime.parse(start);
            start = df.format(lt.plusMinutes((long) slot));
            Log.i("new slot", start);
            s.put("doctor", docEmail);
            s.put("slot", temp + " - " + start);
            db.insert("slots", null, s);
        }
    }

    public void deletePatient(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete("users", "email = ?", new String[]{String.valueOf(email)});
        db.delete("patients", "patientEmail = ?", new String[]{String.valueOf(email)});
    }

    public void deleteDoctor(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete("users", "email = ?", new String[]{String.valueOf(email)});
        db.delete("doctors", "doctorEmail = ?", new String[]{String.valueOf(email)});
    }

    public void deleteAppointment(String patientEmail, String drEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("appointments", "patient_email = ? and doctor_email = ?", new String[]{String.valueOf(patientEmail), String.valueOf(drEmail)});
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void changingWorkingHours(String drEmail, String startHours, String closingHours, int slot) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete("slots", "doctor = ?", new String[]{String.valueOf(drEmail)});
        calcAppointment(drEmail, startHours, closingHours, slot);
    }

    public int loginCheck(String email, String password, int type) {
        AppDataBase = getReadableDatabase();
        if (type == 1) {
            Cursor cursor = AppDataBase.rawQuery("select * from patients where patientEmail =? and Password =?", new String[]{email, password});
            if (cursor.getCount() > 0)
                return 1;
            else
                return 0;
        }
        else if (type == 2) {
            Cursor cursor = AppDataBase.rawQuery("select * from doctors where doctorEmail =? and Password =?", new String[]{email, password});
            if (cursor.getCount() > 0) {
                return 2;

            }
            else
                return 0;
        }
        else
            return 0;
    }

    public ArrayList<Doctors> fetchDoctorsData(Cursor cursor){
        ArrayList<Doctors> doctorsList = new ArrayList<>();
        while(!cursor.isAfterLast()){
            Doctors tmp = new Doctors();
            tmp.setUserEmail(cursor.getString(0));
            tmp.setName(cursor.getString(1));
            tmp.setSpecialty(cursor.getString(2));
            tmp.setLocation(cursor.getString(6) + ", " + cursor.getString(5));
            tmp.setWorkingHoursStart(cursor.getString(3));
            tmp.setWorkingHoursEnd(cursor.getString(4));
            doctorsList.add(tmp);
            cursor.moveToNext();
        }
        return doctorsList;
    }

    public Cursor getAllPendingDoctors(){
        AppDataBase=getReadableDatabase();
        //Cursor[] data=new Cursor[2];
        //getting Doctor data
        Cursor data=AppDataBase.rawQuery("select doctorEmail, doctors.username,doctors.specialization ," +
                "starting_hours,closing_hours,city, workplace_address from doctors where MembershipType = 2", new String[]{});
        if (data!=null){
            data.moveToFirst();
        }
        Log.i("number of pending", String.valueOf(data.getCount()));
        AppDataBase.close();

        return data;
    }

    @SuppressLint("Recycle")
    public void refuseDoctor(String email){
        AppDataBase= getWritableDatabase();
        AppDataBase.delete("doctors","doctorEmail = ?",new String[] {email});

        AppDataBase.close();
    }

    @SuppressLint("Recycle")
    public void acceptDoctor(String email){
        AppDataBase= getWritableDatabase();
        AppDataBase.execSQL("update doctors set MembershipType = 1 where doctorEmail =?",new String[] {email});
        AppDataBase.close();
    }

    public ArrayList<Doctors> displaySpecificDoctorsData(String specialization){
        ArrayList<Doctors> doctors = new ArrayList<>();
        Cursor c = this.getSpecificDoctors(specialization);
        c.moveToFirst();
        if (c.getCount() == 0){
            Log.i("cursor state", "empty");
        }
        else{
            Doctors d = new Doctors(c.getString(0),c.getString(1), c.getString(3) + ", " + c.getString(2),
                    Integer.parseInt(c.getString(4)));
            doctors.add(d);
        }
        return doctors;
    }

    public Cursor getSpecificDoctors(String specialization){
        AppDataBase = getReadableDatabase();
        Cursor c = AppDataBase.rawQuery("select username, specialization, city, workplace_address, booking_price from " +
                "doctors where specialization = ?", new String[]{specialization});
        return c;
    }

    public ArrayList<Appointment> displayPatientAppointments(String patientEmail){
        Cursor c = this.getPatientAppointments(patientEmail);
        c.moveToFirst();
        if (c.getCount() == 0){
            Log.i("cursor state", "0, no appointments");
            return null;
        }
        else{
            while (c.moveToNext()){
                Appointment a = new Appointment(c.getString(0),c.getString(1), c.getString(2),c.getString(3));
                appointments.add(a);
            }
        }
        return appointments;
    }

    public Cursor getPatientAppointments(String patientEmail){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from appointments where patient_email = ?", new String[]{patientEmail});
        Log.i("cursor in patient_app", String.valueOf(c.getCount()));
        return  c;
    }

    public void addAppointment(String pEmail, String dEmail, String dateTime, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newApp = new ContentValues();
        newApp.put("patient_email", pEmail);
        newApp.put("doctor_email", dEmail);
        newApp.put("dateAndTime", dateTime);
        newApp.put("status", status);
        db.insert("appointments", null, newApp);
    }

    public Cursor retrieveSlots(String docMail){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from slots where doctor = ?", new String[]{docMail});
        Log.i("slot cursor", String.valueOf(c.getCount()));
        return c;
    }

    public ArrayList<Timeslot> displaySlotData(String doctor) {
        Cursor cursor = this.retrieveSlots(doctor);

        if (cursor.getCount() == 0){
            Log.i("cursor state", "empty");
            return null;
        }
        else{
            while(cursor.moveToNext()){
                if (cursor.getString(2).equals("1")){
                    Timeslot s = new Timeslot(cursor.getString(1), Boolean.valueOf(cursor.getString(2)));
                    emptySlots.add(s);
                }
            }
            Log.i("array size", String.valueOf(emptySlots.size()));

        }
        return  emptySlots;
    }

    public void addSlot(String pMail, String dMail, String dateAndTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newSlot = new ContentValues();
        newSlot.put("patient_email", pMail);
        newSlot.put("doctor_email", dMail);
        newSlot.put("dateAndTime", dateAndTime);
        newSlot.put("availability", 1);
        db.insert("slots", null, newSlot);
    }

    public void changeSlotStatus(String doctor, String slot){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues update = new ContentValues();
        update.put("doctor", doctor);
        update.put("slot", slot);
        update.put("availability", 0);
        db.update("slots", update, "slot = ?", new String[]{slot});
    }

    public String getUser(String email){
        AppDataBase=getReadableDatabase();
        //getting Doctor data
        Cursor data=AppDataBase.rawQuery("select username from patients where patientEmail =?", new String[] {email});
        data.moveToFirst();;
        return data.getString(0);
    }

}