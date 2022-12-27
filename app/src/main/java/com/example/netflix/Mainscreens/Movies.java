package com.example.netflix.Mainscreens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflix.Adapters.MainRecyclerAdapter;
import com.example.netflix.Adapters.MovieGridLayoutAdapter;
import com.example.netflix.Helpers.CheckNetworkConfig;
import com.example.netflix.Model.AllCategory;
import com.example.netflix.Model.MovieModel;
import com.example.netflix.R;
import com.example.netflix.Retrofit.RetrofitClient;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Movies extends AppCompatActivity {
    RecyclerView movies_list;
    TextView load_more_btn;
    ProgressDialog dialog;
    ProgressBar load_more_prggressbar;
    MovieGridLayoutAdapter movieGridLayoutAdapter;
    int api_page_number = 1;

    String api_path="https://api.themoviedb.org/3/movie/popular?api_key=12247654257fe450ccbd7df2985ce877&language=en-US&page=";
    ShimmerFrameLayout shimmerFrameLayout;
    ScrollView scrollView;
    ArrayList<MovieModel> movieModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        Objects.requireNonNull(getSupportActionBar()).hide();


        //grave all necessary views RecyclerView
        movies_list = findViewById(R.id.movies_list);
        scrollView = findViewById(R.id.scrollView2);
        load_more_btn = findViewById(R.id.Load_more_movies);
        load_more_prggressbar = findViewById(R.id.Load_more_movies_progressBar);
        shimmerFrameLayout = findViewById(R.id.shimmer_skelaton);
        shimmerFrameLayout.startShimmer();


        BottomNavigationView bottomNavigationView;
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeicon:
                        Intent l=new Intent(Movies.this, Mainscreen.class);
                        startActivity(l);
                        break;
                    case R.id.serachicon:
                        Intent i=new Intent(Movies.this, Search.class);
                        startActivity(i);
                        break;
                    case R.id.settingsicon:
                        Intent j=new Intent(Movies.this, Settings.class);
                        startActivity(j);
                        break;
                }
                return false;
            }
        });
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
            movieModels = new ArrayList<MovieModel>();
            GetMovieList movieList = new GetMovieList();
            movieList.execute(api_path);
        }
        load_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_page_number++;
                new LoadData().execute(new ApiUrl(api_path,String.valueOf(api_page_number)));
            }
        });
    }
    public class GetMovieList extends AsyncTask<String,String ,String> {

        @Override
        protected void onPreExecute() {
            load_more_prggressbar.setVisibility(View.VISIBLE);
            load_more_btn.setText("Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String data="";
            String path_to_call=strings[0];
            try{
                URL url = null;
                HttpURLConnection httpURLConnection=null;
                try{
                    url = new URL(path_to_call+String.valueOf(api_page_number));
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
            load_more_btn.setText("load more");
            load_more_prggressbar.setVisibility(View.INVISIBLE);
            movieModels.clear();
            try {
                JSONObject response = new JSONObject(s);
                JSONArray movies = response.getJSONArray("results");
                for(int i=0;i<movies.length();i++){
                    JSONObject movie = movies.getJSONObject(i);
                    MovieModel  movieModel = new MovieModel();
                    Gson gson = new Gson();
                    movieModel = gson.fromJson(movie.toString(),MovieModel.class);
                    movieModels.add(movieModel);
                    //Log.i("img",String.valueOf("https://www.themoviedb.org/t/p/w220_and_h330_face"+movieModel.getPoster_path()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            upDateRecyclerView(movieModels);
        }
    }

    public class LoadData extends AsyncTask<ApiUrl,String,String>{
        @Override
        protected void onPreExecute() {
            load_more_prggressbar.setVisibility(View.VISIBLE);
            load_more_btn.setText("Loading...");
        }

        @Override
        protected String doInBackground(ApiUrl... strings) {
            String data="";
            ApiUrl api  =strings[0];
            String path_to_call =api.base;
            String page_no = api.page_no;
            try{
                URL url = null;
                HttpURLConnection httpURLConnection=null;
                try{
                    url = new URL(path_to_call+page_no);
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
            load_more_btn.setText("load more");
            load_more_prggressbar.setVisibility(View.INVISIBLE);
            try {
                JSONObject response = new JSONObject(s);
                JSONArray movies = response.getJSONArray("results");
                for(int i=0;i<movies.length();i++){
                    JSONObject movie = movies.getJSONObject(i);
                    MovieModel  movieModel = new MovieModel();
                    Gson gson = new Gson();
                    movieModel = gson.fromJson(movie.toString(),MovieModel.class);
                    movieModels.add(movieModel);
                    movieGridLayoutAdapter.notifyDataSetChanged();
                    //Log.i("img",String.valueOf("https://www.themoviedb.org/t/p/w220_and_h330_face"+movieModel.getPoster_path()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //loadUpDateRecyclerView(movieModels);
        }
    }

    public void upDateRecyclerView(ArrayList<MovieModel> list){
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        movieGridLayoutAdapter = new MovieGridLayoutAdapter(getApplicationContext(),list);
        movies_list.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        movies_list.setAdapter(movieGridLayoutAdapter);
    }
    public void loadUpDateRecyclerView(ArrayList<MovieModel>list){
        movieGridLayoutAdapter.notifyDataSetChanged();
    }

    public class ApiUrl{
        public String base;
        public String page_no;

        public ApiUrl(String base, String page_no) {
            this.base = base;
            this.page_no = page_no;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public String getPage_no() {
            return page_no;
        }

        public void setPage_no(String page_no) {
            this.page_no = page_no;
        }
    }
}