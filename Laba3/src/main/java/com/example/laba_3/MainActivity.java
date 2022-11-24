package com.example.laba_3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addData();
    }

    private void addData() {

        @SuppressLint("StaticFieldLeak")
        class AddData extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                List<Order> orderList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .orderDao()
                        .getAll();
                if (orderList.size() == 0) {
                    Customer customer = new Customer();
                    customer.setSurname("Ivanov");
                    customer.setName("Ivan");
                    customer.setAddress("Moscow City");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .customerDao()
                            .insert(customer);
                    customer.setSurname("Petrova");
                    customer.setName("Anastasia");
                    customer.setAddress("Nijhny Novgorod");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .customerDao()
                            .insert(customer);
                    Stuff stuff = new Stuff();
                    stuff.setName("Teddy Bear");
                    stuff.setPrice("2000");
                    stuff.setStock("yes");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .stuffDao()
                            .insert(stuff);
                    stuff.setName("Ball");
                    stuff.setPrice("500");
                    stuff.setStock("yes");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .stuffDao()
                            .insert(stuff);
                    stuff.setName("Barbie Doll");
                    stuff.setPrice("5000");
                    stuff.setStock("no");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .stuffDao()
                            .insert(stuff);
                    stuff.setName("Skipping rope");
                    stuff.setPrice("500");
                    stuff.setStock("yes");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .stuffDao()
                            .insert(stuff);

                    Order order = new Order();
                    order.setDate("20/09/2022");
                    order.setStuff(1);
                    order.setCustomer(1);
                    order.setStatus("received");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .orderDao()
                            .insert(order);
                    order.setDate("30/09/2022");
                    order.setStuff(2);
                    order.setCustomer(1);
                    order.setStatus("delivery");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .orderDao()
                            .insert(order);
                    order.setDate("30/09/2022");
                    order.setStuff(4);
                    order.setCustomer(1);
                    order.setStatus("delivery");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .orderDao()
                            .insert(order);
                    order.setDate("25/09/2022");
                    order.setStuff(3);
                    order.setCustomer(2);
                    order.setStatus("delivery");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .orderDao()
                            .insert(order);
                    order.setDate("02/10/2022");
                    order.setStuff(4);
                    order.setCustomer(1);
                    order.setStatus("preparation");
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .orderDao()
                            .insert(order);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), StartingActivity.class));
            }
        }

        AddData sc = new AddData();
        sc.execute();
    }
}
