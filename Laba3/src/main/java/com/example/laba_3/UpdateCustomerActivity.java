package com.example.laba_3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateCustomerActivity extends AppCompatActivity {

    private EditText editTextSurname, editTextName, editTextAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);


        editTextSurname = findViewById(R.id.editTextSurname);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);


        final Customer customer = (Customer) getIntent().getSerializableExtra("customer");

        loadCustomer(customer);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateCustomer(customer);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCustomerActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteCustomer(customer);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadCustomer(Customer customer) {
        editTextSurname.setText(customer.getSurname());
        editTextName.setText(customer.getName());
        editTextAddress.setText(customer.getAddress());
    }

    private void updateCustomer(final Customer customer) {
        final String sSurname = editTextSurname.getText().toString().trim();
        final String sName = editTextName.getText().toString().trim();
        final String sAddress = editTextAddress.getText().toString().trim();

        if (sSurname.isEmpty()) {
            editTextSurname.setError("Surname required");
            editTextSurname.requestFocus();
            return;
        }

        if (sName.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (sAddress.isEmpty()) {
            editTextAddress.setError("Address required");
            editTextAddress.requestFocus();
            return;
        }

        class UpdateCustomer extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                customer.setSurname(sSurname);
                customer.setName(sName);
                customer.setAddress(sAddress);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .customerDao()
                        .update(customer);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateCustomerActivity.this, CustomerActivity.class));
            }
        }

        UpdateCustomer uc = new UpdateCustomer();
        uc.execute();
    }


    private void deleteCustomer(final Customer customer) {
        class DeleteCustomer extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .customerDao()
                        .delete(customer);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateCustomerActivity.this, CustomerActivity.class));
            }
        }

        DeleteCustomer dc = new DeleteCustomer();
        dc.execute();

    }
    public void goBack(View view){
        Intent intent = new Intent(this, CustomerInfoActivity.class);
        startActivity(intent);
    }
}