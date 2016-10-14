package me.androidbox.flicks.model;

import java.util.List;

/**
 * Created by steve on 10/12/16.
 */

public class Movie {
    String page;
    List<Results> results;
    Dates dates;
    int total_pages;
    int total_results;

    static class Results {
        String poster_path;
        boolean adult;
        String overview;
        String release_date;
        List<Integer> genre_ids;
        int id;
        String original_title;
        String original_language;
        String title;
        String backdrop_path;
        float popularity;
        int vote_count;
        boolean video;
        float vote_average;
    }

    static class Dates {
        String maximum;
        String minimum;
    }
}
