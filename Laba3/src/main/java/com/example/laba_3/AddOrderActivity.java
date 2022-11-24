package com.example.laba_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddOrderActivity extends AppCompatActivity {

    private EditText editTextDate, editTextStuff, editTextCustomer;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        editTextDate = findViewById(R.id.editTextDate);
        TextWatcher textWatcher = new DateFormatTextWatcher(this.editTextDate);
        this.editTextDate.addTextChangedListener(textWatcher);
        editTextStuff = findViewById(R.id.editTextStuff);
        editTextCustomer = findViewById(R.id.editTextCustomer);
        radioGroup = findViewById(R.id.radioGroup);


        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOrder();
            }
        });
    }

    private void saveOrder() {
        final String sDate = editTextDate.getText().toString().trim();
        final String sStuff = editTextStuff.getText().toString().trim();
        final String sCustomer = editTextCustomer.getText().toString().trim();
        final String sStatus;
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton1)
            sStatus = "preparation";
        else if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton2)
            sStatus = "delivery";
        else if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton3)
            sStatus = "received";
        else{
            Toast.makeText(getApplicationContext(), "Status required", Toast.LENGTH_LONG).show();
            return;
        }


        if (sDate.isEmpty()) {
            editTextDate.setError("Date required");
            editTextDate.requestFocus();
            return;
        }

        if (sStuff.isEmpty()) {
            editTextStuff.setError("Stuff_id required");
            editTextStuff.requestFocus();
            return;
        }

        if (sCustomer.isEmpty()) {
            editTextCustomer.setError("Customer_id required");
            editTextCustomer.requestFocus();
            return;
        }


        class SaveOrder extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Order order = new Order();
                order.setDate(sDate);
                order.setStuff(Integer.parseInt(sStuff));
                order.setCustomer(Integer.parseInt(sCustomer));
                order.setStatus(sStatus);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .orderDao()
                        .insert(order);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), OrderActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveOrder sc = new SaveOrder();
        sc.execute();
    }
    public void goBack(View view){
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}