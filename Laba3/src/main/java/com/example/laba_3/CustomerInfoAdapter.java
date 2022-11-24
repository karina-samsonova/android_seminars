package com.example.laba_3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class CustomerInfoAdapter extends RecyclerView.Adapter<CustomerInfoAdapter.CustomerInfoViewHolder> {

    private Context mCtx;
    private List<History> historyList;

    public CustomerInfoAdapter(Context mCtx, List<History> historyList) {
        this.mCtx = mCtx;
        this.historyList = historyList;
    }

    @Override
    public CustomerInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_history, parent, false);
        return new CustomerInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerInfoViewHolder holder, int position) {
        History c = historyList.get(position);
        holder.textViewDate.setText(c.getDate());
        holder.textViewName.setText(c.getName());
        holder.textViewPrice.setText(c.getPrice());
        holder.textViewStatus.setText(c.getStatus());
        if (Objects.equals(c.getStatus(), "received"))
            holder.textViewStatus.setBackgroundResource(R.color.active);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class CustomerInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewDate, textViewName, textViewPrice, textViewStatus;

        public CustomerInfoViewHolder(View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}