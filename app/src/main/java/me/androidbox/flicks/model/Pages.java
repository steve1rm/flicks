package me.androidbox.flicks.model;

import java.util.List;

/**
 * Created by steve on 12/10/16.
 */

public class Pages {
    private int page;
    private List<Results> results;
    private Dates dates;
    private int total_pages;
    private int total_results;

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public Dates getDates() {
        return dates;
    }

    public int getPage() {
        return page;
    }

    public List<Results> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }
}
