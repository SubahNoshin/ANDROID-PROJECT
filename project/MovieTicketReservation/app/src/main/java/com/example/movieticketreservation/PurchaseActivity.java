package com.example.movieticketreservation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.example.movieticketreservation.classes.EachTicket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class PurchaseActivity extends AppCompatActivity {

    private ImageView ivImage;
    private TextInputEditText editTextTheatre, editTextMovie,editTextPrice;
    private TextView tvMovieDate, tvMovieTime,tvSeat;
    private Button buttonAdd;
    private EachTicket eachTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_ticket);

        ivImage = findViewById(R.id.iv_image);
        editTextTheatre = findViewById(R.id.editTextTheatre);
        editTextMovie = findViewById(R.id.editTextMovie);
        editTextPrice=findViewById(R.id.editTextPrice);
        tvMovieDate = findViewById(R.id.tvMovieDate);
        tvMovieTime = findViewById(R.id.tvMovieTime);
        tvSeat = findViewById(R.id.tvMovieSeat);
        buttonAdd = findViewById(R.id.buttonAddTicket);


        boolean disable = getIntent().getBooleanExtra("disable",false);
        eachTicket = (EachTicket)getIntent().getSerializableExtra("ticket");

        if(disable){
            buttonAdd.setVisibility(View.GONE);
        }
        else{
            buttonAdd.setVisibility(View.VISIBLE);
        }
        showData();





        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTicket();
            }
        });



    }

    private void bookTicket(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Saving ticket info...");
        dialog.setTitle("Adding ticket");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String uid = user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("booked")
                .child(uid);
        HashMap<String,Object> map = new HashMap<>();
        map.put("seat",String.valueOf(tvSeat.getText()));
        map.put("ticket",eachTicket);
        ref.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Constant.showSafeToast(PurchaseActivity.this,"Ticket booked successfully");
                finish();
            }
        });
    }


    private void showData(){
        if(eachTicket == null) return;


        Glide.with(this).load(eachTicket.getImage()).error(R.drawable.ic_baseline_photo_camera_back_24).into(ivImage);
        editTextTheatre.setText(eachTicket.getTheatreName());
        editTextMovie.setText(eachTicket.getMovieName());
        editTextPrice.setText(eachTicket.getPrice());
        tvMovieDate.setText(eachTicket.getDate());
        tvMovieTime.setText(eachTicket.getTime());

        String ab = "ABCD";
        int id = new Random().nextInt(10);

        tvSeat.setText(id+""+ab.charAt(id%4));
    }


}