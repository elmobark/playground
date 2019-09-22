package com.example.playground.activitys.AnimationLab.recycler_view.recycler_view_to_fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.playground.R;

public class RecyclerViewToFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_to_fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, RecyclerViewFragment.newInstance())
                .commit();
    }
}
