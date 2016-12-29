package me.androidbox.flicks.activity;

import android.app.FragmentTransaction;
import android.os.Build;
import android.transition.Transition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.widget.ImageView;

import me.androidbox.flicks.R;
import me.androidbox.flicks.model.MovieDetail;
import me.androidbox.flicks.moviedetail.MovieDetailView;
import me.androidbox.flicks.movielist.MovieListView;
import me.androidbox.flicks.movielist.MovieViewHolderPortrait;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MovieViewHolderPortrait.GetMovieListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.activity_main, MovieListView.getNewInstance(), "movielistview");
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onGetMovie(int movieId) {
        /* Create a new MovieDetail as this has not been created yet */
        MovieDetailView movieDetailView =
                (MovieDetailView)getFragmentManager().findFragmentByTag(MovieDetailView.class.getSimpleName());
        if(movieDetailView == null) {
            movieDetailView = MovieDetailView.getNewInstance(movieId);
        }

        /* Replace the fragment with the detail view */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /* Get the MovieListView fragment as this has already been created */
            MovieListView movieListView = (MovieListView)getFragmentManager().findFragmentById(R.id.activity_main);

            Transition changeTransform = TransitionInflater.from(MainActivity.this)
                    .inflateTransition(R.transition.change_image);

            Transition explodeTranform = TransitionInflater.from(MainActivity.this)
                    .inflateTransition(android.R.transition.explode);


            /* Setup the exit transition on the list (first) fragment */
            movieListView.setSharedElementReturnTransition(changeTransform);
            movieListView.setExitTransition(explodeTranform);

            /* Setup enter transition on the detail (second) fragment */
            movieDetailView.setSharedElementEnterTransition(changeTransform);
            movieDetailView.setEnterTransition(explodeTranform);

            /* Find the shared element in list (first) fragment */
            final ImageView ivThumbnail = (ImageView)findViewById(R.id.ivMoviePoster);

            /* replace the fragments */
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_main, movieDetailView, MovieDetailView.class.getSimpleName());
            fragmentTransaction.addToBackStack(MovieDetailView.class.getSimpleName());
            fragmentTransaction.addSharedElement(ivThumbnail, getResources().getString(R.string.transition_poster_image));
            fragmentTransaction.commit();
        }
        else {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_main, movieDetailView, MovieDetailView.class.getSimpleName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        /* If there are fragments on the backstack */
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        }
        else {
            super.onBackPressed();
        }
    }
}
