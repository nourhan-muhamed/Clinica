package com.example.clinica;

public class Doctors {
    String name, Specialty, Location, UserEmail,workingHoursStart, workingHoursEnd;
    int fees;

    public Doctors() {
    }

    public Doctors(String drName, String specialty, String address, int fees) {
        this.name = drName;
        this.Specialty = specialty;
        this.Location = address;
        this.fees = fees;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public Doctors(String name, String specialty, String location, String workingHoursStart, String workingHoursEnd) {
        this.name = name;
        Specialty = specialty;
        Location = location;
        this.workingHoursStart = workingHoursStart;
        this.workingHoursEnd = workingHoursEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return Specialty;
    }

    public void setSpecialty(String specialty) {
        Specialty = specialty;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(String workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public String getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(String workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }
}