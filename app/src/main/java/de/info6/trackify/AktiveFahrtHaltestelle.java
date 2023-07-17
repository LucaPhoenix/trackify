package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.HashMap;

public class AktiveFahrtHaltestelle extends AppCompatActivity implements VerkehrsmittelSpinner{

    TextView editText_ankunftHaltestelle, editText_startzeitFahrt, editText_haltestellenname;

    Button button_speichernHaltestelle, button_startzeitFahrt, button_ankunftHaltestelle;

    Spinner dropdown_verkehrsmittel;
    String verkehrsmittel;

    HashMap<String, Object> collectedData = new HashMap<>();

    double latDouble, lonDouble;

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
        editText_haltestellenname = findViewById(R.id.editText_HaltestelleName);

        button_speichernHaltestelle = findViewById(R.id.button_speichernHaltestelle);
        button_startzeitFahrt = findViewById(R.id.button_startzeitFahrt);
        button_ankunftHaltestelle = findViewById(R.id.button_ankunftHaltestelle);

        //Spinner initialisieren
        dropdown_verkehrsmittel = findViewById(R.id.spinner_verkehrsmittel);

        //Intent abrufen
        Intent intentStart = getIntent();

        if(intentStart != null){
            collectedData = (HashMap<String, Object>) getIntent().getSerializableExtra("CollectedData");
            if(collectedData != null) {
                Log.d("----TAG", collectedData.toString());
            }
        }


        //Spinner befüllen
        VerkehrsmittelSpinner.FillSpinner(AktiveFahrtHaltestelle.this, dropdown_verkehrsmittel);



        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, null, new PermissionHandler() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGranted() {
                Toast.makeText(AktiveFahrtHaltestelle.this, "Permission Granted", Toast.LENGTH_SHORT).show();

                //Location Manager Instanz
                LocationManager locationManager = (LocationManager)
                        getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new MyLocationListener();


                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);

                latDouble = MyLocationListener.latitude;
                lonDouble = MyLocationListener.longitude;

            }


        });


        double latDouble = MyLocationListener.latitude;
        double lonDouble = MyLocationListener.longitude;



        button_speichernHaltestelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_startzeitFahrt.getText().toString() == getResources().getString(R.string.text_zeitWaehlen)
                        && editText_ankunftHaltestelle.getText().toString().equals(getResources().getString(R.string.text_zeitWaehlen))){
                    Toast.makeText(AktiveFahrtHaltestelle.this,  "Bitte machen Sie die Zeitangaben, in dem Sie auf ZEIT AUSWÄHLEN drücken.", Toast.LENGTH_LONG).show();
                } else if(editText_haltestellenname.getText().toString().isEmpty()){
                    Toast.makeText(AktiveFahrtHaltestelle.this,  "Bitte geben Sie einen Haltestellennamen an.", Toast.LENGTH_LONG).show();
                } else if (dropdown_verkehrsmittel.getSelectedItem().toString().equals("Bitte auswählen")) {
                    Toast.makeText(AktiveFahrtHaltestelle.this,  "Bitte geben Sie ein Verkehrsmittel an an.", Toast.LENGTH_LONG).show();
                } else{
                    collectedData.put("Ankunft Haltestelle", editText_ankunftHaltestelle.getText().toString());
                    collectedData.put("Haltestellenname", editText_haltestellenname.getText().toString());
                    collectedData.put("Startzeit Fahrt", editText_startzeitFahrt.getText().toString());
                    collectedData.put("Verkehrsmittel", dropdown_verkehrsmittel.getSelectedItem().toString());
                    collectedData.put("KoordinatenEinstieg", lonDouble + ", " + latDouble);
                    Intent intent = new Intent(AktiveFahrtHaltestelle.this, MainActivity.class);
                    intent.putExtra("aktiveFahrt", true);
                    intent.putExtra("CollectedData", collectedData);
                    startActivity(intent);
                }
            }
        });

        button_ankunftHaltestelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimePicker.openTimePickerDialog(AktiveFahrtHaltestelle.this, editText_ankunftHaltestelle);
            }
        });

        button_startzeitFahrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimePicker.openTimePickerDialog(AktiveFahrtHaltestelle.this, editText_startzeitFahrt);
            }
        });

    }
}