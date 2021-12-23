package com.example.tripa;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;


public class SecondFragment extends Fragment implements View.OnClickListener {
    String  nameofTrip, tripdesc, type, tripto, tripfrom;
    String tripdate;
    List<PhotoMetadata> tripmetadata;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText editTextTextPersonName, tripdescc;
    Model Trip;
    AlarmManager alarmManager;
    Button seletdateandtime, go;
    PlacesClient placesClient;
    TextView datetimedisply;
    int randomId;
    String[] types = {"Choose Trip Type", "One Direction",
            "Round Trip ",};
    final Calendar calendar = Calendar.getInstance();
    TripReminder tripReminder;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);


        String apiKey = getString(R.string.apikey);
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), apiKey);
        }
        placesClient = Places.createClient(getContext());
        // Initialize the AutocompleteSupportFragment.
        editTextTextPersonName = (EditText) view.findViewById(R.id.editTextTextPersonName);
        tripdescc = (EditText) view.findViewById(R.id.tripdesc);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteSupportFragment autocompleteFragment1 = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.searchView2);
        autocompleteFragment1.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment1.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS));
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(getContext(), place.getName(), Toast.LENGTH_SHORT).show();
                tripto = place.getName();

            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });
        autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(getContext(), place.getName(), Toast.LENGTH_SHORT).show();
                tripfrom = place.getName();
                tripmetadata = place.getPhotoMetadatas();
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });
        SharedPreferences sharedpreferences;
        datetimedisply = (TextView) view.findViewById(R.id.datetimedesplay);
        Spinner spinner;
        sharedpreferences = getContext().getSharedPreferences("Preferences", 0);
        String login = sharedpreferences.getString("LOGIN", null);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter ad
                = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, types);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(types[position] + "hii");
                type = types[position];
                randomId = (int) Math.floor(Math.random() * (10000 - 100 + 1) + 100);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        seletdateandtime = (Button) view.findViewById(R.id.seletdateandtime);
        seletdateandtime.setOnClickListener(this);
        seletdateandtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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
                                datetimedisply.setText(simpleDateFormat.format(calendar.getTime()));
                                System.out.println(simpleDateFormat.format(calendar.getTime()));
                                tripdate = simpleDateFormat.format(calendar.getTime());
                            }
                        };

                        new TimePickerDialog(getActivity(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
                    }
                };

                new DatePickerDialog(getActivity(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }


        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");


        Trip = new Model();
        go = (Button) view.findViewById(R.id.go);
        go.setOnClickListener(this);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("goisclicked");


                nameofTrip = editTextTextPersonName.getText().toString();
                tripdesc = tripdescc.getText().toString();
                Trip.setTrip_date(tripdate);
                Trip.setTrip_desc(tripdesc);
                Trip.setTrip_from(tripfrom);
                Trip.setTrip_to(tripto);
                Trip.setTrip_name(nameofTrip);
                String items = "[ ] Send meeting notes to team\n" +
                        "[x] Advertise holiday home\n" +
                        "[x] Wish Sarah happy birthday";
                Trip.setTrip_notes(items);
                String tripid = Integer.toString(randomId);
                Trip.setTripid(tripid);
                SharedPreferences sharedpreferences;
                //important
                AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);

                sharedpreferences = getContext().getSharedPreferences("Preferences", 0);
                String login = sharedpreferences.getString("LOGIN", null);
                FirebaseDatabase.getInstance().getReference().child("Users");
                databaseReference = firebaseDatabase.getReference().child("Users").child(login);
                myRef.child(login).child("Trips").child(String.valueOf(randomId)).setValue(Trip);
                Intent sendDetails = new Intent(getActivity(), DetailsActivity.class);
                myRef.child(login).child("Trips").child(String.valueOf(randomId)).child("arrayphotometa").setValue(tripmetadata);
                sendDetails.putParcelableArrayListExtra("photometadata", (ArrayList<PhotoMetadata>) tripmetadata);
                sendDetails.putExtra("key", String.valueOf(randomId));
                sendDetails.putExtra("login", login);
                System.out.println(randomId + "mark");
                startActivity(sendDetails);

            }
        });
        return view;
    }


    @Override
    public void onClick(View v) {

        System.out.println("mark");
        final Calendar calendar = Calendar.getInstance();
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

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

                        System.out.println(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(getActivity(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(getActivity(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
private void setReminder(){
        tripReminder=new TripReminder(getContext(),calendar,alarmManager);

}

}
