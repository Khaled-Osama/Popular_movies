package com.example.khaledosama.popularmovies.data.FavouriteMovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.khaledosama.popularmovies.data.MainMovies.MovieContract;
import com.example.khaledosama.popularmovies.data.MovieDatabase;

public class MovieContentProvider extends ContentProvider {
    public static final int MOVIES_DIRECTORY = 100;
    public static final int MOVIE_WITH_ID = 101;
    UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES, MOVIES_DIRECTORY);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES+"/#", MOVIE_WITH_ID);

        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        Log.v("Content_provider", getContext().toString());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //SQLiteDatabase sqLiteDatabase = mMovieDbHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        FavouriteMovieDAO favouriteMovieDAO = MovieDatabase.getInstance(getContext()).favouriteMovieDAO();

        if(match!=MOVIES_DIRECTORY) throw new UnsupportedOperationException("Unknown uri: " + uri);

        Cursor retCursor = favouriteMovieDAO.getFavouriteMovies();
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return retCursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        FavouriteMovieDAO favouriteMovieDAO = MovieDatabase.getInstance(getContext()).favouriteMovieDAO();

        int match = uriMatcher.match(uri);
        if(match!=MOVIES_DIRECTORY) throw new UnsupportedOperationException("Unknown uri: " + uri);

        long id = favouriteMovieDAO.addnewFavouriteMovie(FavouriteMovie.fromContentValue(values));
        Uri retUri;
        if(id>0){
            retUri = ContentUris.withAppendedId(uri, id);
        }
        else {
            throw new SQLException("Failed to insert row into "+ uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @NonNull String selection, @NonNull String[] selectionArgs) {
        String id = uri.getPathSegments().get(1);
        int match = uriMatcher.match(uri);
        FavouriteMovieDAO favouriteMovieDAO = MovieDatabase.getInstance(getContext()).favouriteMovieDAO();

        long MoviesRemoved = 0;
        if(match == MOVIE_WITH_ID){
            MoviesRemoved = favouriteMovieDAO.removeMovie(ContentUris.parseId(uri));
        }
        else{
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(MoviesRemoved !=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return (int)MoviesRemoved;
    }

    @Override
    public int update(@NonNull Uri uri,  ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}