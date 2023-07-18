package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class Umfrage extends AppCompatActivity {

    Button button_umfrageBeenden;

    HashMap<String, Object> collectedData = new HashMap<>();

    RadioButton frage1_sehrGut, frage1_gut, frage1_mittel, frage1_schlecht, frage1_sehrSchlecht,
            frage2_sehrGut, frage2_gut, frage2_mittel, frage2_schlecht, frage2_sehrSchlecht,
            frage5_sehrGut, frage5_gut, frage5_mittel, frage5_schlecht, frage5_sehrSchlecht;

    EditText frage3, frage4, frage6, frage7;

    String antwort1 ="", antwort2="", antwort3="", antwort4="", antwort5="", antwort6="", antwort7="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umfrage);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frage1_sehrGut = findViewById(R.id.frage1_sehrGut);
        frage1_gut = findViewById(R.id.frage1_gut);
        frage1_mittel = findViewById(R.id.frage1_mittel);
        frage1_schlecht = findViewById(R.id.frage1_schlecht);
        frage1_sehrSchlecht = findViewById(R.id.frage1_sehrSchlecht);

        frage2_sehrGut = findViewById(R.id.frage2_sehrGut);
        frage2_gut = findViewById(R.id.frage2_gut);
        frage2_mittel = findViewById(R.id.frage2_mittel);
        frage2_schlecht = findViewById(R.id.frage2_schlecht);
        frage2_sehrSchlecht = findViewById(R.id.frage2_sehrSchlecht);

        frage5_sehrGut = findViewById(R.id.frage5_sehrGut);
        frage5_gut = findViewById(R.id.frage5_gut);
        frage5_mittel = findViewById(R.id.frage5_mittel);
        frage5_schlecht = findViewById(R.id.frage5_schlecht);
        frage5_sehrSchlecht = findViewById(R.id.frage5_sehrSchlecht);

        frage3 =findViewById(R.id.frage3_textView);
        frage4 =findViewById(R.id.frage4_textView);
        frage6 =findViewById(R.id.frage6_textView);
        frage7 =findViewById(R.id.frage7_textView);

        //Intent abrufen
        Intent intentStart = getIntent();

        if(intentStart != null){
            collectedData = (HashMap<String, Object>) getIntent().getSerializableExtra("CollectedData");
            if(collectedData != null) {
                Log.d("----TAG", collectedData.toString());
            }
        }

        frage1_sehrGut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort1 = frage1_sehrGut.getText().toString();
                frage1_sehrGut.setChecked(true);
                frage1_gut.setChecked(false);
                frage1_mittel.setChecked(false);
                frage1_schlecht.setChecked(false);
                frage1_sehrSchlecht.setChecked(false);
            }
        });
        frage1_gut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort1 = frage1_gut.getText().toString();
                frage1_sehrGut.setChecked(false);
                frage1_gut.setChecked(true);
                frage1_mittel.setChecked(false);
                frage1_schlecht.setChecked(false);
                frage1_sehrSchlecht.setChecked(false);
            }
        });

        frage1_mittel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort1 = frage1_mittel.getText().toString();
                frage1_sehrGut.setChecked(false);
                frage1_gut.setChecked(false);
                frage1_mittel.setChecked(true);
                frage1_schlecht.setChecked(false);
                frage1_sehrSchlecht.setChecked(false);
            }
        });
        frage1_schlecht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort1 = frage1_schlecht.getText().toString();
                frage1_sehrGut.setChecked(false);
                frage1_gut.setChecked(false);
                frage1_mittel.setChecked(false);
                frage1_schlecht.setChecked(true);
                frage1_sehrSchlecht.setChecked(false);
            }
        });
        frage1_sehrSchlecht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort1 = frage1_sehrSchlecht.getText().toString();
                frage1_sehrGut.setChecked(false);
                frage1_gut.setChecked(false);
                frage1_mittel.setChecked(false);
                frage1_schlecht.setChecked(false);
                frage1_sehrSchlecht.setChecked(true);
            }
        });

        frage2_sehrGut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort2 = frage2_sehrGut.getText().toString();
                frage2_sehrGut.setChecked(true);
                frage2_gut.setChecked(false);
                frage2_mittel.setChecked(false);
                frage2_schlecht.setChecked(false);
                frage2_sehrSchlecht.setChecked(false);
            }
        });
        frage2_gut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort2 = frage2_gut.getText().toString();
                frage2_sehrGut.setChecked(false);
                frage2_gut.setChecked(true);
                frage2_mittel.setChecked(false);
                frage2_schlecht.setChecked(false);
                frage2_sehrSchlecht.setChecked(false);
            }
        });

        frage2_mittel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort2 = frage2_mittel.getText().toString();
                frage2_sehrGut.setChecked(false);
                frage2_gut.setChecked(false);
                frage2_mittel.setChecked(true);
                frage2_schlecht.setChecked(false);
                frage2_sehrSchlecht.setChecked(false);
            }
        });
        frage2_schlecht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort2 = frage2_schlecht.getText().toString();
                frage2_sehrGut.setChecked(false);
                frage2_gut.setChecked(false);
                frage2_mittel.setChecked(false);
                frage2_schlecht.setChecked(true);
                frage2_sehrSchlecht.setChecked(false);
            }
        });
        frage2_sehrSchlecht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort2 = frage2_sehrSchlecht.getText().toString();
                frage2_sehrGut.setChecked(false);
                frage2_gut.setChecked(false);
                frage2_mittel.setChecked(false);
                frage2_schlecht.setChecked(false);
                frage2_sehrSchlecht.setChecked(true);
            }
        });

        frage5_sehrGut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort5 = frage5_sehrGut.getText().toString();
                frage5_sehrGut.setChecked(true);
                frage5_gut.setChecked(false);
                frage5_mittel.setChecked(false);
                frage5_schlecht.setChecked(false);
                frage5_sehrSchlecht.setChecked(false);
            }
        });
        frage5_gut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort5 = frage5_gut.getText().toString();
                frage5_sehrGut.setChecked(false);
                frage5_gut.setChecked(true);
                frage5_mittel.setChecked(false);
                frage5_schlecht.setChecked(false);
                frage5_sehrSchlecht.setChecked(false);
            }
        });

        frage5_mittel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort5 = frage5_mittel.getText().toString();
                frage5_sehrGut.setChecked(false);
                frage5_gut.setChecked(false);
                frage5_mittel.setChecked(true);
                frage5_schlecht.setChecked(false);
                frage5_sehrSchlecht.setChecked(false);
            }
        });
        frage5_schlecht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort5 = frage5_schlecht.getText().toString();
                frage5_sehrGut.setChecked(false);
                frage5_gut.setChecked(false);
                frage5_mittel.setChecked(false);
                frage5_schlecht.setChecked(true);
                frage5_sehrSchlecht.setChecked(false);
            }
        });
        frage5_sehrSchlecht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort5 = frage5_sehrSchlecht.getText().toString();
                frage5_sehrGut.setChecked(false);
                frage5_gut.setChecked(false);
                frage5_mittel.setChecked(false);
                frage5_schlecht.setChecked(false);
                frage5_sehrSchlecht.setChecked(true);
            }
        });

        button_umfrageBeenden = findViewById(R.id.button_umfrageBeenden);

        button_umfrageBeenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwort3 = frage3.getText().toString();
                antwort4 = frage4.getText().toString();
                antwort6 = frage6.getText().toString();
                antwort7 = frage7.getText().toString();
                
                if(Objects.equals(antwort1, "") || Objects.equals(antwort2, "") || Objects.equals(antwort3, "") || Objects.equals(antwort4, "") || antwort5 =="" || antwort6 =="" || antwort7 ==""){
                    Toast.makeText(Umfrage.this, "Bitte fülle den Fragebogen vollständig aus", Toast.LENGTH_SHORT).show();
                } else if(Integer.parseInt(antwort7) < 0 || Integer.parseInt(antwort7) > 100){
                    Toast.makeText(Umfrage.this, "Bitte Prozentwerte im Bereich von 0-100 eintragen.", Toast.LENGTH_SHORT).show();
                }
                else {
                    collectedData.put("Antwort1", antwort1);
                    collectedData.put("Antwort2", antwort2);
                    collectedData.put("Antwort3", antwort3);
                    collectedData.put("Antwort4", antwort4);
                    collectedData.put("Antwort5", antwort5);
                    collectedData.put("Antwort6", antwort6);
                    collectedData.put("Antwort7", antwort7);
                    saveInBetween();
                    Intent intent = new Intent(Umfrage.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    private void saveInBetween(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        Bitmap photo = (Bitmap) collectedData.get("bitmapPhoto");
        saveDataInFirebase(photo,
                collectedData.get("Startzeit").toString(),
                collectedData.get("Gewünschte Ankunftszeit").toString(),
                collectedData.get("Ankunft Haltestelle").toString(),
                collectedData.get("Startzeit Fahrt").toString(),
                collectedData.get("KoordinatenStartzeit").toString(),
                collectedData.get("Verkehrsmittel").toString(),
                collectedData.get("Haltestellenname").toString(),
                collectedData.get("KoordinatenEinstieg").toString(),
                collectedData.get("Umsteigen Haltestellenname").toString(),
                collectedData.get("Umsteigen Verkehrsmittel").toString(),
                collectedData.get("Umsteigen Ankunft Haltestelle").toString(),
                collectedData.get("Umsteigen Startzeit Fahrt").toString(),
                collectedData.get("KoordinatenUmsteigen").toString(),
                collectedData.get("Problem Beschreibung").toString(),
                collectedData.get("Problem Zeit").toString(),
                collectedData.get("KoordinatenProblem").toString(),
                collectedData.get("Ankunft Haltestelle Ziel").toString(),
                collectedData.get("Ankunftszeit Ziel").toString(),
                collectedData.get("Ankunft Haltestelle Ziel").toString(),
                collectedData.get("KoordinatenEnde").toString(),
                collectedData.get("Antwort1").toString(),
                collectedData.get("Antwort2").toString(),
                collectedData.get("Antwort3").toString(),
                collectedData.get("Antwort4").toString(),
                collectedData.get("Antwort5").toString(),
                collectedData.get("Antwort6").toString(),
                collectedData.get("Antwort7").toString(),
                dtf.format(now), getUserIdFromFile());
    }
    private void saveDataInFirebase(Bitmap photoProblem, String startzeit, String gewuenschteAnkunftszeit, String ankunftHaltestelle,
                                    String startzeitFahrt, String gpsKoordinatenStartzeit, String startZeitVerkehrsmittel, String haltestelleStartzeit,
                                    String gpsKoordinateEinstieg, String ausstiegHaltestelleUmstieg, String verkehrsmittelUmstieg,
                                    String umsteigenAnkunfthaltestelle, String umsteigenStartzeitFahrt, String gpsKoordinatenUmsteigen, String beschreibungProblem,
                                    String problemZeit, String gpsKoordinatenProblemString, String endzeitFahrt,
                                    String ankunftszeitZiel, String ankunftHaltestelleZiel, String gpsKoordinatenZiel, String umfrageAntwort1, String umfrageAntwort2, String umfrageAntwort3,
                                    String umfrageAntwort4, String umfrageAntwort5, String umfrageAntwort6, String umfrageAntwort7, String datum,
                                    String userId){

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        //Dokument und Photo Id
        String idNeu = UUID.randomUUID().toString();

        //Daten hochladen
        firebaseHelper.fahrtInFirebaseSpeichern(startzeit, gewuenschteAnkunftszeit, ankunftHaltestelle, startzeitFahrt, gpsKoordinatenStartzeit, startZeitVerkehrsmittel, haltestelleStartzeit,
                gpsKoordinateEinstieg, ausstiegHaltestelleUmstieg, verkehrsmittelUmstieg, umsteigenAnkunfthaltestelle, umsteigenStartzeitFahrt, gpsKoordinatenUmsteigen,
                beschreibungProblem, problemZeit, gpsKoordinatenProblemString, endzeitFahrt, ankunftszeitZiel, ankunftHaltestelleZiel, gpsKoordinatenZiel, umfrageAntwort1, umfrageAntwort2,
                umfrageAntwort3, umfrageAntwort4, umfrageAntwort5, umfrageAntwort6, umfrageAntwort7, datum, userId, idNeu);

        /*
        //Daten hochladen
        firebaseHelper.fahrtInFirebaseSpeichern(startzeit, gewuenschteAnkunftszeit, ankunftHaltestelle, startzeitFahrt,
                startZeitVerkehrsmittel, ausstiegHaltestelleUmstieg, verkehrsmittelUmstieg, haltestelleStartzeit, beschreibungProblem,
                umsteigenAnkunfthaltestelle, umsteigenStartzeitFahrt, endzeitFahrt, ankunftszeitZiel, umfrageAntwort1, umfrageAntwort2,
                umfrageAntwort3, umfrageAntwort4, umfrageAntwort5, umfrageAntwort6, umfrageAntwort7, datum, userId, idNeu);

         */


        if (photoProblem != null) {
            //Photos hochladen
            firebaseHelper.bildInFirebaseStorageSpeichern(photoProblem, "problem", idNeu);
        }
    }

    public String getUserIdFromFile(){

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("profileDir", Context.MODE_PRIVATE);

        if (directory.isDirectory())
        {
            String[] list = directory.list(new FilenameFilter()
            {
                @Override
                public boolean accept(File f, String s )
                {
                    return s.endsWith(".txt");
                }

            });
            if ( list.length > 0 )
            {

                String idWithExtension = list[0];

                String id = idWithExtension.substring(0, idWithExtension.length()-4);

                return id;
            }
        }

        return null;

    }



}