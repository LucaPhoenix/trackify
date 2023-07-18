package de.info6.trackify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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


import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button buttonNeueFahrt, buttonBisherigeFahrten;

    ImageView gameification;

    ImageButton imageButton_profile;

    Bitmap photoProblem;


    HashMap<String, Object> collectedData = new HashMap<>();
    boolean aktiveFahrt, aktiveFahrtHaltestelle;

    String userId;

    //Firebase Nutzer
    private FirebaseAuth mAuth;


    int zaehler = 0;
  
  
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
                            //Toast.makeText(MainActivity.this,  " Login succesfull", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this,  "Login failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });


        //Überprüfung, ob die App zum ersten Mal gestartet wurde
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("firstTimeOpened", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("firstTimeOpened", false).commit();

            Intent intent = new Intent(MainActivity.this, Profil.class);
            intent.putExtra("firstStart", true);
            startActivity(intent);

        }

        collectedData = (HashMap<String, Object>) getIntent().getSerializableExtra("CollectedData");
        //Intent abrufen
        Intent intentStart = getIntent();

        aktiveFahrt = false;
        aktiveFahrtHaltestelle = false;

        if(intentStart != null){
            aktiveFahrt = intentStart.getBooleanExtra("aktiveFahrt", false);
            aktiveFahrtHaltestelle = intentStart.getBooleanExtra("aktiveFahrtHaltestelle", false);
            userId = intentStart.getStringExtra("userId");

            //Die folgenden drei Zeilen sind nötig für das Problem Foto
            byte[] byteArray = getIntent().getByteArrayExtra("photoProblem");
            if (byteArray != null) {
                photoProblem = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                collectedData.put("bitmapPhoto", photoProblem);
            }
            if(collectedData != null) {
                Log.d("----TAG", collectedData.toString());
            }
        }

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        //Buttons initialisieren
        buttonNeueFahrt = findViewById(R.id.button_neueFahrt);
        buttonBisherigeFahrten = findViewById(R.id.button_bisherigeFahrten);

        //ImageView initialisieren
        gameification = findViewById(R.id.imageView_gamefication);

        imageButton_profile = findViewById(R.id.imageButton_Profile);

        //If aktiveFahrt == true, dann umbenennen von Button
        if (aktiveFahrt){
            buttonNeueFahrt.setText("Aktive Fahrt fortführen");
        } else if (aktiveFahrtHaltestelle) {
            buttonNeueFahrt.setText("Einsteigen");
        }

        setGameification();


        //OnClickListener
        buttonNeueFahrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aktiveFahrt) {
                    Intent intent = new Intent(MainActivity.this, Middleactivity.class);
                    intent.putExtra("CollectedData", collectedData);
                    startActivity(intent);
                }

                else if (aktiveFahrtHaltestelle) {
                    Intent intent = new Intent(MainActivity.this, AktiveFahrtHaltestelle.class);
                    intent.putExtra("CollectedData", collectedData);
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
                if (aktiveFahrt) {
                    Toast.makeText(MainActivity.this, "Die alten Fahrten können momentan nicht angezeigt werden, da gerade eine Fahrt getrackt wird", Toast.LENGTH_LONG).show();
                }

                else if (aktiveFahrtHaltestelle) {
                    Toast.makeText(MainActivity.this, "Die alten Fahrten können momentan nicht angezeigt werden, da gerade eine Fahrt getrackt wird", Toast.LENGTH_LONG).show();
                }

                else {
                    Intent intent = new Intent(MainActivity.this, BisherigeFahrtenLuca.class);
                    startActivity(intent);
                }
            }
        });

        imageButton_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (aktiveFahrt) {
                    Toast.makeText(MainActivity.this, "Das Profil kann nicht geöffnet werden, da gerade eine Fahrt getrackt wird", Toast.LENGTH_LONG).show();
                }

                else if (aktiveFahrtHaltestelle) {
                    Toast.makeText(MainActivity.this, "Das Profil kann nicht geöffnet werden, da gerade eine Fahrt getrackt wird", Toast.LENGTH_LONG).show();
                }

                else {
                    Intent intent = new Intent(MainActivity.this, Profil.class);
                    startActivity(intent);
                }
            }
        });

        //Toast für EasterEgg initialisieren
        Toast mToast = Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zaehler++;

                int anzahlDruecken = 5 - zaehler;
                String anzeige = "Noch " + anzahlDruecken + " mal drücken.";

                mToast.setText(anzeige);
                mToast.show();


                if (zaehler == 5){
                    Intent intent = new Intent(MainActivity.this, DevActivity.class);
                    startActivity(intent);
                }

            }
        });



    }

    public void doSomething(){

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        //Dokument und Photo Id
        String idNeu = UUID.randomUUID().toString();

/*
        //Daten hochladen
        firebaseHelper.fahrtInFirebaseSpeichern("wert1", "wert2", "wert3", "wert4",
                "wert5", "wert6", "wert7", "wert8", "wert9",
                "wert10", "wert11", "wert12", "wert13", "wert14", "wert15",
                "wert16", "wert16", "wert16", "wert16", "wert16", "wert16", userId, idNeu);


 */

        if (photoProblem != null) {
            //Photos hochladen
            firebaseHelper.bildInFirebaseStorageSpeichern(photoProblem, "problem", idNeu);
        }

    }



    private void setGameification(){

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        int gameificationLvl = settings.getInt("gameification", 0);

        if (gameificationLvl >= 0){
            gameification.setImageDrawable(getDrawable(R.drawable.grass));
        }

        if (gameificationLvl >= 5){
            gameification.setImageDrawable(getDrawable(R.drawable.grassundblume));
        }

        if (gameificationLvl >= 10){
            gameification.setImageDrawable(getDrawable(R.drawable.grassund2blumen));
        }

        if (gameificationLvl >= 15){
            gameification.setImageDrawable(getDrawable(R.drawable.grassund3blumen));
        }

        if (gameificationLvl >= 20){
            gameification.setImageDrawable(getDrawable(R.drawable.bildwald));
        }

        //Diese Zeile kannst du auskommentieren und dann immer kurz zum Profil und wieder zurück gehen, damit du
        //dir die Gameification anschauen kannst
        //settings.edit().putInt("gameification", gameificationLvl + 5).commit();

        //Bei der Activity FahrtBeenden können dann folgende Zeilen dafür sorgen, dass das funktioniert:
        /*
        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int gameificationLvl = settings.getInt("gameification", 0);
        settings.edit().putInt("gameification", gameificationLvl + 1).commit();
         */


    }

}