package com.example.movieticketreservation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<User> {
    private Activity context;
    private List<User> userList;

    public CustomAdapter( Activity context,List<User> userList) {
        super(context, R.layout.nev_sample,userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.nev_sample,null,true);
        User us=userList.get(position);

        TextView textview1=view.findViewById(R.id.textview1);
        TextView textview2=view.findViewById(R.id.textview2);
        TextView textview3=view.findViewById(R.id.textview3);
        TextView textview4=view.findViewById(R.id.textview4);
        TextView textview5=view.findViewById(R.id.textview5);
        textview1.setText("User name: " + us.getName());
        textview2.setText("Movie name: " + us.getMovies());
        textview3.setText("Seat no: " + us.getSeat());
        textview4.setText("Date: " + us.getDate());
        textview5.setText("Price: " + us.getMoney());

        return view;
    }
}
