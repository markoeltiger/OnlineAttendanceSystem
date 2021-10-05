package com.example.tripa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.List;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    List<PhotoMetadata> tripmetadata ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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





