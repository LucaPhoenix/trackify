package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NeueFahrtStartzeit extends AppCompatActivity {

    TextView editText_startzeit, editText_gewuenschteAnkunftszeit;

    Button button_speichernStartzeit;



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


        button_speichernStartzeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NeueFahrtStartzeit.this, MainActivity.class);
                intent.putExtra("aktiveFahrtHaltestelle", true);
                startActivity(intent);
            }
        });


    }
}