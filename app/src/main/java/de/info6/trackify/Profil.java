package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Profil extends AppCompatActivity {

    TextView editText_alter, editText_derzeitigeBeschaeftigung, editText_hauptverkehrsmittel;

    Button button_speichernUserProfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText_alter = findViewById(R.id.editText_alter);
        editText_derzeitigeBeschaeftigung = findViewById(R.id.editText_beschaeftigung);
        editText_hauptverkehrsmittel = findViewById(R.id.editText_hauptverkehrsmittel);

        button_speichernUserProfil = findViewById(R.id.button_speichernProfil);
    }
}