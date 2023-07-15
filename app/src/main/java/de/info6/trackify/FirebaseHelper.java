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

    public void dokumenteInFirebaseSpeichern(String wert1, String wert2, String wert3, String wert4, String id) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();



        //Dokument erstellen
        Map<String, Object> fahrt = new HashMap<>();
        fahrt.put("Startzeit", wert1);
        fahrt.put("Ankunft_Haltestelle", wert2);
        fahrt.put("Abfahrt_Straßenbahn", wert3);
        fahrt.put("Ankunft_Endhaltestelle", wert4);

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
            pathString = id + "/" + id + "-Problem";

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