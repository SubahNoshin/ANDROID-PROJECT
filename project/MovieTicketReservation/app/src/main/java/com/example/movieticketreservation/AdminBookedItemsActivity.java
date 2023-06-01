package com.example.movieticketreservation;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminBookedItemsActivity extends AppCompatActivity {
    DatabaseReference reff;
    ListView listviewid1;
    List<User> userList;
    AdminAdaptor customadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bookeditems);

        reff= FirebaseDatabase.getInstance().getReference("ADMIN");

        userList=new ArrayList<>();
        customadapter=new AdminAdaptor(AdminBookedItemsActivity.this,userList);
        listviewid1=findViewById(R.id.listviewid1);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> newList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // dataSnapshot holds data for some mail
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        User user = data.getValue(User.class);
                        newList.add(user);
                    }
                }
                userList.clear();
                userList.addAll(newList);
                listviewid1.setAdapter(customadapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}