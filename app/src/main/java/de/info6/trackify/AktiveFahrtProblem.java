package de.info6.trackify;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;

public class AktiveFahrtProblem extends AppCompatActivity {


    private static final int GalleryPick = 1;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    String cameraPermission[];
    String storagePermission[];

    double latDouble, lonDouble;


    TextView editText_problemBeschreibung;

    ImageView imageView_Problem;

    Button button_speichernProblem;

    Bitmap bitmapProblem;

    HashMap<String, Object> collectedData = new HashMap<>();
    HashMap<String, String> problemBeschreibung = new HashMap<>();
    HashMap<String, String> problemZeit = new HashMap<>();
    HashMap<String, String> problemKoordinaten = new HashMap<>();
    int problemCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktive_fahrt_problem);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //EditText und Button initialisieren
        editText_problemBeschreibung = findViewById(R.id.editText_problemBeschreibung);
        button_speichernProblem = findViewById(R.id.button_speichernProblem);

        //ImageView initialisieren
        imageView_Problem = findViewById(R.id.imageView_Problem);

        //Intent abrufen
        Intent intentStart = getIntent();

        if(intentStart != null){
            collectedData = (HashMap<String, Object>) getIntent().getSerializableExtra("CollectedData");
            if(collectedData != null) {
                Log.d("----TAG", collectedData.toString());
                if(collectedData.containsKey("Problem Beschreibung")){
                    problemBeschreibung = (HashMap<String, String>) collectedData.get("Problem Beschreibung");
                    problemZeit = (HashMap<String, String>) collectedData.get("Problem Zeit");
                    problemKoordinaten = (HashMap<String, String>) collectedData.get("KoordinatenProblem");
                    problemCounter = problemBeschreibung.size() +1;
                }
                else{
                    problemCounter = 1;
                }
            }
        }

        button_speichernProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY), minutes = Calendar.getInstance().get(Calendar.MINUTE);
                String hour, minute, time;
                if(hours<10){
                    hour = "0" + String.valueOf(hours);
                }
                else {
                    hour = String.valueOf(hours);
                }
                if(minutes<10){
                    minute = "0" + String.valueOf(minutes);
                }
                else {
                    minute = String.valueOf(minutes);
                }
                time = hours + ":" + minutes;
                if(editText_problemBeschreibung.getText().toString().isEmpty()){
                    Toast.makeText(AktiveFahrtProblem.this,  "Bitte geben Sie eine Problembeschreibung ab.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(AktiveFahrtProblem.this, MainActivity.class);
                    intent.putExtra("aktiveFahrt", true);
                    problemBeschreibung.put("Problem" + problemCounter, editText_problemBeschreibung.getText().toString());
                    problemZeit.put("Problem" + problemCounter, time);
                    problemKoordinaten.put("Problem" + problemCounter, lonDouble + ", " + latDouble);

/*                    collectedData.put("Problem Beschreibung", editText_ankunftHaltestelle_umsteigen.getText().toString());
                    collectedData.put("Problem Zeit", time);
                    collectedData.put("KoordinatenProblem", lonDouble + ", " + latDouble);*/

                    collectedData.put("Problem Beschreibung", problemBeschreibung);
                    collectedData.put("Problem Zeit", problemZeit);
                    collectedData.put("KoordinatenProblem", problemKoordinaten);


                    if (bitmapProblem != null) {
                        //Bitmap to Byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmapProblem.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("photoProblem" + problemCounter, byteArray);
                    }
                    intent.putExtra("CollectedData", collectedData);
                    startActivity(intent);
                }
            }
        });

        imageView_Problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomething();
            }
        });

        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, null, new PermissionHandler() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGranted() {
                Toast.makeText(AktiveFahrtProblem.this, "Permission Granted", Toast.LENGTH_SHORT).show();

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





    }


    public void doSomething() {

        if (!checkStoragePermission()) {
            requestStoragePermission();
        } else {
            showImagePicDialog2();
        }

    }

    private void showImagePicDialog2() {
        String options[] = {"Kamera", "Gallerie"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wähl aus");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pickFromGallery();
            }
        });
        builder.create().show();
    }

    //Von Gallerie auswählen
    private void pickFromGallery() {
        CropImage.activity().start(AktiveFahrtProblem.this);
    }

    //Nach auswahl Bild darstellen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView_Problem.setImageURI(resultUri);

                bitmapProblem = ((BitmapDrawable) imageView_Problem.getDrawable()).getBitmap();

            }
        }

    }




    //Entsprechende Permissions überprüfen
    //Permission überprüfen
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(AktiveFahrtProblem.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AktiveFahrtProblem.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST);
        }

    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(AktiveFahrtProblem.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AktiveFahrtProblem.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        }
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }


}