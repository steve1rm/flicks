package me.androidbox.flicks.movielist;


import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import butterknife.Unbinder;
import me.androidbox.flicks.R;
import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.model.Movies;
import me.androidbox.flicks.model.Results;
import me.androidbox.flicks.utils.DividerItemDecorator;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListView extends Fragment implements MovieListViewContract {
    @Inject MovieListPresenterImp mMovieListPresenterImp;

    @BindView(R.id.tool_bar) Toolbar mToolBar;
    @BindView(R.id.rvMovieList) RecyclerView mRvMovieList;
    @Nullable @BindView(R.id.swipeContainer) SwipeRefreshLayout mSWipeContainer;

    private Unbinder mUnbinder;
    private MovieListAdapter mMovieListAdapter;
    private List<Results> mMoviesList = Collections.emptyList();

    public MovieListView() {
        // Required empty public constructor
    }

    public static MovieListView getNewInstance() {
        return new MovieListView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.movie_list_view, container, false);

        /* View Injection */
        mUnbinder = ButterKnife.bind(MovieListView.this, view);

        setupToolBar();
        setRecyclerView();

        /* Don't display the tabs on the landscape for the space constaint, the tab selected on the portrait will display the match list of movies */
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupTabs(view);
        }

        /* We won't do pull to refresh in landscape mode */
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupSwipeRefresh();
        }



        return view;
    }

    /** Setup swipe to refresh */
    private void setupSwipeRefresh() {
        mSWipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSWipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimeLineAsync();
            }
        });
    }

    public void fetchTimeLineAsync() {
        mMovieListAdapter.clearMovies();
        mMovieListPresenterImp.loadUpcomingMovies();
        mSWipeContainer.setRefreshing(false);
    }

    /** Setup the toolbar */
    private void setupToolBar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(mToolBar);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);

      //  if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
       // }
    }

    /** Setup the tabs */
    private void setupTabs(View view) {
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_nowshowing));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_upcoming));
    }

    /** Setup recycler view */
    private void setRecyclerView() {
        mMoviesList = new ArrayList<>();
        mMovieListAdapter = new MovieListAdapter(mMoviesList, getActivity());
        mRvMovieList.setAdapter(mMovieListAdapter);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final DividerItemDecorator dividerItemDecorator = new DividerItemDecorator(16);
            mRvMovieList.addItemDecoration(dividerItemDecorator);

            mRvMovieList.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        }
        else {
            mRvMovieList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }
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
    public void loadUpcomingMovies(List<Results> moviesList) {
        Timber.d("LoadUpcomingMovies");
        mMovieListAdapter.updateMovieList(moviesList);
    }
}
