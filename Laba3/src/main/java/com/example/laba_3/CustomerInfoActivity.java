package com.example.laba_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomerInfoActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    CustomerInfoAdapter adapter;
    Customer customer;
    Integer ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        customer = (Customer) getIntent().getSerializableExtra("customer");
        ID = customer.getId();

        TextView surname = findViewById(R.id.textViewSurname);
        surname.setText(customer.getSurname());
        TextView name = findViewById(R.id.textViewName);
        name.setText(customer.getName());

        getHistory();
    }

    private void getHistory() {
        class GetHistory extends AsyncTask<Void, Void, List<History>> {

            @Override
            protected List<History> doInBackground(Void... voids) {
                List<History> historyList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .orderDao()
                        .getHistory(ID);
                return historyList;
            }

            @Override
            protected void onPostExecute(List<History> history) {
                super.onPostExecute(history);
                adapter = new CustomerInfoAdapter(CustomerInfoActivity.this, history);
                recyclerview.setAdapter(adapter);
            }
        }

        GetHistory gc = new GetHistory();
        gc.execute();
    }

    public void goBack(View view){
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
    }

    public void openUpdatePage(View view){
        Intent intent = new Intent(this, UpdateCustomerActivity.class);
        intent.putExtra("customer", customer);
        startActivity(intent);
    }
}