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

public class AktiveFahrtHaltestelle extends AppCompatActivity {

    TextView editText_ankunftHaltestelle, editText_startzeitFahrt;

    Button button_speichernHaltestelle;

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

        button_speichernHaltestelle = findViewById(R.id.button_speichernHaltestelle);


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
                Intent intent = new Intent(AktiveFahrtHaltestelle.this, MainActivity.class);
                intent.putExtra("aktiveFahrt", true);
                startActivity(intent);
            }
        });

    }
}