package de.info6.trackify;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {

    //Name der Felder im Firestoredokument für Fahrten
    public static final String feldStartzeit                    = "Startzeit";
    public static final String feldGewuenschteAnkunftszeit      = "GewuenschteAnkunftszeit";
    public static final String feldAnkunftHaltestelle           = "AnkunftHaltestelle";
    public static final String feldStartzeitFahrt               = "StartzeitFahrt";
    public static final String feldAnkunftHaltestelleUmstieg    = "AnkunftHaltestelleUmstieg";
    public static final String feldStartzeitFahrtUmstieg        = "StartzeitFahrtUmstieg";
    public static final String feldEndzeitFahrt                 = "EndzeitFahrt";
    public static final String feldAnkunftszeitZiel             = "AnkunftszeitZiel";
    public static final String feldUmfrageantwortFrage1         = "Umfrageantwort-Frage1";
    public static final String feldUmfrageantwortFrage2         = "Umfrageantwort-Frage2";
    public static final String feldUmfrageantwortFrage3         = "Umfrageantwort-Frage3";
    public static final String feldUmfrageantwortFrage4         = "Umfrageantwort-Frage4";
    public static final String feldUmfrageantwortFrage5         = "Umfrageantwort-Frage5";
    public static final String feldUmfrageantwortFrage6         = "Umfrageantwort-Frage6";
    public static final String feldUmfrageantwortFrage7         = "Umfrageantwort-Frage7";
    public static final String feldBeschreibungProblem          = "BeschreibungProblem";
    public static final String feldUserId                       = "UserId";


    //Name der Felder im Firestoredokument fürs Profil
    public static final String feldName = "Name";
    public static final String feldAlter = "Alter";
    public static final String feldBeschaeftigung = "Beschaeftigung";
    public static final String feldGeschlecht = "Geschlecht";
    public static final String feldEinkommen = "Einkommen";



    public void fahrtInFirebaseSpeichern(String startzeit, String gewuenschteAnkunftszeit, String ankunftHaltestelle, String startzeitFahrt,
                                         String beschreibungProblem, String umsteigenAnkunfthaltestelle, String umsteigenStartzeitFahrt, String endzeitFahrt,
                                         String ankunftszeitZiel, String umfrageAntwort1, String umfrageAntwort2, String umfrageAntwort3,
                                         String umfrageAntwort4, String umfrageAntwort5, String umfrageAntwort6, String umfrageAntwort7, String userId, String id) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Dokument erstellen
        Map<String, Object> fahrt = new HashMap<>();
        fahrt.put(feldStartzeit, startzeit);
        fahrt.put(feldGewuenschteAnkunftszeit, gewuenschteAnkunftszeit);
        fahrt.put(feldAnkunftHaltestelle, ankunftHaltestelle);
        fahrt.put(feldStartzeitFahrt, startzeitFahrt);
        fahrt.put(feldAnkunftHaltestelleUmstieg, umsteigenAnkunfthaltestelle);
        fahrt.put(feldStartzeitFahrtUmstieg, umsteigenStartzeitFahrt);
        fahrt.put(feldEndzeitFahrt, endzeitFahrt);
        fahrt.put(feldAnkunftszeitZiel, ankunftszeitZiel);
        fahrt.put(feldUmfrageantwortFrage1, umfrageAntwort1);
        fahrt.put(feldUmfrageantwortFrage2, umfrageAntwort2);
        fahrt.put(feldUmfrageantwortFrage3, umfrageAntwort3);
        fahrt.put(feldUmfrageantwortFrage4, umfrageAntwort4);
        fahrt.put(feldUmfrageantwortFrage5, umfrageAntwort5);
        fahrt.put(feldUmfrageantwortFrage6, umfrageAntwort6);
        fahrt.put(feldUmfrageantwortFrage7, umfrageAntwort7);
        fahrt.put(feldBeschreibungProblem, beschreibungProblem);
        fahrt.put(feldUserId, userId);

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

    public void profilInFirebaseSpeichern(String name, String alter, String beschaeftigung, String geschlecht, String einkommen, String id) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Dokument erstellen
        Map<String, Object> user = new HashMap<>();
        user.put(feldName, name);
        user.put(feldAlter, alter);
        user.put(feldBeschaeftigung, beschaeftigung);
        user.put(feldGeschlecht, geschlecht);
        user.put(feldEinkommen, einkommen);

        //Dokument speichern
        db.collection("users").document(id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Firebase", "Userupload erfolgreich");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Firebase", "Userupload fehlgeschlagen", e);
            }
        });

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

    public String[] getUserDataFromFirebase(String userId){


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

                        String vorNachname = String.valueOf(documentSnapshot.get(feldName));

                        userData[0] = String.valueOf(documentSnapshot.get(feldName));
                        userData[1] = String.valueOf(documentSnapshot.get(feldAlter));
                        userData[2] = String.valueOf(documentSnapshot.get(feldBeschaeftigung));
                        userData[3] = String.valueOf(documentSnapshot.get(feldGeschlecht));
                        userData[4] = String.valueOf(documentSnapshot.get(feldEinkommen));

                    } else {
                        Log.d("Firebase", "No such document");
                    }
                }
            }
        });

        userData[0] = "Max Mustermann";
        userData[1] = "29";
        userData[2] = "Informatiker";
        userData[3] = "Männlich";
        userData[4] = "3456,00";

        return userData;

    }


}