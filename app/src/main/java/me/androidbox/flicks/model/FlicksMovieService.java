package me.androidbox.flicks.model;

import android.graphics.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by steve on 10/12/16.
 */

public interface FlicksMovieService {
    @GET("movie/upcoming")
    Call<List<Movie>> getUpcomingMovies();
}
