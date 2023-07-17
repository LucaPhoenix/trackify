package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class Middleactivity extends AppCompatActivity {

    Button button_probleme, button_fahrtBeenden, button_umsteigen;

    HashMap<String, Object> collectedData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middleactivity);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Buttons initialisieren
        button_probleme = findViewById(R.id.button_problem);
        button_umsteigen = findViewById(R.id.button_umsteigen);
        button_fahrtBeenden = findViewById(R.id.button_fahrtBeenden);

        //Intent abrufen
        Intent intentStart = getIntent();

        if(intentStart != null){
            collectedData = (HashMap<String, Object>) getIntent().getSerializableExtra("CollectedData");
            if(collectedData != null) {
                Log.d("----TAG", collectedData.toString());
            }
        }


        button_probleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Middleactivity.this, AktiveFahrtProblem.class);
                intent.putExtra("CollectedData", collectedData);
                startActivity(intent);
            }
        });

        button_umsteigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Middleactivity.this, Umsteigen.class);
                intent.putExtra("CollectedData", collectedData);
                startActivity(intent);
            }
        });

        button_fahrtBeenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Middleactivity.this, FahrtBeenden.class);
                intent.putExtra("CollectedData", collectedData);
                startActivity(intent);
            }
        });


    }
}