package me.androidbox.flicks.moviedetail;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.ImageView;
import android.widget.Toast;

import me.androidbox.flicks.R;
import me.androidbox.flicks.movielist.MovieListView;
import me.androidbox.flicks.movielist.MovieViewHolderPortrait;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final int movieId;
        final Intent intent = getIntent();
        if(intent.hasExtra(MovieViewHolderPortrait.MOVIEID_KEY)) {
            movieId = intent.getIntExtra(MovieViewHolderPortrait.MOVIEID_KEY, -1);
        }
        else {
            Toast.makeText(MovieDetailActivity.this, "Failed to get movie id", Toast.LENGTH_LONG).show();
            return;
        }

        if(savedInstanceState == null) {
            getIntent().hasExtra(MovieViewHolderPortrait.MOVIEID_KEY);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Transition changeTransform = TransitionInflater.from(MovieDetailActivity.this)
                        .inflateTransition(R.transition.change_image);

                Transition explodeTranform = TransitionInflater.from(MovieDetailActivity.this)
                        .inflateTransition(android.R.transition.explode);

                /* Get the fragments */
                MovieListView movieListView = MovieListView.getNewInstance();
                MovieDetailView movieDetailView = MovieDetailView.getNewInstance(movieId);

                /* Setup the exit transition on the list (first) fragment */
                movieListView.setSharedElementReturnTransition(changeTransform);
                movieListView.setExitTransition(explodeTranform);

                /* Setup enter transition on the detail (second) fragment */
                movieDetailView.setSharedElementEnterTransition(changeTransform);
                movieDetailView.setEnterTransition(explodeTranform);

/*
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.activity_detail_container, movieDetailView, "moviedetailview");
                fragmentTransaction.addToBackStack("moviedetailview");
                fragmentTransaction.addSharedElement(mImageView, "image");
                fragmentTransaction.commit();
*/
            }
            else {
                /* Transitions are not supported just load the fragment as normal */
/*
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.activity_detail_container, MovieDetailView.getNewInstance(movieId), "moviedetailview");
                fragmentTransaction.commit();
*/
            }
        }
    }
}
