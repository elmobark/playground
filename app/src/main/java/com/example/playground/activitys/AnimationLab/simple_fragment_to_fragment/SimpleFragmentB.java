package com.example.playground.activitys.AnimationLab.simple_fragment_to_fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playground.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragmentB extends Fragment {


    public SimpleFragmentB() {
        // Required empty public constructor
    }

    public static SimpleFragmentB newInstance() {
        return new SimpleFragmentB();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple_fragment_b, container, false);
    }

}
