package com.example.netflix.Mainscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.netflix.Adapters.MainRecyclerAdapter;
import com.example.netflix.Adapters.SearchAdapter;
import com.example.netflix.Adapters.SearchRecyclerAdapter;
import com.example.netflix.Model.AllCategory;
import com.example.netflix.Model.MovieModel;
import com.example.netflix.R;
import com.example.netflix.Retrofit.RetrofitClient;
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

public class Search extends AppCompatActivity {
    String base_search_url="https://api.themoviedb.org/3/search/multi?api_key=12247654257fe450ccbd7df2985ce877&language=en-US&page=1&include_adult=false&query=";
    RecyclerView recyclerView;
    androidx.appcompat.widget.SearchView searchView;
    TextView headline,load_more_btn;
    ProgressBar load_more_prggressbar;

    ArrayList<MovieModel> searchArrayList;
    SearchAdapter searchAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Objects.requireNonNull(getSupportActionBar()).hide();
        load_more_prggressbar =findViewById(R.id.Load_more_movies_progressBar);
        headline = findViewById(R.id.headline);
        recyclerView = findViewById(R.id.search_list);
        searchView =findViewById(R.id.searchbar);
        load_more_btn = findViewById(R.id.Load_more_movies);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                headline.setText("Results for.." + query);
                GetSearchResult get = new GetSearchResult();
                get.execute(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        BottomNavigationView bottomNavigationView;
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeicon:
                        Intent l=new Intent(Search.this, Mainscreen.class);
                        startActivity(l);
                        break;
                    case R.id.serachicon:
                        break;
                    case R.id.settingsicon:
                        Intent j=new Intent(Search.this,Settings.class);
                        startActivity(j);
                        break;
                }
                return false;
            }
        });
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isConnected()||!networkInfo.isAvailable()){
            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setTitle("No internet Connection");
            builder.setMessage("Please turn on internet connection to continue.");
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    recreate();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
        else {
            searchArrayList = new ArrayList<MovieModel>();
            GetMovieList getMovieList = new GetMovieList();
            getMovieList.execute();
        }

    }

    public class GetSearchResult extends AsyncTask<String,String ,String> {

        @Override
        protected void onPreExecute() {
            load_more_prggressbar.setVisibility(View.VISIBLE);
            load_more_btn.setText("Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String data="";
            String query = strings[0];
            try{
                URL url = null;
                HttpURLConnection httpURLConnection=null;
                try{
                    url = new URL(base_search_url+query);
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
            load_more_btn.setText("Search more");
            load_more_prggressbar.setVisibility(View.INVISIBLE);
            searchArrayList.clear();
            try {
                JSONObject response = new JSONObject(s);
                JSONArray movies = response.getJSONArray("results");
                for(int i=0;i<movies.length();i++){
                    JSONObject movie = movies.getJSONObject(i);
                    MovieModel  movieModel = new MovieModel();
                    Gson gson = new Gson();
                    movieModel = gson.fromJson(movie.toString(),MovieModel.class);
                    searchArrayList.add(movieModel);
                    //Log.i("img",String.valueOf("https://www.themoviedb.org/t/p/w220_and_h330_face"+movieModel.getPoster_path()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            searchAdapter.notifyDataSetChanged();
        }
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
            try{
                URL url = null;
                HttpURLConnection httpURLConnection=null;
                try{
                    url = new URL("https://api.themoviedb.org/3/discover/movie?api_key=12247654257fe450ccbd7df2985ce877&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate");
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
            searchArrayList.clear();
            try {
                JSONObject response = new JSONObject(s);
                JSONArray movies = response.getJSONArray("results");
                for(int i=0;i<movies.length();i++){
                    JSONObject movie = movies.getJSONObject(i);
                    MovieModel  movieModel = new MovieModel();
                    Gson gson = new Gson();
                    movieModel = gson.fromJson(movie.toString(),MovieModel.class);
                    searchArrayList.add(movieModel);
                    //Log.i("img",String.valueOf("https://www.themoviedb.org/t/p/w220_and_h330_face"+movieModel.getPoster_path()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            upDateRecyclerView(searchArrayList);
        }
    }
    public void upDateRecyclerView(ArrayList<MovieModel>list){
        searchAdapter = new SearchAdapter(getApplicationContext(),list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(searchAdapter);
    }
}