package com.example.clinica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.AppointmentViewHolder>{
    private ArrayList<Appointment> allAppointments;
    private ItemClickListener itemClickListener;

    public AppointmentsAdapter(ArrayList<Appointment> appointments) {
        allAppointments = appointments;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }
    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        public TextView patientView, emailView, dateTimeView, statusView;

        public AppointmentViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            patientView = itemView.findViewById(R.id.appPatientEmailView);
            emailView = itemView.findViewById(R.id.appDocEmailView);
            dateTimeView = itemView.findViewById(R.id.dateTimeView);
            statusView = itemView.findViewById(R.id.appStatusView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            itemClickListener.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_entry, parent, false);
        AppointmentViewHolder avh = new AppointmentViewHolder(v, itemClickListener);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment a = allAppointments.get(position);
        holder.patientView.setText(a.getpMail());
        holder.emailView.setText(a.getdMail());
        holder.dateTimeView.setText(a.getDateTime());
        holder.statusView.setText(String.valueOf(a.getStatus()));
    }

    @Override
    public int getItemCount() {
        return allAppointments.size();
    }
}
