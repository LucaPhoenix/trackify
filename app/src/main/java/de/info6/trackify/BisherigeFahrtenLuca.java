package de.info6.trackify;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BisherigeFahrtenLuca extends AppCompatActivity {

    ArrayList<String> bisherigeFahrtenListe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bisherige_fahrten_luca);

        //Toolbar initialisieren
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getData();



    }


    protected void setBisherigeFahrtenListe(String einfuegen){
        this.bisherigeFahrtenListe.add(einfuegen);
    }

    protected void createAdapterAndListView(){
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bisherigeFahrtenListe);
        ListView listView = (ListView) findViewById(R.id.listview_bisherigeFahrten);
        listView.setAdapter(itemsAdapter);
    }

    protected void getData() {
        bisherigeFahrtenListe.clear();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth;
        FirebaseUser currentUser;

        String uid = null;

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            uid = currentUser.getUid();
        }

        db.collection("fahrten").whereEqualTo(FirebaseHelper.feldUserIdFirebase, uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int i = 0;


                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        int size = task.getResult().size();

                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(i);

                        String haltestelleStart = documentSnapshot.getString(FirebaseHelper.feldStartzeitHaltestelle);
                        //String haltestelleEnde = documentSnapshot.getString(FirebaseHelper.feldAussteigenHaltestelleUmsteigen);
                        String datumFahrt = String.valueOf(documentSnapshot.get(FirebaseHelper.feldDatum));


                        String einfuegen = "Start: " + haltestelleStart + "\nDatum: " + datumFahrt;
                        //String einfuegen = "Start: " + haltestelleStart + "\nEnde: " + haltestelleEnde + "\nDatum: " + datumFahrt;

                        setBisherigeFahrtenListe(einfuegen);

                    }

                    createAdapterAndListView();

                }
            }
        });


    }



}
