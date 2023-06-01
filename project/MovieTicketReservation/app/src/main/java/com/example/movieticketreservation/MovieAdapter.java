package com.example.movieticketreservation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private Activity context;
    private List<Movie> movieList;
    private String mail;
    private String userName;

    public MovieAdapter(Activity context, List<Movie> movieList, String mail, String userName) {
        super(context, R.layout.movie_layout, movieList);
        this.context = context;
        this.movieList = movieList;
        this.mail = mail;
        this.userName = userName;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.movie_layout,null,true);
        Movie movie = movieList.get(position);

        ImageView imageView = view.findViewById(R.id.iv_movie_banner);
        TextView textView = view.findViewById(R.id.tv_movie_name);
        textView.setText(movie.movie_name);

        Bitmap bitmap = loadBitmapFromAssets(context, movie.image_name);
        imageView.setImageBitmap(bitmap);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(context, seatReservation.class);
                Log.d("Subah", "onItemClick: " + movie.movie_name);
                intent2.putExtra("movie", movie.movie_name);
                intent2.putExtra("mail", mail);
                intent2.putExtra("username", userName);
                context.startActivity(intent2);
            }
        });

        return view;
    }

    public static Bitmap loadBitmapFromAssets(Context context, String name) {
        try {
            InputStream is = context.getAssets().open(name);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
