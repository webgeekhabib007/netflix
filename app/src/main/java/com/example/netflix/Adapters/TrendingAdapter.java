package com.example.netflix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.netflix.Model.MovieModel;
import com.example.netflix.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.MyViewHolder> {

    Context context;
    ArrayList<MovieModel> list;
    ViewPager2 viewPager2;


    public TrendingAdapter(Context context,ArrayList<MovieModel> list,ViewPager2 viewPager2){
        this.context = context;
        this.list   = list;
        this.viewPager2=viewPager2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trending_page_card_view,parent,false);
        return new TrendingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide
                .with(context)
                .load(String.valueOf("https://www.themoviedb.org/t/p/w220_and_h330_face"+list.get(position).getPoster_path()))
                .into(holder.roundedImageView);

        if(position == list.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView roundedImageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView =itemView.findViewById(R.id.trending_card);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            list.addAll(list);
            notifyDataSetChanged();
        }
    };
}
