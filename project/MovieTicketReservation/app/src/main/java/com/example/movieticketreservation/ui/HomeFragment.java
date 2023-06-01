package com.example.movieticketreservation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketreservation.AddTicketActivity;
import com.example.movieticketreservation.AdminProfileActivity;
import com.example.movieticketreservation.R;
import com.example.movieticketreservation.classes.EachTicket;
import com.example.movieticketreservation.classes.TicketAdapter;
import com.example.movieticketreservation.classes.TicketAdapterUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    private RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
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
                TicketAdapterUser adapter = new TicketAdapterUser(requireActivity(),tickets);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
