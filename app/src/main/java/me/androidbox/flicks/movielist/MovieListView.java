package me.androidbox.flicks.movielist;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.flicks.R;
import me.androidbox.flicks.di.DaggerInjector;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListView extends Fragment implements MovieListViewContract {

    @BindView(R.id.tool_bar) Toolbar mToolBar;
    private Unbinder mUnbinder;

    @Inject
    MovieListPresenterImp mMovieListPresenterImp;

    public MovieListView() {
        // Required empty public constructor
    }

    public static MovieListView getInstance() {
        return new MovieListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.movie_list_view, container, false);

        /* View Injection */
        mUnbinder = ButterKnife.bind(MovieListView.this, view);

        setupToolBar();
        setupTabs(view);

        return view;
    }

    /** Setup the toolbar */
    private void setupToolBar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(mToolBar);
    }

    /** Setup the tabs */
    private void setupTabs(View view) {
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_nowshowing));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_upcoming));

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
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void loadUpcomingMovies() {
        Timber.d("LoadUpcomingMovies");
    }
}
