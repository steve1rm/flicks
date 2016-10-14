package me.androidbox.flicks.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by steve on 10/12/16.
 */

public interface FlicksMovieService {
    @GET("movie/upcoming")
    Call<Movie> getUpcomingMovies(@Query("api_key") String apiKey);
}
