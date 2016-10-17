package me.androidbox.flicks.moviedetail;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.androidbox.flicks.R;
import me.androidbox.flicks.movielist.MovieViewHolderPortrait;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            getIntent().hasExtra(MovieViewHolderPortrait.MOVIEID_KEY);
            int movieId = getIntent().getIntExtra(MovieViewHolderPortrait.MOVIEID_KEY, -1);
            fragmentTransaction.add(R.id.activity_detail_container, MovieDetailView.getInstance(movieId), "moviedetailview");
            fragmentTransaction.commit();
        }
    }
}
