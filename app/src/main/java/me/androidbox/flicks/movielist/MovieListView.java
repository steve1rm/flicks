package me.androidbox.flicks.movielist;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.androidbox.flicks.R;
import me.androidbox.flicks.di.DaggerInjector;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListView extends Fragment implements MovieListViewContract {

    @Inject
    MovieListListPresenterImp mMovieListPresenterImp;

    public MovieListView() {
        // Required empty public constructor
    }

    public static MovieListView getInstance() {
        return new MovieListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_list_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerInjector.getsAppComponent().inject(MovieListView.this);

        if(mMovieListPresenterImp != null) {
            Timber.d("mMovieListPresenterImp != null");
            mMovieListPresenterImp.attachView(MovieListView.this);
            mMovieListPresenterImp.loadUpcomingMovies();
        }
    }

    @Override
    public void loadUpcomingMovies() {
        Timber.d("LoadUpcomingMovies");
    }
}
