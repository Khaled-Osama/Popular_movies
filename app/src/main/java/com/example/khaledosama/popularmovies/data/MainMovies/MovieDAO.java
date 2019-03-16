package com.example.khaledosama.popularmovies.data.MainMovies;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.example.khaledosama.popularmovies.data.MainMovies.MovieContract;

import java.util.List;

@Dao
public interface MovieDAO {
    @Query("SELECT * FROM "+ MovieContract.MovieEntry.TABLE_NAME)
    Cursor getMovies();

    @Query("DELETE FROM "+MovieContract.MovieEntry.TABLE_NAME)
    public void deleteAll();

    @Insert
    long insert(Movie movie);

    @Insert
    List<Long> insertAll(List<Movie>movies);

    @Query("SELECT COUNT(*) FROM " + MovieContract.MovieEntry.TABLE_NAME)
    int count();
}