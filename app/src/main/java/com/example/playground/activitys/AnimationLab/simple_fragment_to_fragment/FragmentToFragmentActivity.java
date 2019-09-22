package com.example.playground.activitys.AnimationLab.simple_fragment_to_fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.playground.R;

public class FragmentToFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_to_fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, SimpleFragmentA.newInstance())
                .commit();
    }
}
