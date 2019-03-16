package com.example.khaledosama.popularmovies.data.MainMovies;

import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getResults(){
        return movies;
    }

}