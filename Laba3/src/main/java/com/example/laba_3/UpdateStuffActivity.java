package com.example.laba_3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class UpdateStuffActivity extends AppCompatActivity {

    private EditText editTextName, editTextPrice;
    private SwitchCompat switchStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stuff);


        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        switchStock = findViewById(R.id.switch1);



        final Stuff stuff = (Stuff) getIntent().getSerializableExtra("stuff");

        loadStuff(stuff);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateStuff(stuff);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStuffActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteStuff(stuff);
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

    private void loadStuff(Stuff stuff) {
        editTextName.setText(stuff.getName());
        editTextPrice.setText(stuff.getPrice());
        switchStock.setChecked(Objects.equals(stuff.getStock(), "yes"));
    }

    private void updateStuff(final Stuff stuff) {
        final String sName = editTextName.getText().toString().trim();
        final String sPrice = editTextPrice.getText().toString().trim();
        final String sStock;
        if (switchStock.isChecked())
            sStock = "yes";
        else
            sStock = "no";


        if (sName.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (sPrice.isEmpty()) {
            editTextPrice.setError("Price required");
            editTextPrice.requestFocus();
            return;
        }

        class UpdateStuff extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                stuff.setName(sName);
                stuff.setPrice(sPrice);
                stuff.setStock(sStock);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .stuffDao()
                        .update(stuff);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateStuffActivity.this, StuffActivity.class));
            }
        }

        UpdateStuff uc = new UpdateStuff();
        uc.execute();
    }


    private void deleteStuff(final Stuff stuff) {
        class DeleteStuff extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .stuffDao()
                        .delete(stuff);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateStuffActivity.this, StuffActivity.class));
            }
        }

        DeleteStuff dc = new DeleteStuff();
        dc.execute();

    }

    public void goBack(View view){
        Intent intent = new Intent(this, StuffActivity.class);
        startActivity(intent);
    }
}