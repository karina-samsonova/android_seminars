package com.example.laba_3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private Context mCtx;
    private List<Customer> customerList;

    public CustomerAdapter(Context mCtx, List<Customer> customerList) {
        this.mCtx = mCtx;
        this.customerList = customerList;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        Customer c = customerList.get(position);
        holder.textViewId.setText(Integer.toString(c.getId()));
        holder.textViewSurname.setText(c.getSurname());
        holder.textViewName.setText(c.getName());
        holder.textViewAddress.setText(c.getAddress());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewId, textViewSurname, textViewName, textViewAddress;

        public CustomerViewHolder(View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.textViewId);
            textViewSurname = itemView.findViewById(R.id.textViewSurname);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Customer customer = customerList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, CustomerInfoActivity.class);
            intent.putExtra("customer", customer);

            mCtx.startActivity(intent);
        }
    }
}
