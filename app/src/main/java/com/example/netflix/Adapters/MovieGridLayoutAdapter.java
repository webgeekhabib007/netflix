package com.example.netflix.Adapters;

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
import com.example.netflix.Mainscreens.MovieDetails;
import com.example.netflix.Model.MovieModel;
import com.example.netflix.R;

import java.util.ArrayList;

public class MovieGridLayoutAdapter extends RecyclerView.Adapter<MovieGridLayoutAdapter.MyViewHolder> {
    Context context;
    ArrayList<MovieModel> list;
    public MovieGridLayoutAdapter(Context context, ArrayList<MovieModel> list){
        this.context = context;
        this.list  = list;
    }
    @NonNull
    @Override
    public MovieGridLayoutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieGridLayoutAdapter.MyViewHolder holder, int position) {
        Glide
                .with(context)
                .load(String.valueOf("https://www.themoviedb.org/t/p/w220_and_h330_face"+list.get(position).getPoster_path().toString()))
                .into(holder.imageView);

        holder.textView.setText(list.get(position).getTitle());
        if(list.get(position).getRelease_date()!=null && list.get(position).getRelease_date().length()>4)holder.textView1.setText(list.get(position).getRelease_date().substring(0,4));
        else holder.textView1.setText("null");
        holder.imageView.setOnClickListener(view->{
            Intent intent = new Intent(context, MovieDetails.class);
            intent.putExtra("img",list.get(position).getPoster_path());
            intent.putExtra("title",list.get(position).getTitle());
            intent.putExtra("overview",list.get(position).getOverview());
            intent.putExtra("releaseDate",list.get(position).getRelease_date());
            intent.putExtra("video_id",list.get(position).getId().toString());
            intent.putExtra("type","movie");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView,textView1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_grid_item);
            textView = itemView.findViewById(R.id.text_grid_item);
            textView1 = itemView.findViewById(R.id.text_grid_item1);
        }
    }
}
