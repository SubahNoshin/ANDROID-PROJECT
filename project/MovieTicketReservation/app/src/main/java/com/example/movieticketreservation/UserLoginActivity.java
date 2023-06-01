package com.example.movieticketreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLoginActivity extends AppCompatActivity {

    private EditText email,password,username1;
    private TextView forgotpass,register;
    private Button login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        forgotpass=findViewById(R.id.forgotpass);
        register=findViewById(R.id.register);
        login=findViewById(R.id.login);
        username1=findViewById(R.id.username1);
        auth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtxt=email.getText().toString().trim();
                String new1mail=emailtxt.replace('.',',');
                String usernametxt=username1.getText().toString().trim();
                String pass=password.getText().toString().trim();
                auth.signInWithEmailAndPassword(emailtxt,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(UserLoginActivity.this, "log in successful", Toast.LENGTH_SHORT).show();
                         Intent intent=new Intent(UserLoginActivity.this, HomepageUserActivity.class);
                         SharedPreferences sp = getSharedPreferences("sp",MODE_PRIVATE);
                         SharedPreferences.Editor editor = sp.edit();
                         editor.putBoolean("isUserLoggedIn",true);
                         editor.apply();

                         intent.putExtra("mail",new1mail);
                         intent.putExtra("username",usernametxt);
                         startActivity(intent);
                     }
                     else
                     {
                         Toast.makeText(UserLoginActivity.this, "invalid user", Toast.LENGTH_SHORT).show();
                     }
                    }

                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserLoginActivity.this,UserRegistration.class);
                startActivity(intent);
            }
        });
    }
}