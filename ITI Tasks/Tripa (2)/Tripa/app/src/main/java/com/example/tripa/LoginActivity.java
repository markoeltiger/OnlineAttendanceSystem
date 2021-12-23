package com.example.tripa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText edtTxtEmail;
    private EditText edtTxtPassword ;
    private Button btnLogin ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        initComponent();
        mAuth = FirebaseAuth.getInstance();
          SharedPreferences sharedpreferences;

        sharedpreferences=getApplicationContext().getSharedPreferences("Preferences", 0);
        String login = sharedpreferences.getString("LOGIN", null);

        if (login != null) {
            Intent secInt = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(  secInt);
        }
        else {

// to go login activity
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = edtTxtEmail.getText().toString().trim();
                String password = edtTxtPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    edtTxtEmail.setError("Email is Required");
                    edtTxtEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtTxtEmail.setError("Please provide valid email");
                    edtTxtEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    edtTxtPassword.setError("Password is Required");
                    edtTxtPassword.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){
                            Intent homeInt = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(  homeInt);
                            System.out.println(mAuth.getCurrentUser().getUid()+"1");
                            System.out.println(mAuth.getUid()+"2");
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("LOGIN", mAuth.getUid() );
                            editor.apply();
                            homeInt.putExtra("uid",mAuth.getUid());
                        }else{
                            Toast.makeText(getApplicationContext(),"Failed to login! please check your email and password" ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void initComponent(){
        edtTxtEmail=findViewById(R.id.editTextEmail);
        edtTxtPassword=findViewById(R.id.editTextPassword);
        btnLogin=findViewById(R.id.cirLoginButton);
    }
}