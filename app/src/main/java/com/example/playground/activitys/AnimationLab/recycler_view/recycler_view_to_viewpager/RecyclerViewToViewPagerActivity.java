package com.example.playground.activitys.AnimationLab.recycler_view.recycler_view_to_viewpager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.playground.R;

public class RecyclerViewToViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_to_view_pager);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, RecyclerViewFragment.newInstance())
                .commit();
    }
}
