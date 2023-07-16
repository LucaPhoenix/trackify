package de.info6.trackify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.UUID;

public class Profil extends AppCompatActivity {

    TextView editText_alter, editText_derzeitigeBeschaeftigung, editText_name, editText_einkommen;

    Spinner dropdown_geschlecht;

    Button button_speichernUserProfil;

    String[] dropdownGeschlechter = new String[]{"Bitte auswählen", "Männlich", "Weiblich", "Divers", "Keine Angabe"};

    String name, alter, beschäftigung, geschlecht, einkommen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //Intent abrufen
        Intent intentStart = getIntent();

        boolean firstStart = false;

        if(intentStart != null){
            firstStart = intentStart.getBooleanExtra("firstStart", false);
        }


        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        //Nur Zurück bei Toolbar, wenn nicht das erste Mal nach installation app geöffnet
        if (!firstStart) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //EditText initialisieren
        editText_alter = findViewById(R.id.editText_alter);
        editText_derzeitigeBeschaeftigung = findViewById(R.id.editText_beschaeftigung);
        editText_name = findViewById(R.id.editText_name);
        editText_einkommen = findViewById(R.id.editText_einkommen);
        button_speichernUserProfil = findViewById(R.id.button_speichernProfil);

        //Spinner initialisieren
        dropdown_geschlecht = findViewById(R.id.spinner_geschlecht);

        //Spinner befüllen
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dropdownGeschlechter);
        dropdown_geschlecht.setAdapter(adapter);

        dropdown_geschlecht.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                geschlecht = dropdownGeschlechter[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });



        button_speichernUserProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = editText_name.getText().toString().trim();
                alter = editText_alter.getText().toString().trim();
                beschäftigung = editText_derzeitigeBeschaeftigung.getText().toString().trim();
                einkommen = editText_einkommen.getText().toString().trim();


                if (!name.isEmpty()){
                    save();
                    Intent intent = new Intent(Profil.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Profil.this,  "Bitte geben Sie ihren Vor- und Nachnamen an.", Toast.LENGTH_LONG).show();
                }

            }
        });

        loadProfile();




    }

    private void loadProfile(){
        FirebaseHelper firebaseHelper = new FirebaseHelper();

        String userId = getUserIdFromFile();

        if (userId != null) {
            /*
            String[] userData = firebaseHelper.getUserDataFromFirebase(userId);

            editText_name.setText(userData[0]);
            editText_alter.setText(userData[1]);
            editText_derzeitigeBeschaeftigung.setText(userData[2]);
            editText_einkommen.setText(userData[4]);

            switch (userData[3]){
                case "Bitte auswählen":
                    dropdown_geschlecht.setSelection(0);
                    break;
                case "Männlich":
                    dropdown_geschlecht.setSelection(1);
                    break;
                case "Weiblich":
                    dropdown_geschlecht.setSelection(2);
                    break;
                case "Divers":
                    dropdown_geschlecht.setSelection(3);
                    break;
                case "Keine Angabe":
                    dropdown_geschlecht.setSelection(4);
                    break;
                default:
                    dropdown_geschlecht.setSelection(0);
                    break;
            }

             */

            String[] userData = new String[5];

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference documentReference = db.collection("users").document(userId);

            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        if (documentSnapshot.exists()) {
                            Log.d("Firebase", "DocumentSnapshot data: " + documentSnapshot.getData());

                            String nameIntern = String.valueOf(documentSnapshot.get(FirebaseHelper.feldName));
                            String alterIntern = String.valueOf(documentSnapshot.get(FirebaseHelper.feldAlter));
                            String beschaeftigungIntern = String.valueOf(documentSnapshot.get(FirebaseHelper.feldBeschaeftigung));
                            String geschlechtIntern = String.valueOf(documentSnapshot.get(FirebaseHelper.feldGeschlecht));
                            String einkommenIntern = String.valueOf(documentSnapshot.get(FirebaseHelper.feldEinkommen));

                            editText_name.setText(nameIntern);
                            editText_alter.setText(alterIntern);
                            editText_derzeitigeBeschaeftigung.setText(beschaeftigungIntern);
                            editText_einkommen.setText(einkommenIntern);

                            switch (geschlechtIntern){
                                case "Bitte auswählen":
                                    dropdown_geschlecht.setSelection(0);
                                    break;
                                case "Männlich":
                                    dropdown_geschlecht.setSelection(1);
                                    break;
                                case "Weiblich":
                                    dropdown_geschlecht.setSelection(2);
                                    break;
                                case "Divers":
                                    dropdown_geschlecht.setSelection(3);
                                    break;
                                case "Keine Angabe":
                                    dropdown_geschlecht.setSelection(4);
                                    break;
                                default:
                                    dropdown_geschlecht.setSelection(0);
                                    break;
                            }


                        } else {
                            Log.d("Firebase", "No such document");
                        }
                    }
                }
            });




        }




    }

    private void save(){

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        String id = UUID.randomUUID().toString();

        saveUserIdInFile(id);

        firebaseHelper.profilInFirebaseSpeichern(name, alter, beschäftigung, geschlecht, einkommen, id);

    }


    public String getUserIdFromFile(){

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("profileDir", Context.MODE_PRIVATE);

            if (directory.isDirectory())
            {
                String[] list = directory.list(new FilenameFilter()
                {
                    @Override
                    public boolean accept(File f, String s )
                    {
                        return s.endsWith(".txt");
                    }

                });
                if ( list.length > 0 )
                {

                    String idWithExtension = list[0];

                    String id = idWithExtension.substring(0, idWithExtension.length()-4);

                    return id;
                }
            }

            return null;

    }

    private void saveUserIdInFile(String id){

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("profileDir", Context.MODE_PRIVATE);

        //Delete existing User Profile File
        String previousFile = getUserIdFromFile();
        if (previousFile != null){
            previousFile = previousFile + ".txt";

            File file = new File(directory, previousFile);
            boolean deleted = file.delete();
        }

        File file = new File(directory, id + ".txt");
        Log.d("path", file.toString());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.flush();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}