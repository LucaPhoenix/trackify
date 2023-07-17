package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.HashMap;
import java.util.UUID;

public class FahrtBeenden extends AppCompatActivity implements OpenTimePicker{

    TextView editText_endzeitFahrt, editText_ankunftZiel, editText_haltestelleZiel;

    Button button_speichernFahrtBeenden, button_endzeitFahrt, button_ankunftZiel;

    Switch switch_fahrtExportieren;
    HashMap<String, Object> collectedData = new HashMap<>();

    double latDouble, lonDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrt_beenden);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Edit Text und Button initialisieren
        editText_endzeitFahrt = findViewById(R.id.editText_endzeitFahrt);
        editText_ankunftZiel = findViewById(R.id.editText_ankunftZiel);
        editText_haltestelleZiel = findViewById(R.id.editText_HaltestelleZiel);

        button_speichernFahrtBeenden = findViewById(R.id.button_speichernFahrtBeenden);
        button_endzeitFahrt = findViewById(R.id.button_endzeitFahrt);
        button_ankunftZiel = findViewById(R.id.button_ankunftZiel);

        switch_fahrtExportieren = findViewById(R.id.switch_fahrtExportieren);

        //Intent abrufen
        Intent intentStart = getIntent();

        if(intentStart != null){
            collectedData = (HashMap<String, Object>) getIntent().getSerializableExtra("CollectedData");
            if(collectedData != null) {
                Log.d("----TAG", collectedData.toString());
            }
        }


        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, null, new PermissionHandler() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGranted() {
                //Toast.makeText(FahrtBeenden.this, "Permission Granted", Toast.LENGTH_SHORT).show();

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




        button_speichernFahrtBeenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_endzeitFahrt.getText().toString() == getResources().getString(R.string.text_zeitWaehlen)
                        && editText_ankunftZiel.getText().toString().equals(getResources().getString(R.string.text_zeitWaehlen))){
                    Toast.makeText(FahrtBeenden.this,  "Bitte machen Sie die Zeitangaben, in dem Sie auf ZEIT AUSWÄHLEN drücken.", Toast.LENGTH_LONG).show();
                } else if(editText_haltestelleZiel.getText().toString().isEmpty()){
                    Toast.makeText(FahrtBeenden.this,  "Bitte geben Sie einen Haltestellennamen an.", Toast.LENGTH_LONG).show();
                } else {
                    boolean exportTrue = false;

                    if (switch_fahrtExportieren.isChecked()) {
                        exportTrue = true;
                    }

                    collectedData.put("Ankunft Haltestelle Ziel", editText_endzeitFahrt.getText().toString());
                    collectedData.put("Ankunftszeit Ziel", editText_ankunftZiel.getText().toString());
                    collectedData.put("Haltestellenname Ziel", editText_haltestelleZiel.getText().toString());
                    collectedData.put("KoordinatenEnde", lonDouble + ", " + latDouble);
                    Intent intent = new Intent(FahrtBeenden.this, Umfrage.class);
                    intent.putExtra("exportTrue", exportTrue);
                    intent.putExtra("CollectedData", collectedData);
                    startActivity(intent);
                }
            }
        });

        button_endzeitFahrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimePicker.openTimePickerDialog(FahrtBeenden.this, editText_endzeitFahrt);
            }
        });

        button_ankunftZiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimePicker.openTimePickerDialog(FahrtBeenden.this, editText_ankunftZiel);
            }
        });
    }
}