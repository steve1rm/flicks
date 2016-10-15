package me.androidbox.flicks.moviedetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.androidbox.flicks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailView extends Fragment implements MovieDetailViewContrct {


    public MovieDetailView() {
        // Required empty public constructor
    }

    public static MovieDetailView getInstance() {
        return new MovieDetailView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_detail_view, container, false);
    }

    @Override
    public void loadMovieDetail(String id) {

    }
}
