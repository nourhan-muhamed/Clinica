package com.example.clinica;

public class Timeslot {
    private String slotTime;
    private boolean availability;

    public Timeslot(String slotTime, boolean availability) {
        this.slotTime = slotTime;
        this.availability = availability;
    }

    public void changeText(String newText){
        slotTime = newText;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public boolean isAvailability() {
        return availability;
    }

}
