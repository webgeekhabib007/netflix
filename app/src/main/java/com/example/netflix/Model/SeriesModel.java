package com.example.netflix.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SeriesModel {
    @SerializedName("backdrop_path")
    String backdrop_path;
    @SerializedName("first_air_date")
    String first_air_date;
    @SerializedName("genre_ids")
    List<String> genre_ids;
    @SerializedName("id")
    Integer id;
    @SerializedName("name")
    String name;
    @SerializedName("origin_country")
    ArrayList<String> origin_country;
    @SerializedName("original_language")
    String original_language;
    @SerializedName("original_name")
    String original_name;
    @SerializedName("overview")
    String overview;
    @SerializedName("popularity")
    String popularity;
    @SerializedName("poster_path")
    String poster_path;
    @SerializedName("vote_average")
    String vote_average;
    @SerializedName("vote_count")
    Integer vote_count;

    public SeriesModel(){};

    public SeriesModel(String backdrop_path,
                       String first_air_date,
                       List<String> genre_ids,
                       Integer id,
                       String name,
                       ArrayList<String> origin_country,
                       String original_language,
                       String original_name,
                       String overview,
                       String popularity,
                       String poster_path,
                       String vote_average,
                       Integer vote_count) {
        this.backdrop_path = backdrop_path;
        this.first_air_date = first_air_date;
        this.genre_ids = genre_ids;
        this.id = id;
        this.name = name;
        this.origin_country = origin_country;
        this.original_language = original_language;
        this.original_name = original_name;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(ArrayList<String> origin_country) {
        this.origin_country = origin_country;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }
}
