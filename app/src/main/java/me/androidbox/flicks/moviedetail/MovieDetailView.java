package me.androidbox.flicks.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class MovieDetailView extends Fragment implements MovieDetailViewContract {

    @Inject
    MovieDetailPresenterImp mMovieDetailPresenterImp;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerInjector.getsAppComponent().inject(MovieDetailView.this);

        if(mMovieDetailPresenterImp != null) {
            Timber.d("mMovieDetailPresenterImp != null");
            mMovieDetailPresenterImp.loadMovieDetail(302946L);
        }
    }

    @Override
    public void loadMovieDetail(String id) {

    }
}
