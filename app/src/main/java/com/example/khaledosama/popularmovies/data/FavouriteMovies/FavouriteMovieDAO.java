package com.example.khaledosama.popularmovies.data.FavouriteMovies;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.example.khaledosama.popularmovies.data.MainMovies.MovieContract;

@Dao
public interface FavouriteMovieDAO {

    String table_name = "favourite_movies";

    @Query("SELECT * FROM " + table_name)
    Cursor getFavouriteMovies();

    @Query("Select * FROM "+table_name+" WHERE "+MovieContract.MovieEntry._ID+"= :id")
    Cursor favouriteMovie(long id);

    @Insert
    long addnewFavouriteMovie(FavouriteMovie favouriteMovie);

    @Query("DELETE FROM " + table_name + " WHERE "+ MovieContract.MovieEntry._ID+"= :id")
    int removeMovie(long id);

}