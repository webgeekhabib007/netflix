package com.example.netflix.Mainscreens;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.netflix.Model.MovieModel;
import com.example.netflix.R;
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
import java.util.Objects;


public class VideoPlayer extends AppCompatActivity {

    WebView mWebView;
    TextView titl,des;
    String base_url1 = "https://api.themoviedb.org/3/movie/";
    String base_url2="/videos?api_key=12247654257fe450ccbd7df2985ce877&language=en-US";
    String key="";
    String base_url;
    ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Objects.requireNonNull(getSupportActionBar()).hide();
        mWebView = (WebView) findViewById(R.id.videoview);

        String title = getIntent().getExtras().getString("title");
        String overview = getIntent().getExtras().getString("overview");
        String video_id = getIntent().getExtras().getString("video_id");

        base_url=base_url1 + video_id + base_url2;

        titl = findViewById(R.id.title);
        des = findViewById(R.id.trailer);
        progressBar =findViewById(R.id.progressBar2);

        titl.setText(title);
        des.setText("Description :"+overview);



        GetVideo getvideo = new GetVideo();
        getvideo.execute(base_url);





    }

    public class GetVideo extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String data="";
            String baseUrl=strings[0];
            try{
                URL url = null;
                HttpURLConnection httpURLConnection=null;
                try{
                    url = new URL(baseUrl);
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
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray videos = jsonObject.getJSONArray("results");
                if(videos.length() >0){
                    for(int i=0;i<videos.length();i++){
                        JSONObject video = videos.getJSONObject(0);
                        if(video.getString("site").toString().equals("YouTube")){
                            key=video.getString("key");
                            break;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("videos",base_url+","+key);
            setUpVideoView(key);
        }
    }

    private void setUpVideoView(String key) {
        progressBar.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
        String videoStr = "<html><body><br>" +
                "<iframe width=\"100%\" height=\"315\" " +
                "src=\"https://www.youtube.com/embed/" +
                key +
                "\" " + "frameborder=\"0\" allowfullscreen>" +
                "</iframe>" +
                "</body>" +
                "</html>";

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings ws = mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        mWebView.loadData(videoStr, "text/html", "utf-8");
    }

}