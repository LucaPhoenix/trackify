package de.info6.trackify;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    String id;

    public void dokumentInFirestoreSpeichern(String wert1, String wert2, String wert3, String wert4) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();



        //Dokument erstellen
        Map<String, Object> fahrt = new HashMap<>();
        fahrt.put("Startzeit", wert1);
        fahrt.put("Ankunft_Haltestelle", wert2);
        fahrt.put("Abfahrt_Straßenbahn", wert3);
        fahrt.put("Ankunft_Endhaltestelle", wert4);

        //Dokument speichern
        db.collection("fahrten").add(fahrt).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Firebase", "Dokumentupload erfolgreich mit ID: " + documentReference.getId());
                id = documentReference.getId();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Firebase", "Dokumentupload fehlgeschlagen", e);
            }
        });
    }

    public void bildInFirebaseStorageSpeichern(Bitmap photo){

    }
}