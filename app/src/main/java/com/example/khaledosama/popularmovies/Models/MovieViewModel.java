package com.example.khaledosama.popularmovies.Models;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.database.Cursor;

import com.example.khaledosama.popularmovies.data.FavouriteMovies.FavouriteMovie;
import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.example.khaledosama.popularmovies.data.MovieRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {

    MutableLiveData<Boolean> getDataFlage = new MutableLiveData<>();
    public MutableLiveData<String> moviesMutableLiveData = new MutableLiveData<>();
    public MovieRepository movieRepository;

    public final LiveData<List<Movie>> moviesLiveData = Transformations.switchMap(moviesMutableLiveData, new Function<String, LiveData<List<Movie>>>() {
        @Override
        public LiveData<List<Movie>> apply(String input) {
            return movieRepository.getMovies(input);
        }
    });
    public final LiveData<Cursor> favouriteMovies = Transformations.switchMap(getDataFlage, new Function<Boolean, LiveData<Cursor>>() {
        @Override
        public LiveData<Cursor> apply(Boolean input) {
            return movieRepository.getFavouriteMovies();
        }
    });

    public void setInput(String moviesType,MovieRepository movieRepository){

        if(this.movieRepository ==null){
            this.movieRepository = movieRepository;
        }
        moviesMutableLiveData.setValue(moviesType);

    }
    public void setRepositry(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public LiveData<List<Movie>> getMovies(){
        return moviesLiveData;
    }

    public LiveData<Cursor> getFavouriteMovies(){
        getDataFlage.setValue(true);
        return favouriteMovies;}

}