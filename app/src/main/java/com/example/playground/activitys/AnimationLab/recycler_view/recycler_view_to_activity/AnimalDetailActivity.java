package com.example.playground.activitys.AnimationLab.recycler_view.recycler_view_to_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.playground.R;
import com.example.playground.activitys.AnimationLab.recycler_view.AnimalItem;

public class AnimalDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);
        supportPostponeEnterTransition();

        Bundle extras = getIntent().getExtras();
        AnimalItem animalItem = extras.getParcelable(RecyclerViewActivity.EXTRA_ANIMAL_ITEM);

        ImageView imageView = (ImageView) findViewById(R.id.animal_detail_image_view);
        TextView textView = (TextView) findViewById(R.id.animal_detail_text);
        textView.setText(animalItem.detail);

        int imageUrl = animalItem.imageUrl;

            String imageTransitionName = extras.getString(RecyclerViewActivity.EXTRA_ANIMAL_IMAGE_TRANSITION_NAME);
            imageView.setTransitionName(imageTransitionName);
imageView.setImageDrawable(getResources().getDrawable(imageUrl));

//        Picasso.with(this)
//                .load(imageUrl)
//                .noFade()
//                .into(imageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        supportStartPostponedEnterTransition();
//                    }
//
//                    @Override
//                    public void onError() {
//                        supportStartPostponedEnterTransition();
//                    }
//                });
    }
}
