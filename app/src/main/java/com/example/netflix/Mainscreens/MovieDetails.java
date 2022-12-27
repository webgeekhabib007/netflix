package com.example.netflix.Mainscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.netflix.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MovieDetails extends AppCompatActivity {
   ImageView movieimage;
   TextView moviename,description,release,genreid;
   Button Play;
   String name,image,fileurl,moviesid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Objects.requireNonNull(getSupportActionBar()).hide();


        movieimage=findViewById(R.id.imagedeatils);
        moviename=findViewById(R.id.moviename);
        Play=findViewById(R.id.playbutton);
        description = findViewById(R.id.main_description);
        release =findViewById(R.id.main_release);



        Bundle bundle = getIntent().getExtras();
        String img = bundle.getString("img");
        String overview = bundle.getString("overview");
        String title = bundle.getString("title");
        String releaseDate = bundle.getString("releaseDate");
        String video_id= bundle.getString("video_id");
        String genre = bundle.getString("genre");
        String type = bundle.getString("type");


        description.setText(overview);
        release.setText(releaseDate);
        Glide
                .with(getApplicationContext())
                        .load("https://www.themoviedb.org/t/p/w220_and_h330_face"+img)
                                .into(movieimage);

        moviename.setText(title);

        Log.i("movie_details",releaseDate+","+video_id+","+type);

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MovieDetails.this,VideoPlayer.class);
                i.putExtra("overview",overview);
                i.putExtra("video_id",video_id);
                i.putExtra("title",title);
                startActivity(i);
            }
        });

    }
}