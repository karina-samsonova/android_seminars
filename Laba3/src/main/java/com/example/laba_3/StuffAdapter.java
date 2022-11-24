package com.example.laba_3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StuffAdapter extends RecyclerView.Adapter<StuffAdapter.StuffViewHolder> {

    private Context mCtx;
    private List<Stuff> stuffList;

    public StuffAdapter(Context mCtx, List<Stuff> stuffList) {
        this.mCtx = mCtx;
        this.stuffList = stuffList;
    }

    @Override
    public StuffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_stuff, parent, false);
        return new StuffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StuffViewHolder holder, int position) {
        Stuff c = stuffList.get(position);
        holder.textViewId.setText(Integer.toString(c.getId()));
        holder.textViewName.setText(c.getName());
        holder.textViewPrice.setText(c.getPrice());
        holder.textViewStock.setText(c.getStock());
    }

    @Override
    public int getItemCount() {
        return stuffList.size();
    }

    class StuffViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewId, textViewName, textViewPrice, textViewStock;

        public StuffViewHolder(View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.textViewId);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewStock = itemView.findViewById(R.id.textViewStock);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Stuff stuff = stuffList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateStuffActivity.class);
            intent.putExtra("stuff", stuff);

            mCtx.startActivity(intent);
        }
    }
}