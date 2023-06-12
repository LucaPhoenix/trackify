package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button buttonNeueFahrt, buttonBisherigeFahrten, buttonExportieren;

    ImageView gameification;

    boolean aktiveFahrt, aktiveFahrtHaltestelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent abrufen
        Intent intentStart = getIntent();

        aktiveFahrt = false;
        aktiveFahrtHaltestelle = false;

        if(intentStart != null){
            aktiveFahrt = intentStart.getBooleanExtra("aktiveFahrt", false);
            aktiveFahrtHaltestelle = intentStart.getBooleanExtra("aktiveFahrtHaltestelle", false);
        }

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        //Buttons initialisieren
        buttonNeueFahrt = findViewById(R.id.button_neueFahrt);
        buttonBisherigeFahrten = findViewById(R.id.button_bisherigeFahrten);
        buttonExportieren = findViewById(R.id.button_exportieren);

        //ImageView initialisieren
        gameification = findViewById(R.id.imageView_gamefication);

        //If aktiveFahrt == true, dann umbenennen von Button
        if (aktiveFahrt){
            buttonNeueFahrt.setText("Aktive Fahrt fortführen");
        }


        //OnClickListener
        buttonNeueFahrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!aktiveFahrt) {
                    Intent intent = new Intent(MainActivity.this, NeueFahrtStartzeit.class);
                    startActivity(intent);
                }

                if (!aktiveFahrtHaltestelle) {
                    Intent intent = new Intent(MainActivity.this, Middleactivity.class);
                    startActivity(intent);
                }

                else {
                    Intent intent = new Intent(MainActivity.this, AktiveFahrtHaltestelle.class);
                    startActivity(intent);
                }
            }
        });

        buttonBisherigeFahrten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonExportieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}