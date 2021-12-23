package com.example.tripa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class DetailsActivity extends AppCompatActivity {
    List<PhotoMetadata> tripmetadata ;
    MaterialChecklist checklist;
    SharedPreferences sharedpreferences;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText nameedx , toedx,fromedx,descedx;
    String  names ,  tos ,  froms, descs ,items,dates;
    TextView tripdate;
    Button saveBTN,editDate;
      Model Trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        checklist =(MaterialChecklist)findViewById(R.id.checklist);
        nameedx = (EditText) findViewById(R.id.tripname);
        descedx = (EditText) findViewById(R.id.tripdesc);
        toedx = (EditText) findViewById(R.id.tripto);
        fromedx = (EditText) findViewById(R.id.tripfrome);
        saveBTN=(Button)findViewById(R.id.savebtn);
        tripdate = (TextView) findViewById(R.id.tripdate);
        editDate=(Button)findViewById(R.id.editdate);
        Intent intent = getIntent();
        String id = intent.getStringExtra("key");
        final Calendar calendar = Calendar.getInstance();
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
                String mTo =snapshot.child("trip_to").getValue().toString();
                String mfrom =snapshot.child("trip_from").getValue().toString();
                froms=mfrom;
                tos=mTo ;

                String  mdate=snapshot.child("trip_date").getValue().toString();
                System.out.println("mname "+mname);
                nameedx.setText(mname);
                descedx.setText(mdesc);
                toedx.setText(mTo);
                fromedx.setText(mfrom);
                tripdate.setText(mdate);
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
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("mark2");
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd|HH:mm");
                                tripdate.setText(simpleDateFormat.format(calendar.getTime()));
                                System.out.println(simpleDateFormat.format(calendar.getTime()));
                                dates = simpleDateFormat.format(calendar.getTime());
                            }
                        };

                        new TimePickerDialog(DetailsActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
                    }
                };
                new Handler().postDelayed(new Runnable(){

                    public void run() {
                        new DatePickerDialog(DetailsActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

                    }

                }, 100L);

            }
        });
        Trip = new Model();
   saveBTN.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {

           names = nameedx.getText().toString();
           descs = descedx.getText().toString();

           Trip.setTrip_date(dates);
           Trip.setTrip_desc(descs);
           Trip.setTrip_from(froms);
           Trip.setTrip_to(tos);
           Trip.setTrip_name(names);
             items = checklist.getItems(true,true);

           Trip.setTrip_notes(items);

           Trip.setTripid(id);
           SharedPreferences sharedpreferences;
           //important
           AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);

           sharedpreferences = getApplicationContext().getSharedPreferences("Preferences", 0);
           String login = sharedpreferences.getString("LOGIN", null);
           FirebaseDatabase.getInstance().getReference().child("Users");

           myRef.setValue(Trip);

           myRef.   child("arrayphotometa").setValue(tripmetadata);

       System.out.println("DOne");




       }
   });










    }


    public void onError(@NonNull Status status) {
        // TODO: Handle the error.
        Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
    }}





