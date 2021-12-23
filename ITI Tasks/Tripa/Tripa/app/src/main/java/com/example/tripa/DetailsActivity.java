package com.example.tripa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dvdb.materialchecklist.MaterialChecklist;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    List<PhotoMetadata> tripmetadata ;
    MaterialChecklist checklist;
    SharedPreferences sharedpreferences;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
EditText nameedx , to,from,descedx;
String mname , mto , mfrom,mdesc ,items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        checklist =(MaterialChecklist)findViewById(R.id.checklist);
       nameedx = (EditText) findViewById(R.id.tripname);
        descedx = (EditText) findViewById(R.id.tripdesc);
        Intent intent = getIntent();
        String id = intent.getStringExtra("key");

        System.out.println(id+"ley");
        sharedpreferences=getApplicationContext().getSharedPreferences("Preferences", 0);
        String login = sharedpreferences.getString("LOGIN", null);
System.out.println(login+"login");
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(login).child("Trips").child(id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mname = snapshot.child("trip_name").getValue().toString();
                String mdesc = snapshot.child("trip_desc").getValue().toString();
                String mnotes = snapshot.child("trip_notes").getValue().toString();

                System.out.println("mname "+mname);
                nameedx.setText(mname);
                descedx.setText(mdesc);
                checklist.setItems(mnotes);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

   //      myRef.child(login).child("Trips").child(id);

      //  checklist.setItems(items);
        String apiKey = getString(R.string.apikey);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        Intent i = getIntent();
        tripmetadata = (List<PhotoMetadata>) i.getSerializableExtra("photometadata");

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
        FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(Objects.requireNonNull(tripmetadata.get(0))).build();

        placesClient.fetchPhoto(photoRequest).addOnSuccessListener(
                new OnSuccessListener<FetchPhotoResponse>() {
                    @Override
                    public void onSuccess(FetchPhotoResponse response) {
                        Bitmap bitmap = response.getBitmap();
                        ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        exception.printStackTrace();
                    }
                });
    }


    public void onError(@NonNull Status status) {
        // TODO: Handle the error.
        Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
    }}





