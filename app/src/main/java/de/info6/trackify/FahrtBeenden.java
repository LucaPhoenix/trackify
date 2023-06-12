package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FahrtBeenden extends AppCompatActivity {

    TextView editText_endzeitFahrt, editText_ankunftZiel;

    Button button_speichernFahrtBeenden;

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

        button_speichernFahrtBeenden = findViewById(R.id.button_speichernFahrtBeenden);

        button_speichernFahrtBeenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FahrtBeenden.this, Umfrage.class);
                startActivity(intent);
            }
        });
    }
}