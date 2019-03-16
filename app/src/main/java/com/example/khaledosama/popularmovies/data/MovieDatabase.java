package com.example.khaledosama.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.khaledosama.popularmovies.data.FavouriteMovies.FavouriteMovie;
import com.example.khaledosama.popularmovies.data.FavouriteMovies.FavouriteMovieDAO;
import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.example.khaledosama.popularmovies.data.MainMovies.MovieDAO;

@Database(entities = {Movie.class, FavouriteMovie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    static MovieDatabase mInstance;
    public abstract MovieDAO movieDAO();
    public abstract FavouriteMovieDAO favouriteMovieDAO();

    public synchronized static MovieDatabase getInstance(Context context){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDatabase.class,
                    "ex").allowMainThreadQueries().build();
        }
        return mInstance;

    }
}