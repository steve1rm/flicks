package me.androidbox.flicks.movielist;


import android.app.Fragment;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TabLayout;
import android.app.LoaderManager;
import android.support.test.espresso.idling.CountingIdlingResource;
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

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.flicks.R;
import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.model.Contact;
import me.androidbox.flicks.model.Latest;
import me.androidbox.flicks.model.Results;
import me.androidbox.flicks.utils.DividerItemDecorator;
import me.androidbox.flicks.utils.Network;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListView extends Fragment implements MovieListViewContract, LoaderManager.LoaderCallbacks<List<Results>> {
    public static final String TAG = MovieListView.class.getSimpleName();

    private static final String RESTORE_RECYCLER_POSITION_KEY = "restore_recycler_postion";
    /**
     * Waits for the movies to be downloaded before espresso will start the test
     */
    private CountingIdlingResource mCountingIdlingResource;
    private static final String IDLE_RES_WAIT_FOR_MOVIE_LOAD = "idle_res_wait_for_movie_load";

    @Inject MovieListPresenterImp mMovieListPresenterImp;

    @BindView(R.id.tool_bar) Toolbar mToolBar;
    @BindView(R.id.rvMovieList) RecyclerView mRvMovieList;
    @Nullable @BindView(R.id.swipeContainer) SwipeRefreshLayout mSWipeContainer;

    private Unbinder mUnbinder;
    private MovieListAdapter mMovieListAdapter;
    private List<Results> mMoviesList = Collections.emptyList();
    private static final int LOADER_ID = 1;
    private static final String LOADER_MOVIE_KEY = "loader_movie_key";

    public MovieListView() {
        // Required empty public constructor
        setArguments(new Bundle());
    }

    public static MovieListView getNewInstance() {

        return new MovieListView();
    }

    @Override
    public void onLoadFinished(Loader<List<Results>> loader, List<Results> results) {
        Timber.d("onLoadFinished size: %d", results.size());

        if(loader.getId() == LOADER_ID) {
            if(mMovieListAdapter.getItemCount() == 0) {
                mMovieListAdapter.addFreshMovies(results);
            }
        }
    }

    @Override
    public Loader<List<Results>> onCreateLoader(int i, Bundle bundle) {
        Timber.d("onCreateLoader %d", i);
        List<Results> movieResults = Collections.emptyList();

        if(bundle != null) {
            movieResults = Parcels.unwrap(bundle.getParcelable(LOADER_MOVIE_KEY));
        }

        return new MovieListLoader(getActivity(), movieResults);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        Timber.d("onLoaderReset");
        if(loader.getId() == LOADER_ID) {
            mMovieListAdapter.clearMovies();
        }
    }

    @VisibleForTesting
    public CountingIdlingResource getIdingResource() {
        return mCountingIdlingResource;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");

        mCountingIdlingResource = new CountingIdlingResource(IDLE_RES_WAIT_FOR_MOVIE_LOAD);

        /* Create a translucent status bar */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.d("onSavedInstanceState");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Timber.d("onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.d("onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.d("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume %d", mMovieListAdapter.getItemCount());
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
/*
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupTabs(view);
        }
*/

        /* We won't do pull to refresh in landscape mode */
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupSwipeRefresh();
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Timber.d("onActivityCreated");

        DaggerInjector.getsAppComponent().inject(MovieListView.this);

        /* If there is no network connect available don't request movies */
        if(Network.isNetworkAvailable(getActivity())) {

            /* Check that we can connect to the moviedb website */
            if(Network.isOnline()) {
                if (mMovieListPresenterImp != null) {
                    Timber.d("mMovieListPresenterImp != null");
                    /* Don't get movies if we are already attached to prevent constant network requests */
                    if(!mMovieListPresenterImp.isAttached()) {
                        Timber.d("Lets get some movies");
                        mMovieListPresenterImp.attachView(MovieListView.this);
                        /* Start the espresso idling resouce counter to indicate a background operation has started */
                        mCountingIdlingResource.increment();

                        mMovieListPresenterImp.loadUpcomingMovies();
                        //   mMovieListPresenterImp.getLatestMovie();
                    }
                    else {
                        Timber.d("Already attached no need for another network request");
                    }
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
//        ContactsAdapter mContactsAdapter = new ContactsAdapter(getActivity(), Contact.getContacts());
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
    public void onDestroyView() {
        super.onDestroyView();
        Timber.d("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
        mUnbinder.unbind();
        mCountingIdlingResource = null;

        if(mMovieListPresenterImp.isAttached()) {
            Timber.d("detach view");
            mMovieListPresenterImp.detachView();
        }
    }

    @Override
    public void loadNowPlayingMovies(List<Results> movieList) {
        Timber.d("loadNowPlayingMovies: %s", movieList.size());
        mMovieListAdapter.updateMovieList(movieList);
        /* Decrement the espresso to indicate that the movies have loaded and espresso can start the UI test */
        mCountingIdlingResource.decrement();

        /* We have new movies add this to the loader */
        Bundle bundle = new Bundle();
        bundle.putParcelable(LOADER_MOVIE_KEY, Parcels.wrap(movieList));
        getLoaderManager().restartLoader(LOADER_ID, bundle, MovieListView.this);
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
