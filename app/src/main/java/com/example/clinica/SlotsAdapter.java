package com.example.clinica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.SlotViewHolder> {
    private ArrayList<Timeslot> availableSlots;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }
    public static class SlotViewHolder extends RecyclerView.ViewHolder {
        public TextView slotView;

        public SlotViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            slotView = itemView.findViewById(R.id.slotView);
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

    public SlotsAdapter(ArrayList<Timeslot> slotList){
        availableSlots = slotList;
    }

    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.slotentry, parent, false);
        SlotViewHolder svh = new SlotViewHolder(v, itemClickListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder holder, int position) {
        Timeslot ts = availableSlots.get(position);
        holder.slotView.setText(ts.getSlotTime());

    }

    @Override
    public int getItemCount() {
        return availableSlots.size();
    }
}
