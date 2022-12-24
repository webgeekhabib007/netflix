package com.example.netflix.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatestMovieModel {
    @SerializedName("page")
    String page;
    @SerializedName("results")
    List<MovieModel> results;
    public LatestMovieModel(){};

    public LatestMovieModel(String page, List<MovieModel> results) {
        this.page = page;
        this.results = results;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<MovieModel> getResults() {
        return results;
    }

    public void setResults(List<MovieModel> results) {
        this.results = results;
    }
}
