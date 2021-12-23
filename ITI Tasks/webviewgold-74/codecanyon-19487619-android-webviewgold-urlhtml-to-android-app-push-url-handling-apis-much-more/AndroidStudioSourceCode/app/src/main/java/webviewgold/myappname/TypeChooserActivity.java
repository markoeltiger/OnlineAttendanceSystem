package webviewgold.myappname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class TypeChooserActivity extends AppCompatActivity {
String type;
ProgressBar progressBar;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_chooser);
        pref  = getApplicationContext().getSharedPreferences("MyPref", 0);



        progressBar = findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);

    }


    public void onlineClicked(View view) {
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences.Editor editor = pref.edit();
        Intent myInt =new Intent(TypeChooserActivity.this,MainActivity.class);
        type="online";
        editor.putString("type",type);
        editor.putString("done","done");
editor.apply();
        myInt.putExtra("mtype",type);
        startActivity(myInt);


    }

    public void centerClicked(View view) {
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences.Editor editor = pref.edit();

        Intent myInt =new Intent(TypeChooserActivity.this,MainActivity.class);
        type="center";
        editor.putString("type",type);
        editor.putString("done","done");
        editor.apply();
        myInt.putExtra("mtype",type);
        startActivity(myInt);

    }
}