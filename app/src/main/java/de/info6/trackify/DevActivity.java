package de.info6.trackify;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DevActivity extends AppCompatActivity {

    Button gmfLvl1, gmfLvl2, gmfLvl3, gmfLvl4, gmfLvl5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_activity);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Development Bereich");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gmfLvl1 = findViewById(R.id.devActivityButton_gameificationLvl1);
        gmfLvl2 = findViewById(R.id.devActivityButton_gameificationLvl2);
        gmfLvl3 = findViewById(R.id.devActivityButton_gameificationLvl3);
        gmfLvl4 = findViewById(R.id.devActivityButton_gameificationLvl4);
        gmfLvl5 = findViewById(R.id.devActivityButton_gameificationLvl5);



        gmfLvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String PREFS_NAME = "MyPrefsFile";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                int gameificationLvl = settings.getInt("gameification", 0);
                settings.edit().putInt("gameification", 0).commit();
            }
        });

        gmfLvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String PREFS_NAME = "MyPrefsFile";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                int gameificationLvl = settings.getInt("gameification", 0);
                settings.edit().putInt("gameification", 5).commit();
            }
        });

        gmfLvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String PREFS_NAME = "MyPrefsFile";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                int gameificationLvl = settings.getInt("gameification", 0);
                settings.edit().putInt("gameification", 10).commit();
            }
        });

        gmfLvl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String PREFS_NAME = "MyPrefsFile";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                int gameificationLvl = settings.getInt("gameification", 0);
                settings.edit().putInt("gameification", 15).commit();
            }
        });

        gmfLvl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String PREFS_NAME = "MyPrefsFile";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                int gameificationLvl = settings.getInt("gameification", 0);
                settings.edit().putInt("gameification", 20).commit();
            }
        });



    }
}
