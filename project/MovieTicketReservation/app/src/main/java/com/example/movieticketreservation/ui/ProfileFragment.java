package com.example.movieticketreservation.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieticketreservation.R;
import com.example.movieticketreservation.classes.EachTicket;
import com.example.movieticketreservation.classes.EachTicketPro;
import com.example.movieticketreservation.classes.TicketAdapterMine;
import com.example.movieticketreservation.classes.TicketAdapterUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    private RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        showAllTickets();

    }


    private void showAllTickets(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) return;

        String uid = user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("booked").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<EachTicketPro> tickets = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()){
                    EachTicket tck = ds.child("ticket").getValue(EachTicket.class);
                    String seat = String.valueOf(ds.child("seat").getValue());
                    if(tck == null) return;

                    tickets.add(new EachTicketPro(seat,tck));
                }
                TicketAdapterMine adapter = new TicketAdapterMine(requireActivity(),tickets);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
