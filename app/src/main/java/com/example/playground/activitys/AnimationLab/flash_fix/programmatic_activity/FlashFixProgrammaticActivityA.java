package com.example.playground.activitys.AnimationLab.flash_fix.programmatic_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.playground.R;

public class FlashFixProgrammaticActivityA extends AppCompatActivity {
    public static final String STARFISH_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/f/f2/Starfish_09_(paulshaffner).jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_fix_programmatic_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


            Fade fade = new Fade();
            fade.excludeTarget(R.id.appBar, true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);


        final ImageView imageView = (ImageView) findViewById(R.id.flash_fix_xml_activity_a_imageView);
//        Glide.with(this)
//                .load(STARFISH_IMAGE_URL)
//                .centerCrop()
//                .into(imageView);

        Button button = (Button) findViewById(R.id.flash_fix_xml_activity_a_btn);
        button.setText(R.string.starfish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashFixProgrammaticActivityA.this, FlashFixProgrammaticActivityB.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(FlashFixProgrammaticActivityA.this,
                                imageView,
                                ViewCompat.getTransitionName(imageView));
                startActivity(intent, options.toBundle());
            }
        });
    }
}
