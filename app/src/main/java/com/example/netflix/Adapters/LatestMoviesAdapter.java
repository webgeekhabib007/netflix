package com.example.netflix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflix.Model.LatestMovieModel;
import com.example.netflix.Model.MovieModel;
import com.example.netflix.R;

import java.util.ArrayList;
import java.util.List;

public class LatestMoviesAdapter extends RecyclerView.Adapter<LatestMoviesAdapter.MyViewHolder>{
    Context context;
    ArrayList<MovieModel> list;

    public LatestMoviesAdapter(Context context, ArrayList<MovieModel> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide
                .with(context)
                .load(String.valueOf("https://www.themoviedb.org/t/p/w220_and_h330_face"+list.get(position).getPoster_path()))
                .into(holder.movieImg);


        holder.release.setText(list.get(position).getRelease_date());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImg;
        TextView title,release;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.item_image);
            release=itemView.findViewById(R.id.release_date_card);
        }
    }
}
