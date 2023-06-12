package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button buttonNeueFahrt, buttonBisherigeFahrten, buttonExportieren;

    ImageView gameification;

    ImageButton imageButton_profile;

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

        imageButton_profile = findViewById(R.id.imageButton_Profile);

        //If aktiveFahrt == true, dann umbenennen von Button
        if (aktiveFahrt){
            buttonNeueFahrt.setText("Aktive Fahrt fortführen");
        } else if (aktiveFahrtHaltestelle) {
            buttonNeueFahrt.setText("Einsteigen");
        }


        //OnClickListener
        buttonNeueFahrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aktiveFahrt) {
                    Intent intent = new Intent(MainActivity.this, Middleactivity.class);
                    startActivity(intent);
                }

                else if (aktiveFahrtHaltestelle) {
                    Intent intent = new Intent(MainActivity.this, AktiveFahrtHaltestelle.class);
                    startActivity(intent);
                }

                else {
                    Intent intent = new Intent(MainActivity.this, NeueFahrtStartzeit.class);
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

        imageButton_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Profil.class);
                startActivity(intent);
            }
        });



    }
}