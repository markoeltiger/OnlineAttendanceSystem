package webviewgold.myappname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import worker8.com.github.radiogroupplus.RadioGroupPlus;

public class GradeChooser extends AppCompatActivity {
RadioGroupPlus groupPlus;
Button next;
String grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_chooser);
        groupPlus= (RadioGroupPlus)findViewById(R.id.radio_group);
        next=(Button)findViewById(R.id.next);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        SharedPreferences.Editor editor = pref.edit();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id =groupPlus.getCheckedRadioButtonId();
                switch (id){
                    case R.id.radio_button1:
                        System.out.println("one");
                        grade="one";
                        editor.putString("grade", "one");
                    case R.id.radio_button2:
                        System.out.println("two");
                        grade="two";
                        editor.putString("grade", "two");

                    case R.id.radio_button3:
                        System.out.println("grade");
                        grade="three";
                        editor.putString("grade", "three");

                }

                Intent toChooseType =new Intent(GradeChooser.this,TypeChooserActivity.class);
                toChooseType.putExtra("Grade",grade);
                editor.apply();
                startActivity(toChooseType);


            }
        });
    }
}