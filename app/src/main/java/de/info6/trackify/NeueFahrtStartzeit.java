package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NeueFahrtStartzeit extends AppCompatActivity implements OpenTimePicker {

    TextView editText_startzeit, editText_gewuenschteAnkunftszeit;

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


        button_speichernStartzeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectedData.put("Startzeit", editText_startzeit.getText().toString());
                collectedData.put("Gewünschte Ankunftszeit", editText_gewuenschteAnkunftszeit.getText().toString());
                Intent intent = new Intent(NeueFahrtStartzeit.this, MainActivity.class);
                intent.putExtra("aktiveFahrtHaltestelle", true);
                intent.putExtra("CollectedData", collectedData);
                startActivity(intent);
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