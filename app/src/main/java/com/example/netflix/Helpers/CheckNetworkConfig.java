package com.example.netflix.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkConfig {
    public ConnectivityManager connectivityManager;
    public Context context;
    public NetworkInfo networkInfo;
    public CheckNetworkConfig(Context context){
        this.context =context;
    }
    public void prepare(){
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
    }
    public boolean isPrepared(){
        return networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable();
    }
}
