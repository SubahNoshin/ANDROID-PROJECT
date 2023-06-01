package com.example.movieticketreservation;

import static com.example.movieticketreservation.R.*;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class AdminAdaptor extends ArrayAdapter<User> {
        private Activity context;
        private List<User> userList;

        public AdminAdaptor(Activity context, List<User> userList) {
                super(context, layout.sample1_layout,userList);
                this.context = context;
                this.userList = userList;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                //LayoutInflater layoutInflater=context.getLayoutInflater();
                View view = LayoutInflater.from(context).inflate(layout.sample1_layout,parent,false);

                User us=userList.get(position);

                TextView textview6=view.findViewById(id.textview6);
                TextView textview7=view.findViewById(id.textview7);
                TextView textview8=view.findViewById(id.textview8);
                TextView textview9=view.findViewById(id.textview9);
                TextView textview10=view.findViewById(id.textview10);
                textview6.setText("User name: " + us.name);
                textview7.setText("Movie name: " + us.movies);
                textview8.setText("Seat no: " + us.seat);
                textview9.setText("Date: " + us.date);
                textview10.setText("Price: " + us.money);

                return view;
        }
}


