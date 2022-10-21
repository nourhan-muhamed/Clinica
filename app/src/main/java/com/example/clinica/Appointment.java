package com.example.clinica;

public class Appointment {
    private String pMail, dMail, dateTime, status;

    public Appointment(String pMail, String dMail, String dateTime, String status) {
        this.pMail = pMail;
        this.dMail = dMail;
        this.dateTime = dateTime;
        this.status = status;
    }

    public String getpMail() {
        return pMail;
    }

    public String getdMail() {
        return dMail;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String  getStatus() {
        return status;
    }
}
