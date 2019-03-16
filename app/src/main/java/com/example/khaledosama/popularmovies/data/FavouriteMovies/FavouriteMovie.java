package com.example.khaledosama.popularmovies.data.FavouriteMovies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.example.khaledosama.popularmovies.data.MainMovies.MovieContract;

@Entity(tableName = "favourite_movies")
public class FavouriteMovie {
    @Ignore
    String IMAGE_BASE_PATH = "https://image.tmdb.org/t/p/w185/";

    @Ignore
    String fullPathImage;
    @PrimaryKey
    @ColumnInfo(name = MovieContract.MovieEntry._ID)
    long id;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_TITLE)
    String title;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_IMAGE)
    String image;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_OVERVIEW)
    String overview;

    @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_AVG_RATING)
    String rating;
    @Ignore
    public FavouriteMovie(){}

    public FavouriteMovie(long id, String title, String image, String overview, String rating){
        this.id = id;
        this.title = title;
        this.image = image;
        this.overview = overview;
        this.rating = rating;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public String getFullPathImage(){return IMAGE_BASE_PATH + image;}

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public static FavouriteMovie fromContentValue(ContentValues contentValues){
        FavouriteMovie retMovie = new FavouriteMovie();
        retMovie.setId((long)contentValues.get(MovieContract.MovieEntry._ID));
        retMovie.setImage(contentValues.get(MovieContract.MovieEntry.COLUMN_IMAGE).toString());
        retMovie.setTitle(contentValues.get(MovieContract.MovieEntry.COLUMN_TITLE).toString());
        retMovie.setRating(contentValues.get(MovieContract.MovieEntry.COLUMN_AVG_RATING).toString());
        retMovie.setOverview(contentValues.get(MovieContract.MovieEntry.COLUMN_OVERVIEW).toString());

        return retMovie;

    }

}