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

public class NeueFahrtStartzeit extends AppCompatActivity {

    TextView editText_startzeit, editText_gewuenschteAnkunftszeit;

    Button button_speichernStartzeit;

    double latDouble, lonDouble;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neue_fahrt_startzeit);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //EditText & Button initialisieren
        editText_startzeit = findViewById(R.id.editText_startzeit);
        editText_gewuenschteAnkunftszeit = findViewById(R.id.editText_gewuenschteAnkunftszeit);

        button_speichernStartzeit = findViewById(R.id.button_speichernStartzeit);


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

                String startzeit = editText_startzeit.getText().toString().trim();
                String gewuenschteAnkunftszeit = editText_gewuenschteAnkunftszeit.getText().toString().trim();

                if (!startzeit.isEmpty() && !gewuenschteAnkunftszeit.isEmpty()) {
                    Intent intent = new Intent(NeueFahrtStartzeit.this, MainActivity.class);
                    intent.putExtra("aktiveFahrtHaltestelle", true);
                    intent.putExtra("startzeit", startzeit);
                    intent.putExtra("gewuenschteAnkunftszeit", gewuenschteAnkunftszeit);
                    intent.putExtra("latitudeNeueFahrtStartzeit", latDouble);
                    intent.putExtra("longitudeNeueFahrtStartzeit", lonDouble);
                    startActivity(intent);
                }
            }
        });


    }
}