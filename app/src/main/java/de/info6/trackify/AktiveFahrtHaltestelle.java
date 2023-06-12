package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AktiveFahrtHaltestelle extends AppCompatActivity {

    TextView editText_ankunftHaltestelle, editText_startzeitFahrt;

    Button button_speichernHaltestelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktive_fahrt_haltestelle);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //EditText initialisieren
        editText_ankunftHaltestelle = findViewById(R.id.editText_ankunftHaltestelle);
        editText_startzeitFahrt = findViewById(R.id.editText_startzeitFahrt);

        button_speichernHaltestelle = findViewById(R.id.button_speichernHaltestelle);

        button_speichernHaltestelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AktiveFahrtHaltestelle.this, MainActivity.class);
                intent.putExtra("aktiveFahrtHaltestelle", true);
                startActivity(intent);
            }
        });

    }
}