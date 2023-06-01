package com.example.movieticketreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class seatReservation extends AppCompatActivity {
    String[] names;
    EditText date,seat;
    TextView status,showmovie,showseat,showtime,showmoney,show;//show spinner er jnno
    private Spinner spinnerid;

    DatePickerDialog datePickerDialog;
    Calendar calendar;
    DatabaseReference reff;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_reservation);
        names=getResources().getStringArray(R.array.Through);
        spinnerid=findViewById(R.id.spinnerid);
        show=findViewById(R.id.show);
        seat=findViewById(R.id.seat);
        date=findViewById(R.id.time);
        status=findViewById(R.id.status);
        showmovie=findViewById(R.id.showmovie);
        showseat=findViewById(R.id.showseat);
        showtime=findViewById(R.id.showtime);
        showmoney=findViewById(R.id.showmoney);
        confirm=findViewById(R.id.confrim);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,R.layout.sample_splinner,R.id.txtsample,names);
        spinnerid.setAdapter(adapter);
        calendar=Calendar.getInstance();
        String usermail=getIntent().getExtras().getString("mail");
        String username3=getIntent().getExtras().getString("username");
        String mov=getIntent().getExtras().getString("movie");
        String newmail=usermail.replace(".",",");
        reff= FirebaseDatabase.getInstance().getReference("ADMIN");

        DatePickerDialog.OnDateSetListener Date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateDate();
            }
        };

        date.setOnClickListener(view -> {
            new DatePickerDialog(seatReservation.this,Date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                String spn= spinnerid.getSelectedItem().toString();
                show.setText("Payed by : "+spn);
                String seats=seat.getText().toString().trim();
                showseat.setText("Your seat : "+seats);
                String movie=getIntent().getExtras().getString("movie");
                showmovie.setText("Movie : "+movie);
                showmoney.setText("Ticket : 300 taka only");
                status.setText("STATUS");
                String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                String month=String.valueOf(calendar.get(Calendar.MONTH));
                String year=String.valueOf(calendar.get(Calendar.YEAR));
                String dayname=String.valueOf(calendar.get(Calendar.DATE));
                showtime.setText("Date : "+day+"/"+month+"/"+year+" "+dayname);

                user.setName(username3);
                user.setMovies(mov);
                user.setSeat(seats);
                user.setDate(String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year));
                user.setMoney("300 taka only");
                reff.child(newmail).push().setValue(user);
            }
        });
    }
    private void updateDate() {
        String mformat="dd/mm/yy EEEE";
        SimpleDateFormat dateFormat=new SimpleDateFormat(mformat, Locale.US);
        date.setText(dateFormat.format(calendar.getTime()));
    }
}