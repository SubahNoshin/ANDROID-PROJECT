package com.example.movieticketreservation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.movieticketreservation.classes.EachTicket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AddTicketActivity extends AppCompatActivity {

    private ImageView ivImage;
    private TextInputEditText editTextTheatre, editTextMovie,editTextPrice;
    private TextView tvMovieDate, tvMovieTime;
    private Button buttonAdd;
    private ActivityResultLauncher<String> mGetContent;
    private Uri photoUri;
    int day, month, year;
    private Button buttonDelete;
    private EachTicket eachTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        ivImage = findViewById(R.id.iv_image);
        editTextTheatre = findViewById(R.id.editTextTheatre);
        editTextMovie = findViewById(R.id.editTextMovie);
        editTextPrice = findViewById(R.id.editTextPrice);

        tvMovieDate = findViewById(R.id.tvMovieDate);
        tvMovieTime = findViewById(R.id.tvMovieTime);
        buttonAdd = findViewById(R.id.buttonAddTicket);
        buttonDelete = findViewById(R.id.buttonDeleteTicket);

        startPicker();



        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        year = cal.get(Calendar.YEAR); month = cal.get(Calendar.MONTH); day = cal.get(Calendar.DAY_OF_MONTH);

        tvMovieDate.setText(day+"-"+month+"-"+year);
        String min = date.getMinutes()+"";
        if(min.length()<2) min = "0"+min;

        tvMovieTime.setText(date.getHours()+":"+min);

        eachTicket = (EachTicket)getIntent().getSerializableExtra("ticket");
        showData();


        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        tvMovieDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTicketActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        year = y;
                        month = m;
                        day = d;
                        tvMovieDate.setText(day+"-"+month+"-"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        tvMovieTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                TimePickerDialog dialog = new TimePickerDialog(AddTicketActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String min = minute+"";
                                if(min.length()<2) min = "0"+min;
                                tvMovieTime.setText(hourOfDay+":"+min);
                            }
                        },date.getHours(),date.getMinutes(),true);
                dialog.show();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoUri == null && eachTicket == null){
                    Constant.showSafeToast(AddTicketActivity.this,"Upload an image");
                    return;
                }

                String tName = String.valueOf(editTextTheatre.getText());
                String mName = String.valueOf(editTextMovie.getText());
                String date = String.valueOf(tvMovieDate.getText());
                String time = String.valueOf(tvMovieTime.getText());
                String price = String.valueOf(editTextPrice.getText());

                if(tName.isEmpty() ||
                        mName.isEmpty() ||
                        date.isEmpty() ||
                        price.isEmpty() ||
                        time.isEmpty()){
                    Constant.showSafeToast(AddTicketActivity.this,"Fill all forms");
                    return;
                }

                startUploading(tName,mName,date,time,price);
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eachTicket == null) return;

                String id = eachTicket.getTicketId();
                if( id == null) return;
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                        .child("tickets").child(id);
                ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Constant.showSafeToast(AddTicketActivity.this, "Ticket deleted");
                            finish();
                        }
                        else{
                            Constant.showSafeToast(AddTicketActivity.this,"Something went wrong");
                        }
                    }
                });
            }
        });


    }


    private void showData(){
        if(eachTicket == null) return;


        Glide.with(this).load(eachTicket.getImage()).error(R.drawable.ic_baseline_photo_camera_back_24).into(ivImage);
        editTextTheatre.setText(eachTicket.getTheatreName());
        editTextMovie.setText(eachTicket.getMovieName());
        tvMovieDate.setText(eachTicket.getDate());
        tvMovieTime.setText(eachTicket.getTime());

        buttonDelete.setVisibility(View.VISIBLE);
        buttonAdd.setText(getString(R.string.update));

    }


    private void startUploading(String tName, String mName, String date, String time,String price){

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Saving ticket info...");
        dialog.setTitle("Adding ticket");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String fName = System.currentTimeMillis()+"";

        if(photoUri != null) {

            StorageReference ref = FirebaseStorage.getInstance().getReference().child("admin").child(fName);

            ref.putFile(photoUri)
                    .addOnSuccessListener(taskSnapshot ->
                            ref.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference()
                                                .child("tickets");
                                        String id = ref1.push().getKey();
                                        if(eachTicket !=null && eachTicket.getTicketId() != null){
                                            id = eachTicket.getTicketId();
                                        }

                                        if (id == null) {
                                            dialog.dismiss();
                                            Constant.showSafeToast(AddTicketActivity.this, "Failed");
                                            return;
                                        }

                                        EachTicket ticket = new EachTicket(id, tName, mName, date, time, String.valueOf(uri),price);

                                        ref1.child(id).setValue(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                dialog.dismiss();
                                                Constant.showSafeToast(AddTicketActivity.this, "Ticket added");
                                                finish();
                                            }
                                        });
                                    })
                                    .addOnFailureListener(e -> {
                                        dialog.dismiss();
                                        Constant.showSafeToast(AddTicketActivity.this, "Failed");
                                        e.printStackTrace();
                                    })
                    )
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Constant.showSafeToast(AddTicketActivity.this, e.getMessage());
                            dialog.dismiss();
                        }
                    });
        }
        else{
            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference()
                    .child("tickets");


            String id = eachTicket.getTicketId();

            if (id == null) {
                dialog.dismiss();
                Constant.showSafeToast(AddTicketActivity.this, "Failed");
                return;
            }


            EachTicket ticket = new EachTicket(id, tName, mName, date, time, eachTicket.getImage(),price);

            ref1.child(id).setValue(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    dialog.dismiss();
                    Constant.showSafeToast(AddTicketActivity.this, "Ticket added");
                    finish();
                }
            });
        }
    }

    private void startPicker(){
        mGetContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(), result -> {
                    if (result != null) {
                        photoUri = result;
                        ivImage.setImageURI(photoUri);
                    }
                }
        );
    }

}