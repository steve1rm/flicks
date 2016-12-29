package me.androidbox.flicks.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by steve on 10/12/16.
 */

public interface FlicksMovieService {
    @GET("movie/now_playing")
    Observable<Pages> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Observable<List<Results>> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<Videos> getMovieVideos(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/latest")
    Observable<Latest> getLatestMovie(@Query("api_key") String apiKey);
}
