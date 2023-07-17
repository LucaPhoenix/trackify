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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;


import java.util.HashMap;

public class NeueFahrtStartzeit extends AppCompatActivity implements OpenTimePicker {

    TextView editText_startzeit, editText_gewuenschteAnkunftszeit;


    double latDouble, lonDouble;


    Button button_speichernStartzeit, button_startzeit, button_gewuenschteAnkunftszeit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HashMap<String, Object> collectedData = new HashMap<>();
        setContentView(R.layout.activity_neue_fahrt_startzeit);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TextClock & Button initialisieren
        editText_startzeit = findViewById(R.id.editText_startzeit);
        editText_gewuenschteAnkunftszeit = findViewById(R.id.editText_gewuenschteAnkunftszeit);

        button_speichernStartzeit = findViewById(R.id.button_speichernStartzeit);
        button_startzeit = findViewById(R.id.button_startzeit);
        button_gewuenschteAnkunftszeit = findViewById(R.id.button_gewuenschteAnkunftszeit);


        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, null, new PermissionHandler() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGranted() {
                Toast.makeText(NeueFahrtStartzeit.this, "Permission Granted", Toast.LENGTH_SHORT).show();

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


        button_speichernStartzeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_startzeit.getText().toString() != getResources().getString(R.string.text_zeitWaehlen)
                        && !editText_gewuenschteAnkunftszeit.getText().toString().equals(getResources().getString(R.string.text_zeitWaehlen))){
                    collectedData.put("Startzeit", editText_startzeit.getText().toString());
                    collectedData.put("Gewünschte Ankunftszeit", editText_gewuenschteAnkunftszeit.getText().toString());
                    collectedData.put("LatitudeStartzeit", latDouble);
                    collectedData.put("LongitudeStartzeit", lonDouble);
                    Intent intent = new Intent(NeueFahrtStartzeit.this, MainActivity.class);
                    intent.putExtra("aktiveFahrtHaltestelle", true);
                    intent.putExtra("CollectedData", collectedData);
                    startActivity(intent);
                } else {
                    Toast.makeText(NeueFahrtStartzeit.this,  "Bitte geben Sie beide Zeiten an, in dem Sie auf ZEIT AUSWÄHLEN drücken.", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_startzeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimePicker.openTimePickerDialog(NeueFahrtStartzeit.this, editText_startzeit);
            }
        });

        button_gewuenschteAnkunftszeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimePicker.openTimePickerDialog(NeueFahrtStartzeit.this, editText_gewuenschteAnkunftszeit);
            }
        });
    }
}