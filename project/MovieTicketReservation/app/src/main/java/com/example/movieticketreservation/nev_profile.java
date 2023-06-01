package com.example.movieticketreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class nev_profile extends AppCompatActivity {

    DatabaseReference reff;
    ListView listviewid;
    List<User> userList;
    CustomAdapter customadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nev_profile);
        String mail2=getIntent().getExtras().getString("mail");
        reff= FirebaseDatabase.getInstance().getReference("ADMIN").child(mail2);//mail2 holo mail
        userList=new ArrayList<>();
        customadapter=new CustomAdapter(nev_profile.this,userList);
        listviewid=findViewById(R.id.listviewid);



    reff.addValueEventListener(new ValueEventListener() {


        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            List<User> newList = new ArrayList<>();

            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
              User user=dataSnapshot.getValue(User.class);
                newList.add(user);}
                userList.clear();
                userList.addAll(newList);
                listviewid.setAdapter(customadapter);

        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    });
    }
}