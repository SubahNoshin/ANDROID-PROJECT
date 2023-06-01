package com.example.movieticketreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.movieticketreservation.classes.EachTicket;
import com.example.movieticketreservation.classes.TicketAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminProfileActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        fabAdd = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclerView);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfileActivity.this,AddTicketActivity.class));
            }
        });

        showAllTickets();

    }

    private void showAllTickets(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("tickets");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<EachTicket> tickets = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()){
                    EachTicket tck = ds.getValue(EachTicket.class);
                    if(tck == null) return;

                    tickets.add(tck);
                }
                TicketAdapter adapter = new TicketAdapter(AdminProfileActivity.this,tickets);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}