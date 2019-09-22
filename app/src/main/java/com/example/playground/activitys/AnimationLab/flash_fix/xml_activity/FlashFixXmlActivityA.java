package com.example.playground.activitys.AnimationLab.flash_fix.xml_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.playground.R;

public class FlashFixXmlActivityA extends AppCompatActivity {

    public static final String PIG_PIC_URL = "http://s0.geograph.org.uk/photos/57/76/577604_d3efbef6.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_fix_xml_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageView imageView = (ImageView) findViewById(R.id.flash_fix_xml_activity_a_imageView);
//        Glide.with(this)
//                .load(PIG_PIC_URL)
//                .centerCrop()
//                .into(imageView);

        Button button = (Button) findViewById(R.id.flash_fix_xml_activity_a_btn);
        button.setText(R.string.pig);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashFixXmlActivityA.this, FlashFixXmlActivityB.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(FlashFixXmlActivityA.this,
                                imageView,
                                ViewCompat.getTransitionName(imageView));
                startActivity(intent, options.toBundle());
            }
        });

    }
}
