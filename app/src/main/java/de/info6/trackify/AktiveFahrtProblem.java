package de.info6.trackify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

public class AktiveFahrtProblem extends AppCompatActivity {


    private static final int GalleryPick = 1;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    String cameraPermission[];
    String storagePermission[];


    TextView editText_problemBeschreibung;

    ImageView imageView_Problem;

    Button button_speichernProblem;

    Bitmap bitmapProblem;

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

        button_speichernProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AktiveFahrtProblem.this, MainActivity.class);
                intent.putExtra("aktiveFahrt", true);
                startActivity(intent);
            }
        });

        imageView_Problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomething();
            }
        });


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