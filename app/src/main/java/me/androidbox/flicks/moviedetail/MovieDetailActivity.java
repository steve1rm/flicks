package me.androidbox.flicks.moviedetail;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import me.androidbox.flicks.R;
import me.androidbox.flicks.movielist.MovieViewHolderPortrait;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final int movieId;
        final Intent intent = getIntent();
        if(intent.hasExtra(MovieViewHolderPortrait.MOVIEID_KEY)) {
            movieId = intent.getIntExtra(MovieViewHolderPortrait.MOVIEID_KEY, -1);

            if(savedInstanceState == null) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.activity_detail_container, MovieDetailView.getNewInstance(movieId), MovieDetailView.TAG);
                fragmentTransaction.commit();
            }
        }
        else {
            Toast.makeText(MovieDetailActivity.this, "Failed to get movie id", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}

