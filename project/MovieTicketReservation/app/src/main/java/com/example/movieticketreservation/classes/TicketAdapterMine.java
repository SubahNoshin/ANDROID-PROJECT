package com.example.movieticketreservation.classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.movieticketreservation.PurchaseActivity;
import com.example.movieticketreservation.R;

import java.util.ArrayList;

public class TicketAdapterMine extends RecyclerView.Adapter<TicketAdapterMine.ItemViewHolder> {

    Context context;
    ArrayList<EachTicketPro> tickets;

    public TicketAdapterMine(Context context, ArrayList<EachTicketPro> tickets) {
        this.context = context;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.each_ticket_layout_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {

        EachTicket ticket = tickets.get(position);

        holder.tvTheatre.setText(ticket.getTheatreName());
        holder.tvMovie.setText(ticket.getMovieName());
        holder.tvDate.setText(ticket.getDate());
        holder.tvTime.setText(ticket.getTime());

        Glide.with(context)
                .load(tickets.get(holder.getAdapterPosition()).getImage())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.ic_baseline_image_24)
                .into(holder.ivImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(context, PurchaseActivity.class);
                intent.putExtra("disable",true);
                intent.putExtra("ticket",tickets.get(position));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        private TextView tvMovie, tvDate, tvTime, tvTheatre;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage =itemView.findViewById(R.id.ivImage);
            tvMovie = itemView.findViewById(R.id.tvMovieName);
            tvTheatre = itemView.findViewById(R.id.tvTheatreName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
