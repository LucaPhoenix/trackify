package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AktiveFahrtProblem extends AppCompatActivity {

    TextView editText_problemBeschreibung;

    Button button_speichernProblem;

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

        button_speichernProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AktiveFahrtProblem.this, MainActivity.class);
                intent.putExtra("aktiveFahrtHaltestelle", true);
                startActivity(intent);
            }
        });


    }
}