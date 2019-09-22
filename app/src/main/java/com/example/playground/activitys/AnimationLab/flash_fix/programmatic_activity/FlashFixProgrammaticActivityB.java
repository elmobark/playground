package com.example.playground.activitys.AnimationLab.flash_fix.programmatic_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.playground.R;

public class FlashFixProgrammaticActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_fix_programmatic_b);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        supportPostponeEnterTransition();

        TextView detailTextView = (TextView) findViewById(R.id.flash_fix_activity_b_text);
        detailTextView.setText(getString(R.string.starfish_blurb));


            Fade fade = new Fade();
            fade.excludeTarget(R.id.appBar, true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);


        ImageView imageView = (ImageView) findViewById(R.id.flash_fix_activity_b_imageView);

//        Glide.with(this)
//                .load(FlashFixProgrammaticActivityA.STARFISH_IMAGE_URL)
//                .centerCrop()
//                .dontAnimate()
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        supportStartPostponedEnterTransition();
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        supportStartPostponedEnterTransition();
//                        return false;
//                    }
//                })
//                .into(imageView);
    }
}
