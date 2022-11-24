package com.example.laba_3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCustomerActivity extends AppCompatActivity {

    private EditText editTextSurname, editTextName, editTextAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        editTextSurname = findViewById(R.id.editTextSurname);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCustomer();
            }
        });
    }

    private void saveCustomer() {
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

        class SaveCustomer extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Customer customer = new Customer();
                customer.setSurname(sSurname);
                customer.setName(sName);
                customer.setAddress(sAddress);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .customerDao()
                        .insert(customer);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), CustomerActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveCustomer sc = new SaveCustomer();
        sc.execute();
    }
    public void goBack(View view){
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
    }
}