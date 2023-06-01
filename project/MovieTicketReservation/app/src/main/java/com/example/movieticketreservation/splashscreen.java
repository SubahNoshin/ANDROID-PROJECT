package com.example.movieticketreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class splashscreen extends AppCompatActivity {
    private ProgressBar simpleProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        simpleProgressBar = findViewById(R.id.simpleProgressBar);

        new CountDownTimer(5 * 800, 800) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int)(5 - millisUntilFinished/800)*20;
                simpleProgressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                SharedPreferences sp = getSharedPreferences("sp",MODE_PRIVATE);

                if(sp.getBoolean("isUserLoggedIn",false)){
                    startActivity(new Intent(splashscreen.this,HomepageUserActivity.class));
                }
                else {
                    Intent intent = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }.start();

    }

}
