package com.example.movieticketreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//eta use korini

public class AdminLoginActivity extends AppCompatActivity {
    EditText v1,v2;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        v1=findViewById(R.id.v1);
        v2=findViewById(R.id.v2);
        button3=findViewById(R.id.button3);

         button3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String s1=v1.getText().toString();
                 String s2=v2.getText().toString();
                 if (s1.equals("ADMIN") && s2.equals("1234567")) {
                     Intent intent = new Intent(AdminLoginActivity.this, AdminProfileActivity.class);
                     startActivity(intent);
                 }
                 else
                 {
                     Toast.makeText(AdminLoginActivity.this, "INVALID ADMIN NAME OR PASS  ", Toast.LENGTH_SHORT).show();
                 }
             }
         });

    }

}