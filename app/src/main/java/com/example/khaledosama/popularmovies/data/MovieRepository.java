package com.example.khaledosama.popularmovies.data;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import com.example.khaledosama.popularmovies.data.FavouriteMovies.FavouriteMovie;
import com.example.khaledosama.popularmovies.data.FavouriteMovies.FavouriteMovieDAO;
import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.example.khaledosama.popularmovies.data.MainMovies.MovieApiService;
import com.example.khaledosama.popularmovies.data.MainMovies.MovieContract.MovieEntry;

import com.example.khaledosama.popularmovies.R;
import com.example.khaledosama.popularmovies.data.MainMovies.MovieDAO;
import com.example.khaledosama.popularmovies.data.MainMovies.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private Context mContext;
    private MovieDAO movieDAO;
    private FavouriteMovieDAO favouriteMovieDAO;

    public MovieRepository(Context context){
        mContext = context;
        movieDAO = MovieDatabase.getInstance(context).movieDAO();
        favouriteMovieDAO = MovieDatabase.getInstance(context).favouriteMovieDAO();
    }

    public MutableLiveData< List<Movie> >getMovies(final String moviesType){

        final MutableLiveData< List<Movie> > movies = new MutableLiveData<>();

        final SharedPreferences preference = mContext.getSharedPreferences(mContext.getString(
                R.string.movie_type_shared_prefrence),Context.MODE_PRIVATE);

        String movieSharedPrefrence = preference.getString(mContext.getString(R.string.movie_prefrence_key), "popular");
        if(movieSharedPrefrence.equals(moviesType)){
            int count = movieDAO.count();
            if(count!=0){
                Cursor cursor = movieDAO.getMovies();
                ArrayList<Movie> movieArrayList = new ArrayList<>();
                while(cursor.moveToNext()){
                    movieArrayList.add(new Movie(cursor.getLong(cursor.getColumnIndex(MovieEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_IMAGE)),
                            cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW)),
                            cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_AVG_RATING))));
                }
                movies.setValue(movieArrayList);
                return movies;
            }

        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<MovieResponse> call = movieApiService.getMovies(moviesType, mContext.getResources().getString(R.string.api_key));

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                movieDAO.deleteAll();
                movieDAO.insertAll(response.body().getResults());

                movies.setValue(response.body().getResults());

                SharedPreferences.Editor editor = preference.edit();
                editor.putString(mContext.getString(R.string.movie_prefrence_key), moviesType);
                editor.apply();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });
        return movies;
    }

    public MutableLiveData<Cursor> getFavouriteMovies(){

        Cursor cursor = favouriteMovieDAO.getFavouriteMovies();


        MutableLiveData<Cursor> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(cursor);
        return mutableLiveData;
    }

}