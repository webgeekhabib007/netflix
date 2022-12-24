package com.example.netflix.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieModel {
    @SerializedName("poster_path")
    String poster_path;
    @SerializedName("adult")
    String adult;
    @SerializedName("overview")
    String overview;
    @SerializedName("release_date")
    String release_date;
    @SerializedName("genre_ids")
    List<String> genre_ids;
    @SerializedName("id")
    Integer id;
    @SerializedName("original_title")
    String original_title;
    @SerializedName("original_language")
    String original_language;
    @SerializedName("title")
    String title;
    @SerializedName("backdrop_path")
    String backdrop_path;
    @SerializedName("popularity")
    String popularity;
    @SerializedName("vote_count")
    String vote_count;
    @SerializedName("video")
    boolean video;
    @SerializedName("vote_average")
    String vote_average;

    //constructor
    public MovieModel(){};

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getAdult(String adult) {
        return this.adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<String> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<String> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle(String title) {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public MovieModel(String poster_path, String adult, String overview, String release_date, List<String> genre_ids, Integer id, String original_title, String original_language, String title, String backdrop_path, String popularity, String vote_count, Boolean video, String vote_average) {
        this.poster_path = poster_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
    }

    public String getAdult() {
        return adult;
    }

    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieModel{" +
                "poster_path='" + poster_path + '\'' +
                ", adult='" + adult + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", genre_ids=" + genre_ids +
                ", id=" + id +
                ", original_title='" + original_title + '\'' +
                ", original_language='" + original_language + '\'' +
                ", title='" + title + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", popularity='" + popularity + '\'' +
                ", vote_count='" + vote_count + '\'' +
                ", video=" + video +
                ", vote_average='" + vote_average + '\'' +
                '}';
    }
}
