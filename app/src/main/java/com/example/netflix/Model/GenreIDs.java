package com.example.netflix.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreIDs {
    @SerializedName("genre_ids")
    List<String> list;
    GenreIDs(){};

    public GenreIDs(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
