package me.androidbox.flicks.activity;

import android.app.FragmentTransaction;
import android.os.Build;
import android.transition.Fade;
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
        Timber.d("onCreate");

        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main, MovieListView.getNewInstance(), MovieListView.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.d("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
    }

    @Override
    public void onGetMovie(ImageView imageView, int movieId) {
        Timber.d("onGetMovie");

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

            /* Setup the exit transition on the list (first) fragment */
            movieDetailView.setSharedElementReturnTransition(changeTransform);
            movieListView.setExitTransition(new Fade());

            /* Setup enter transition on the detail (second) fragment */
            movieDetailView.setSharedElementEnterTransition(changeTransform);
            movieDetailView.setEnterTransition(new Fade());

            /* replace the fragments */
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main, movieDetailView, MovieDetailView.class.getSimpleName())
                    .addToBackStack(MovieDetailView.class.getSimpleName())
                    .addSharedElement(imageView, getResources().getString(R.string.image_transition))
                    .commit();
        }
        else {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main, movieDetailView, MovieDetailView.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        /* If there are fragments on the backstack */
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            Timber.d("onBackPressed");
            getFragmentManager().popBackStackImmediate();
        }
        else {
            super.onBackPressed();
        }
    }
}
