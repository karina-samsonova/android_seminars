package com.example.laba_3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mCtx;
    private List<Order> orderList;

    public OrderAdapter(Context mCtx, List<Order> orderList) {
        this.mCtx = mCtx;
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order c = orderList.get(position);
        holder.textViewId.setText(Integer.toString(c.getId()));
        holder.textViewDate.setText(c.getDate());
        holder.textViewStuff.setText(Integer.toString(c.getStuff()));
        holder.textViewCustomer.setText(Integer.toString(c.getCustomer()));
        holder.textViewStatus.setText(c.getStatus());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewId, textViewDate, textViewStuff, textViewCustomer, textViewStatus;

        public OrderViewHolder(View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.textViewId);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewStuff = itemView.findViewById(R.id.textViewStuff);
            textViewCustomer = itemView.findViewById(R.id.textViewCustomer);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Order order = orderList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateOrderActivity.class);
            intent.putExtra("order", order);

            mCtx.startActivity(intent);
        }
    }
}
