package com.example.playground.activitys.AnimationLab.recycler_view.recycler_view_to_fragment;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.playground.R;
import com.example.playground.activitys.AnimationLab.recycler_view.AnimalItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimalDetailFragment extends Fragment {
    private static final String EXTRA_ANIMAL_ITEM = "animal_item";
    private static final String EXTRA_TRANSITION_NAME = "transition_name";

    public AnimalDetailFragment() {
        // Required empty public constructor
    }
    public static AnimalDetailFragment newInstance(AnimalItem animalItem, String transitionName) {
        AnimalDetailFragment animalDetailFragment = new AnimalDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ANIMAL_ITEM, animalItem);
        bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
        animalDetailFragment.setArguments(bundle);
        return animalDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();

            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animal_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnimalItem animalItem = getArguments().getParcelable(EXTRA_ANIMAL_ITEM);
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);

        TextView detailTextView = (TextView) view.findViewById(R.id.animal_detail_text);
        detailTextView.setText(animalItem.detail);

        ImageView imageView = (ImageView) view.findViewById(R.id.animal_detail_image_view);

            imageView.setTransitionName(transitionName);
            imageView.setImageDrawable(getResources().getDrawable(animalItem.imageUrl));
        startPostponedEnterTransition();


    }
}
