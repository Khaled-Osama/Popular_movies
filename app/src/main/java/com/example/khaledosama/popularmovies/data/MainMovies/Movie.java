package com.example.khaledosama.popularmovies.data.MainMovies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

import com.example.khaledosama.popularmovies.data.MainMovies.MovieContract.MovieEntry;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = MovieEntry.TABLE_NAME)

public class Movie implements Serializable {

    @Ignore
    public Movie(){}
    public Movie(long id, String title, String image, String overview,String rating){
        this.id = id;
        this.title = title;
        this.image = image;
        this.overview = overview;
        this.rating = rating;
    }
    @Ignore
    public static String IMAGE_BASE_PATH = "https://image.tmdb.org/t/p/w185/";

    @Ignore
    String fullPathImage;
    @PrimaryKey
    @ColumnInfo(name = MovieEntry._ID)
        @SerializedName("id")
    long id;

    @ColumnInfo(name = MovieEntry.COLUMN_TITLE)
            @SerializedName("title")
    String title;
    @ColumnInfo(name = MovieEntry.COLUMN_IMAGE)
            @SerializedName("poster_path")
    String image;
    @ColumnInfo(name = MovieEntry.COLUMN_OVERVIEW)
            @SerializedName("overview")
    String overview;
    @ColumnInfo(name = MovieEntry.COLUMN_AVG_RATING)
            @SerializedName("vote_average")
    String rating;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

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

    public String getFullPathImage(){
        return IMAGE_BASE_PATH + image;
    }

    public static Movie fromContentValue(ContentValues contentValues){
        Movie retMovie = new Movie();
        retMovie.setId((long)contentValues.get(MovieEntry._ID));
        retMovie.setImage(contentValues.get(MovieEntry.COLUMN_IMAGE).toString());
        retMovie.setTitle(contentValues.get(MovieEntry.COLUMN_TITLE).toString());
        retMovie.setRating(contentValues.get(MovieEntry.COLUMN_AVG_RATING).toString());
        retMovie.setOverview(contentValues.get(MovieEntry.COLUMN_OVERVIEW).toString());

        return retMovie;

    }

}