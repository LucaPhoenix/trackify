package de.info6.trackify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Middleactivity extends AppCompatActivity {

    Button button_probleme, button_fahrtBeenden, button_umsteigen;

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


        button_probleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Middleactivity.this, AktiveFahrtProblem.class);
                startActivity(intent);
            }
        });

        button_umsteigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Middleactivity.this, MainActivity.class);
                intent.putExtra("aktiveFahrtHaltestelle", true);
                startActivity(intent);
            }
        });

        button_fahrtBeenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Middleactivity.this, FahrtBeenden.class);
                startActivity(intent);
            }
        });


    }
}