package de.info6.trackify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button buttonNeueFahrt, buttonBisherigeFahrten, buttonExportieren;

    ImageView gameification;

    ImageButton imageButton_profile;

    boolean aktiveFahrt, aktiveFahrtHaltestelle;

    //Firebase Nutzer
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase initialisieren
        FirebaseApp.initializeApp(this);

        //Firebase Nutzer lokal abrufen
        mAuth = FirebaseAuth.getInstance();

        //Firebase Nutzer anonym anmelden
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,  " Login succesfull", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this,  " Login failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

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
                doSomething();
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

    public void doSomething(){

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        /*
        Startzeit
        Gewünschte Ankunftzeit

        Ankunft Haltestelle
        Startzeit Fahrt

        Beschreibung Problem
        Foto Problem

        Umsteigen Ankunft Haltestelle
        Umsteigen Startzeit Fahrt

        Endzeit Fahrt
        Ankunft Ziel

        Umfrage Antworten
         */

        //Dokument und Photo Id
        String idNeu = UUID.randomUUID().toString();


        //Daten hochladen
        firebaseHelper.dokumenteInFirebaseSpeichern("wert1", "wert2", "wert3", "wert4",
                "wert5", "wert6", "wert7", "wert8", "wert9",
                "wert10", "wert11", "wert12", "wert13", "wert14", "wert15",
                "wert16", idNeu);



        Bitmap bitmapProblem = ((BitmapDrawable) gameification.getDrawable()).getBitmap();

        //Photos hochladen
        firebaseHelper.bildInFirebaseStorageSpeichern(bitmapProblem, "problem", idNeu);

        /*
        Startzeit
        Gewünschte Ankunftzeit

        Ankunft Haltestelle
        Startzeit Fahrt

        Beschreibung Problem
        Foto Problem

        Umsteigen Ankunft Haltestelle
        Umsteigen Startzeit Fahrt

        Endzeit Fahrt
        Ankunft Ziel

        Umfrage Antworten


         */

    }


}