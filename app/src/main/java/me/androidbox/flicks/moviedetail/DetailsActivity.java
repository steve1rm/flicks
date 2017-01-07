package me.androidbox.flicks.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.androidbox.flicks.R;

public class DetailsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.activity_detail_container, DetailFragment.newInstance(111), DetailFragment.class.getSimpleName())
                    .commit();
        }
    }
}
