package com.example.khaledosama.popularmovies.data.MainMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("{type}")
    Call<MovieResponse>getMovies(@Path("type") String moviesType, @Query("api_key") String api_key);

}