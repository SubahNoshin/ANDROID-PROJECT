package com.example.movieticketreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<movieinfo> list;

    public MyAdapter(Context context, ArrayList<movieinfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            //movieinfo mov=new movieinfo();
            //holder.moviename.setText(mov.getMovie());
           // holder.seatno.setText(mov.getSeat());
            //holder.dateno.setText(mov.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView moviename,seatno,dateno;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            moviename=itemView.findViewById(R.id.moviename);
            seatno=itemView.findViewById(R.id.seatno);
            dateno=itemView.findViewById(R.id.dateno);
        }
    }
}
