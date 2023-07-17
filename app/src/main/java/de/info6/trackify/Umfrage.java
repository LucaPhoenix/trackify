package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

public class Umfrage extends AppCompatActivity {

    Button button_umfrageBeenden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umfrage);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button_umfrageBeenden = findViewById(R.id.button_umfrageBeenden);

        button_umfrageBeenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Umfrage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void saveDataInFirebase(Bitmap photoProblem, String startzeit, String gewuenschteAnkunftszeit, String ankunftHaltestelle,
                                    String startzeitFahrt, String startZeitVerkehrsmittel, String ausstiegHaltestelleUmstieg,
                                    String verkehrsmittelUmstieg, String haltestelleStartzeit, String beschreibungProblem,
                                    String umsteigenAnkunfthaltestelle, String umsteigenStartzeitFahrt, String endzeitFahrt,
                                    String ankunftszeitZiel, String umfrageAntwort1, String umfrageAntwort2, String umfrageAntwort3,
                                    String umfrageAntwort4, String umfrageAntwort5, String umfrageAntwort6, String umfrageAntwort7, String datum,
                                    String userId){

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        //Dokument und Photo Id
        String idNeu = UUID.randomUUID().toString();


        //Daten hochladen
        firebaseHelper.fahrtInFirebaseSpeichern(startzeit, gewuenschteAnkunftszeit, ankunftHaltestelle, startzeitFahrt,
                startZeitVerkehrsmittel, ausstiegHaltestelleUmstieg, verkehrsmittelUmstieg, haltestelleStartzeit, beschreibungProblem,
                umsteigenAnkunfthaltestelle, umsteigenStartzeitFahrt, endzeitFahrt, ankunftszeitZiel, umfrageAntwort1, umfrageAntwort2,
                umfrageAntwort3, umfrageAntwort4, umfrageAntwort5, umfrageAntwort6, umfrageAntwort7, datum, userId, idNeu);


        if (photoProblem != null) {
            //Photos hochladen
            firebaseHelper.bildInFirebaseStorageSpeichern(photoProblem, "problem", idNeu);
        }
    }



}