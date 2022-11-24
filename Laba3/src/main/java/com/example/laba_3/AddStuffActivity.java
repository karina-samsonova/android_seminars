package com.example.laba_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddStuffActivity extends AppCompatActivity {

    private EditText editTextName, editTextPrice;
    private SwitchCompat switchStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stuff);

        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        switchStock = findViewById(R.id.switch1);


        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStuff();
            }
        });
    }

    private void saveStuff() {
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


        class SaveStuff extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Stuff stuff = new Stuff();
                stuff.setName(sName);
                stuff.setPrice(sPrice);
                stuff.setStock(sStock);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .stuffDao()
                        .insert(stuff);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), StuffActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveStuff sc = new SaveStuff();
        sc.execute();
    }
    public void goBack(View view){
        Intent intent = new Intent(this, StuffActivity.class);
        startActivity(intent);
    }
}