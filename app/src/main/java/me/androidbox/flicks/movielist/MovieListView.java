package me.androidbox.flicks.movielist;


import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.flicks.R;
import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.model.Latest;
import me.androidbox.flicks.model.Results;
import me.androidbox.flicks.utils.DividerItemDecorator;
import me.androidbox.flicks.utils.Network;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListView extends Fragment implements MovieListViewContract {
    private static final String RESTORE_RECYCLER_POSITION_KEY = "restore_recycler_postion";

    @Inject MovieListPresenterImp mMovieListPresenterImp;

    @BindView(R.id.tool_bar) Toolbar mToolBar;
    @BindView(R.id.rvMovieList) RecyclerView mRvMovieList;
    @Nullable @BindView(R.id.swipeContainer) SwipeRefreshLayout mSWipeContainer;

    private Unbinder mUnbinder;
    private MovieListAdapter mMovieListAdapter;
    private List<Results> mMoviesList = Collections.emptyList();

    public MovieListView() {
        // Required empty public constructor
        setArguments(new Bundle());
    }

    public static MovieListView getNewInstance() {

        return new MovieListView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");

        /* Create a translucent status bar */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.d("onSavedInstanceState");
        outState.putParcelable(RESTORE_RECYCLER_POSITION_KEY, mRvMovieList.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            Timber.d("onViewStateRestored");
            mRvMovieList.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(RESTORE_RECYCLER_POSITION_KEY));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.d("onPause");
        Parcelable parcelable = mRvMovieList.getLayoutManager().onSaveInstanceState();

        getArguments().putParcelable(RESTORE_RECYCLER_POSITION_KEY, parcelable);
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume");
        if(getArguments() != null) {
            Timber.d("getArguments() != null");
            Parcelable parcelable = getArguments().getParcelable(RESTORE_RECYCLER_POSITION_KEY);
         //   mRvMovieList.smoothScrollToPosition(6);
            mRvMovieList.getLayoutManager().smoothScrollToPosition(mRvMovieList, null, 8);

       //     mRvMovieList.getLayoutManager().onRestoreInstanceState(parcelable);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Timber.d("onCreateView");

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

        /* If there is no network connect available don't request movies */
        if(Network.isNetworkAvailable(getActivity())) {

            /* Check that we can connect to the moviedb website */
            if(Network.isOnline()) {
                if (mMovieListPresenterImp != null) {
                    Timber.d("mMovieListPresenterImp != null");
                    mMovieListPresenterImp.attachView(MovieListView.this);
                    mMovieListPresenterImp.loadUpcomingMovies();
                 //   mMovieListPresenterImp.getLatestMovie();
                }
            }
            else {
                Toast.makeText(getActivity(), "Cannot reach the moviedb website", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getActivity(),
                    "There is no network connection available\n check you are connected to the internet",
                    Toast.LENGTH_LONG).show();

            /* Load data from storage */
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.d("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void loadNowPlayingMovies(List<Results> movieList) {
        Timber.d("loadNowPlayingMovies: %s", movieList.size());
        mMovieListAdapter.updateMovieList(movieList);
    }

    @Override
    public void loadUpcomingMovies(List<Results> moviesList) {
        Timber.d("LoadUpcomingMovies");
        mMovieListAdapter.updateMovieList(moviesList);
    }

    @Override
    public void loadLatestMovie(Latest latest) {
        Timber.d("loadLatestMovie: %s", latest.getTitle());
    }
}
