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

public class Umsteigen extends AppCompatActivity implements OpenTimePicker {

    TextView editText_ankunftHaltestelle_umsteigen, editText_startzeitFahrt_umsteigen, editText_haltestellenname_umsteigen;

    Button button_speichernHaltestelle_umsteigen, button_startzeitFahrt_umsteigen, button_ankunftHaltestelle_umsteigen;

    Spinner dropdown_verkehrsmittel_umsteigen;

    HashMap<String, Object> collectedData = new HashMap<>();

    double latDouble, lonDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umsteigen);
        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //EditText initialisieren
        editText_ankunftHaltestelle_umsteigen = findViewById(R.id.editText_ankunftHaltestelle_umsteigen);
        editText_startzeitFahrt_umsteigen = findViewById(R.id.editText_startzeitFahrt_umsteigen);
        editText_haltestellenname_umsteigen = findViewById(R.id.editText_HaltestelleName_umsteigen);

        button_speichernHaltestelle_umsteigen = findViewById(R.id.button_speichernHaltestelle_umsteigen);
        button_startzeitFahrt_umsteigen = findViewById(R.id.button_startzeitFahrt_umsteigen);
        button_ankunftHaltestelle_umsteigen = findViewById(R.id.button_ankunftHaltestelle_umsteigen);

        //Spinner initialisieren
        dropdown_verkehrsmittel_umsteigen = findViewById(R.id.spinner_verkehrsmittel_umsteigen);

        //Intent abrufen
        Intent intentStart = getIntent();

        if(intentStart != null){
            collectedData = (HashMap<String, Object>) getIntent().getSerializableExtra("CollectedData");
            if(collectedData != null) {
                Log.d("----TAG", collectedData.toString());
            }
        }


        //Spinner befüllen
        VerkehrsmittelSpinner.FillSpinner(Umsteigen.this, dropdown_verkehrsmittel_umsteigen);



        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, null, new PermissionHandler() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGranted() {
                Toast.makeText(Umsteigen.this, "Permission Granted", Toast.LENGTH_SHORT).show();

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



        button_speichernHaltestelle_umsteigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_startzeitFahrt_umsteigen.getText().toString() == getResources().getString(R.string.text_zeitWaehlen)
                        && editText_ankunftHaltestelle_umsteigen.getText().toString().equals(getResources().getString(R.string.text_zeitWaehlen))){
                    Toast.makeText(Umsteigen.this,  "Bitte machen Sie die Zeitangaben, in dem Sie auf ZEIT AUSWÄHLEN drücken.", Toast.LENGTH_LONG).show();
                } else if(editText_haltestellenname_umsteigen.getText().toString().isEmpty()){
                    Toast.makeText(Umsteigen.this,  "Bitte geben Sie einen Haltestellennamen an.", Toast.LENGTH_LONG).show();
                } else if (dropdown_verkehrsmittel_umsteigen.getSelectedItem().toString().equals("Bitte auswählen")) {
                    Toast.makeText(Umsteigen.this,  "Bitte geben Sie ein Verkehrsmittel an an.", Toast.LENGTH_LONG).show();
                } else{
                    collectedData.put("Ankunft Haltestelle", editText_ankunftHaltestelle_umsteigen.getText().toString());
                    collectedData.put("Haltestellenname", editText_haltestellenname_umsteigen.getText().toString());
                    collectedData.put("Startzeit Fahrt", editText_startzeitFahrt_umsteigen.getText().toString());
                    collectedData.put("Verkehrsmittel", dropdown_verkehrsmittel_umsteigen.getSelectedItem().toString());
                    Intent intent = new Intent(Umsteigen.this, MainActivity.class);
                    intent.putExtra("aktiveFahrt", true);
                    intent.putExtra("CollectedData", collectedData);
                    startActivity(intent);
                }
            }
        });

        button_ankunftHaltestelle_umsteigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimePicker.openTimePickerDialog(Umsteigen.this, editText_ankunftHaltestelle_umsteigen);
            }
        });

        button_startzeitFahrt_umsteigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimePicker.openTimePickerDialog(Umsteigen.this, editText_startzeitFahrt_umsteigen);
            }
        });

    }
}