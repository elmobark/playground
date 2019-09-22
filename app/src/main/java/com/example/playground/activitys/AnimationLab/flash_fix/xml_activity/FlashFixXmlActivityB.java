package com.example.playground.activitys.AnimationLab.flash_fix.xml_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.playground.R;

public class FlashFixXmlActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_fix_xml_b);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        supportPostponeEnterTransition();

        TextView detailTextView = (TextView) findViewById(R.id.flash_fix_activity_b_text);
        detailTextView.setText(getString(R.string.pig_blurb));

        ImageView imageView = (ImageView) findViewById(R.id.flash_fix_activity_b_imageView);

//        Glide.with(this)
//                .load(FlashFixXmlActivityA.PIG_PIC_URL)
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
