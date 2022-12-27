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
import com.example.netflix.Model.SeriesModel;
import com.example.netflix.R;

import java.util.ArrayList;

public class LatestSeriesAdapter extends RecyclerView.Adapter<LatestSeriesAdapter.MyViewHolder>{
    Context context;
    ArrayList<SeriesModel> list;

    public LatestSeriesAdapter(Context context, ArrayList<SeriesModel> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.horizontal_item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide
                .with(context)
                .load(String.valueOf("https://www.themoviedb.org/t/p/w220_and_h330_face"+list.get(position).getPoster_path()))
                .into(holder.movieImg);

        holder.title.setText(list.get(position).getName().toString());
        holder.release.setText(list.get(position).getFirst_air_date().substring(0,4));
        holder.movieImg.setOnClickListener(view->{
            Intent intent = new Intent(context, MovieDetails.class);
            intent.putExtra("img",list.get(position).getPoster_path());
            intent.putExtra("title",list.get(position).getName());
            intent.putExtra("overview",list.get(position).getOverview());
            intent.putExtra("releaseDate",list.get(position).getFirst_air_date());
            intent.putExtra("video_id",list.get(position).getId().toString());
            intent.putExtra("type","tv");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
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
            title = itemView.findViewById(R.id.text_grid_item);
            movieImg = itemView.findViewById(R.id.img_grid_item);
            release=itemView.findViewById(R.id.text_grid_item1);
        }
    }
}
