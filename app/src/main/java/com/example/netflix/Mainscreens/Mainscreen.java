package com.example.netflix.Mainscreens;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflix.Adapters.LatestMoviesAdapter;
import com.example.netflix.Adapters.LatestSeriesAdapter;
import com.example.netflix.Adapters.TopRatedMovieAdapter;
import com.example.netflix.Adapters.TopRatedSeriesAdapter;
import com.example.netflix.Helpers.CheckNetworkConfig;
import com.example.netflix.Model.LatestMovieModel;
import com.example.netflix.Model.MovieModel;
import com.example.netflix.Model.SeriesModel;
import com.example.netflix.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mainscreen extends AppCompatActivity {
    RecyclerView latest_movies,
            latest_series,
            top_rated_movies,
            top_rated_series;

    TextView movie_tool_text,
            series_tool_text;

    ProgressDialog progressDialog;
    ArrayList<MovieModel> movieModelArrayList;
    ArrayList<MovieModel> topRatedArrayList;
    ArrayList<SeriesModel> seriesModelArrayList;
    ArrayList<SeriesModel> topRatedSeriesList;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //grave all the necessary views RecyclerView
        latest_movies = findViewById(R.id.recycler_latest_movies);
        latest_series = findViewById(R.id.recycler_latest_series);
        top_rated_movies = findViewById(R.id.recycler_top_rated_movies);
        top_rated_series =findViewById(R.id.recycler_top_rated_series);

        //grave all necessary views TextViews
        movie_tool_text = findViewById(R.id.movietooltext);
        series_tool_text = findViewById(R.id.tvseriestooltext);


        //code navigation menu
        BottomNavigationView bottomNavigationView;
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeicon:
                        break;
                    case R.id.serachicon:
                        Intent i=new Intent(Mainscreen.this, Search.class);
                        startActivity(i);
                        break;
                    case R.id.settingsicon:
                        Intent j=new Intent(Mainscreen.this, Settings.class);
                        startActivity(j);
                        break;
                }
                return false;
            }
        });

        //network connection check
        CheckNetworkConfig checkNetworkConfig=new CheckNetworkConfig(getApplicationContext());
        checkNetworkConfig.prepare();
        if(!checkNetworkConfig.isPrepared()){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("No Internet Connection");
            builder.setMessage("Please turn on your internet connection to continue.");
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    recreate();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
        else{
            movieModelArrayList = new ArrayList<>();
            GetMovieList movieList = new GetMovieList();
            movieList.execute();

            topRatedArrayList = new ArrayList<>();
            GetTopRated topRated = new GetTopRated();
            topRated.execute();

            seriesModelArrayList = new ArrayList<SeriesModel>();
            GetSeriesList seriesList = new GetSeriesList();
            seriesList.execute();

            topRatedSeriesList = new ArrayList<SeriesModel>();
            GetTopRatedSeries getTopRatedSeries = new GetTopRatedSeries();
            getTopRatedSeries.execute();
        }


        movie_tool_text.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),Movies.class);
            startActivity(intent);
        });

        series_tool_text.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(),TvSeries.class);
            startActivity(intent);
        });

    }
    public class GetMovieList extends AsyncTask<String,String ,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String data="";

            try{
                URL url = null;
                HttpURLConnection httpURLConnection=null;
                try{
                    url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=12247654257fe450ccbd7df2985ce877&language=en-US&page=1");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream= httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line ;
                    while((line=bufferedReader.readLine())!=null){
                        data+= line;
                    }
                }catch (IOException exception){
                    exception.printStackTrace();
                }finally {
                    if(httpURLConnection !=null){
                        httpURLConnection.disconnect();
                    }
                }

            }catch (Exception exception){
                exception.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            movieModelArrayList.clear();
            try {
                JSONObject response = new JSONObject(s);
                JSONArray movies = response.getJSONArray("results");
                for(int i=0;i<movies.length();i++){
                    JSONObject movie = movies.getJSONObject(i);
                    MovieModel  movieModel = new MovieModel();
                    Gson gson = new Gson();
                    movieModel = gson.fromJson(movie.toString(),MovieModel.class);
                    movieModelArrayList.add(movieModel);
                    //Log.i("img",String.valueOf("https://www.themoviedb.org/t/p/w220_and_h330_face"+movieModel.getPoster_path()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            upDateRecyclerView(movieModelArrayList);
        }
    }

    public class GetTopRated extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String data="";

            try{
                URL url = null;
                HttpURLConnection httpURLConnection=null;
                try{
                    url = new URL("https://api.themoviedb.org/3/movie/top_rated?api_key=12247654257fe450ccbd7df2985ce877&language=en-US&page=1");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream= httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line ;
                    while((line=bufferedReader.readLine())!=null){
                        data+= line;
                    }
                }catch (IOException exception){
                    exception.printStackTrace();
                }finally {
                    if(httpURLConnection !=null){
                        httpURLConnection.disconnect();
                    }
                }

            }catch (Exception exception){
                exception.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            topRatedArrayList.clear();
            try {
                JSONObject response = new JSONObject(s);
                JSONArray movies = response.getJSONArray("results");
                for(int i=0;i<movies.length();i++){
                    JSONObject movie = movies.getJSONObject(i);
                    MovieModel  movieModel = new MovieModel();
                    Gson gson = new Gson();
                    movieModel = gson.fromJson(movie.toString(),MovieModel.class);
                    topRatedArrayList.add(movieModel);
                    //Log.i("top_rated","https://www.themoviedb.org/t/p/w220_and_h330_face"+movieModel.getPoster_path());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            upDateRecyclerView1(topRatedArrayList);
        }
    }

    public class GetSeriesList extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String data="";

            try{
                URL url = null;
                HttpURLConnection httpURLConnection=null;
                try{
                    url = new URL("https://api.themoviedb.org/3/tv/popular?api_key=12247654257fe450ccbd7df2985ce877&language=en-US&page=1");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream= httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line ;
                    while((line=bufferedReader.readLine())!=null){
                        data+= line;
                    }
                }catch (IOException exception){
                    exception.printStackTrace();
                }finally {
                    if(httpURLConnection !=null){
                        httpURLConnection.disconnect();
                    }
                }

            }catch (Exception exception){
                exception.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            seriesModelArrayList.clear();
            try {
                JSONObject response = new JSONObject(s);
                JSONArray series = response.getJSONArray("results");
                for(int i=0;i<series.length();i++){
                    JSONObject tv = series.getJSONObject(i);
                    SeriesModel seriesModel = new SeriesModel();
                    Gson gson = new Gson();
                    seriesModel = gson.fromJson(tv.toString(),SeriesModel.class);
                    seriesModelArrayList.add(seriesModel);
                    //Log.i("top_rated","https://www.themoviedb.org/t/p/w220_and_h330_face"+seriesModel.getPoster_path());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.i("tv",s);
            upDateRecyclerView2(seriesModelArrayList);
        }
    }

    public class GetTopRatedSeries extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String data="";

            try{
                URL url = null;
                HttpURLConnection httpURLConnection=null;
                try{
                    url = new URL("https://api.themoviedb.org/3/tv/top_rated?api_key=12247654257fe450ccbd7df2985ce877&language=en-US&page=1");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream= httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line ;
                    while((line=bufferedReader.readLine())!=null){
                        data+= line;
                    }
                }catch (IOException exception){
                    exception.printStackTrace();
                }finally {
                    if(httpURLConnection !=null){
                        httpURLConnection.disconnect();
                    }
                }

            }catch (Exception exception){
                exception.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            topRatedSeriesList.clear();
            try {
                JSONObject response = new JSONObject(s);
                JSONArray series = response.getJSONArray("results");
                for(int i=0;i<series.length();i++){
                    JSONObject tv = series.getJSONObject(i);
                    SeriesModel seriesModel = new SeriesModel();
                    Gson gson = new Gson();
                    seriesModel = gson.fromJson(tv.toString(),SeriesModel.class);
                    topRatedSeriesList.add(seriesModel);
                    Log.i("top_rated","https://www.themoviedb.org/t/p/w220_and_h330_face"+seriesModel.getPoster_path());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.i("tv",s);
            Log.i("tv",s);
            upDateRecyclerView3(topRatedSeriesList);
        }
    }

    public void upDateRecyclerView(ArrayList<MovieModel> list){
        LatestMoviesAdapter latestMoviesAdapter=new LatestMoviesAdapter(getApplicationContext(),list);
        latest_movies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
        latest_movies.setAdapter(latestMoviesAdapter);
    }
    public void upDateRecyclerView1(ArrayList<MovieModel> list){
        TopRatedMovieAdapter topRatedMovieAdapter =new TopRatedMovieAdapter(getApplicationContext(),list);
        top_rated_movies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
        top_rated_movies.setAdapter(topRatedMovieAdapter);
    }
    public void upDateRecyclerView2(ArrayList<SeriesModel> list){
        LatestSeriesAdapter latestSeriesAdapter = new LatestSeriesAdapter(getApplicationContext(),list);
        latest_series.setLayoutManager(new LinearLayoutManager(
                getApplicationContext(),
                RecyclerView.HORIZONTAL,
                false
        ));
        latest_series.setAdapter(latestSeriesAdapter);
    }
    public void upDateRecyclerView3(ArrayList<SeriesModel> list){
        TopRatedSeriesAdapter topRatedSeriesAdapter =new TopRatedSeriesAdapter(getApplicationContext(),list);
        top_rated_series.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
        top_rated_series.setAdapter(topRatedSeriesAdapter);
    }
}

