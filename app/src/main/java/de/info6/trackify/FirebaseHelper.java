package de.info6.trackify;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    String id;

    public void dokumenteInFirebaseSpeichern(String startzeit, String gewuenschteAnkunftszeit, String ankunftHaltestelle, String startzeitFahrt,
                                             String beschreibungProblem, String umsteigenAnkunfthaltestelle, String umsteigenStartzeitFahrt, String endzeitFahrt,
                                             String ankunftszeitZiel, String umfrageAntwort1, String umfrageAntwort2, String umfrageAntwort3,
                                             String umfrageAntwort4, String umfrageAntwort5, String umfrageAntwort6, String umfrageAntwort7, String id) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Dokument erstellen
        Map<String, Object> fahrt = new HashMap<>();
        fahrt.put("Startzeit", startzeit);
        fahrt.put("GewuenschteAnkunftszeit", gewuenschteAnkunftszeit);
        fahrt.put("AnkunftHaltestelle", ankunftHaltestelle);
        fahrt.put("StartzeitFahrt", startzeitFahrt);
        fahrt.put("AnkunftHaltestelleUmstieg", umsteigenAnkunfthaltestelle);
        fahrt.put("StartzeitFahrtUmstieg", umsteigenStartzeitFahrt);
        fahrt.put("EndzeitFahrt", endzeitFahrt);
        fahrt.put("AnkunftszeitZiel", ankunftszeitZiel);
        fahrt.put("Umfrageantwort-Frage1", umfrageAntwort1);
        fahrt.put("Umfrageantwort-Frage2", umfrageAntwort2);
        fahrt.put("Umfrageantwort-Frage3", umfrageAntwort3);
        fahrt.put("Umfrageantwort-Frage4", umfrageAntwort4);
        fahrt.put("Umfrageantwort-Frage5", umfrageAntwort5);
        fahrt.put("Umfrageantwort-Frage6", umfrageAntwort6);
        fahrt.put("Umfrageantwort-Frage7", umfrageAntwort7);
        fahrt.put("BeschreibungProblem", beschreibungProblem);

        //Dokument speichern
        db.collection("fahrten").document(id).set(fahrt).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Firebase", "Dokumentupload erfolgreich");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Firebase", "Dokumentupload fehlgeschlagen", e);
            }
        });

        /*
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

         */
    }

    public void bildInFirebaseStorageSpeichern(Bitmap bitmap, String whichPhoto, String id){

        //Profilbild speichern
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://trackify-d3c8a.appspot.com");
        StorageReference storageRef = storage.getReference();

        String pathString = "";

        if (whichPhoto.equals("einstieg")){

            pathString = "Fahrtaufnahmen/" + id + "/" + id + "-Einstieg";


        } else if (whichPhoto.equals("problem")) {
            pathString = "Fahrtaufnahmen/" + id + "/" + id + "-Problem";

        } else if (whichPhoto.equals("ausstieg")) {
            pathString = id + "/" + id + "-Ausstieg";

        }

        StorageReference pictureRef = storageRef.child(pathString);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = pictureRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Bildupload fehlgeschlagen");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("TAG", "Bildupload erfolgreich");
            }
        });

        try {
            baos.flush();
            baos.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}