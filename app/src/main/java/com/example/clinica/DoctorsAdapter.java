package com.example.clinica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder>{
    private ArrayList<Doctors> allDoctors;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        public TextView doctorView, specView, addView, feeView;

        public DoctorViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            doctorView = itemView.findViewById(R.id.doctorView);
            specView = itemView.findViewById(R.id.specializationView);
            addView = itemView.findViewById(R.id.addressView);
            feeView = itemView.findViewById(R.id.feeView);
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

    public DoctorsAdapter(ArrayList<Doctors> d){
        allDoctors = d;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_entry, parent, false);
        DoctorViewHolder dvh = new DoctorViewHolder(v, itemClickListener);
        return dvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctors d = allDoctors.get(position);
        holder.doctorView.setText(d.getName());
        holder.specView.setText(d.getSpecialty());
        holder.addView.setText(d.getLocation());
        holder.feeView.setText(String.valueOf(d.getFees()));
    }

    @Override
    public int getItemCount() {
        return allDoctors.size();
    }
}
