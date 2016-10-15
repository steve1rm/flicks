package me.androidbox.flicks.model;

import java.util.List;

/**
 * Created by steve on 10/14/16.
 */

public class MovieDetail {
    private String adult;
    private String backdrop_path;
    private String belongs_to_collection;
    private int budget;
    private List<Genres> genres;
    private String homepage;
    private int id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private float popularity;
    private String poster_path;
    private List<ProductionCompanies> production_companies;
    private List<ProductionCountries> production_countries;
    private String release_date;
    private float revenue;
    private int runtime;
    private List<SpokenLanagues> spoken_languages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private float vote_average;
    private int vote_count;

    private static class Genres {
        private int id;
        private String name;
    }

    private static class ProductionCompanies {
        private String name;
        private int id;
    }

    private static class ProductionCountries {
        private String iso_3166_1;
        private String name;
    }

    private static class SpokenLanagues {
        private String is0_639_1;
        private String name;
    }
}
