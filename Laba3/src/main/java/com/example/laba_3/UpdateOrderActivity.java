package com.example.laba_3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Objects;

public class UpdateOrderActivity extends AppCompatActivity {

    private EditText editTextDate, editTextStuff, editTextCustomer;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);


        editTextDate = findViewById(R.id.editTextDate);
        TextWatcher textWatcher = new DateFormatTextWatcher(this.editTextDate);
        this.editTextDate.addTextChangedListener(textWatcher);
        editTextStuff = findViewById(R.id.editTextStuff);
        editTextCustomer = findViewById(R.id.editTextCustomer);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);



        final Order order = (Order) getIntent().getSerializableExtra("order");

        loadOrder(order);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateOrder(order);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateOrderActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteOrder(order);
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

    private void loadOrder(Order order) {
        editTextDate.setText(order.getDate());
        editTextStuff.setText(Integer.toString(order.getStuff()));
        editTextCustomer.setText(Integer.toString(order.getCustomer()));
        if (Objects.equals(order.getStatus(), "preparation"))
            radioButton1.setChecked(true);
        else if (Objects.equals(order.getStatus(), "delivery"))
            radioButton2.setChecked(true);
        else if (Objects.equals(order.getStatus(), "received"))
            radioButton3.setChecked(true);
    }

    private void updateOrder(final Order order) {
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

        class UpdateOrder extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                order.setDate(sDate);
                order.setStuff(Integer.parseInt(sStuff));
                order.setCustomer(Integer.parseInt(sCustomer));
                order.setStatus(sStatus);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .orderDao()
                        .update(order);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateOrderActivity.this, OrderActivity.class));
            }
        }

        UpdateOrder uc = new UpdateOrder();
        uc.execute();
    }


    private void deleteOrder(final Order order) {
        class DeleteOrder extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .orderDao()
                        .delete(order);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateOrderActivity.this, OrderActivity.class));
            }
        }

        DeleteOrder dc = new DeleteOrder();
        dc.execute();

    }
    public void goBack(View view){
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}