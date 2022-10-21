package com.example.clinica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SpecializationsAdapter extends RecyclerView.Adapter<SpecializationsAdapter.SpecializationViewHolder>{
    ArrayList<Specialization> allSpecialization = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    public SpecializationsAdapter(ArrayList<Specialization> allSpecializations) {
        this.allSpecialization = allSpecializations;
    }

    public static class SpecializationViewHolder extends RecyclerView.ViewHolder {
        public TextView specView;

        public SpecializationViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            specView = itemView.findViewById(R.id.specializationTextView);
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
    public SpecializationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.specialization_entry, parent, false);
        SpecializationViewHolder svh = new SpecializationViewHolder(v,itemClickListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SpecializationViewHolder holder, int position) {
        Specialization s = allSpecialization.get(position);
        holder.specView.setText(s.getSpec());
    }

    @Override
    public int getItemCount() {
        return allSpecialization.size();
    }

}
