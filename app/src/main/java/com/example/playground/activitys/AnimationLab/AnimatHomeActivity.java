package com.example.playground.activitys.AnimationLab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.playground.R;
import com.example.playground.activitys.AnimationLab.flash_fix.programmatic_activity.FlashFixProgrammaticActivityA;
import com.example.playground.activitys.AnimationLab.flash_fix.xml_activity.FlashFixXmlActivityA;
import com.example.playground.activitys.AnimationLab.recycler_view.recycler_view_to_activity.RecyclerViewActivity;
import com.example.playground.activitys.AnimationLab.recycler_view.recycler_view_to_fragment.RecyclerViewToFragmentActivity;
import com.example.playground.activitys.AnimationLab.recycler_view.recycler_view_to_viewpager.RecyclerViewToViewPagerActivity;
import com.example.playground.activitys.AnimationLab.simple_activity_to_activity.SimpleActivityA;
import com.example.playground.activitys.AnimationLab.simple_fragment_to_fragment.FragmentToFragmentActivity;

public class AnimatHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animat_home);

        Button activityToActivityBtn = (Button) findViewById(R.id.activity_to_activity_btn);
        activityToActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimatHomeActivity.this, SimpleActivityA.class));
            }
        });

        Button fragmentToFragmentBtn = (Button) findViewById(R.id.fragment_to_fragment_btn);
        fragmentToFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimatHomeActivity.this, FragmentToFragmentActivity.class));
            }
        });

//        Button picassoActivityBtn = (Button) findViewById(R.id.picasso_activity_to_activity_btn);
//        picassoActivityBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AnimatHomeActivity.this, PicassoActivityA.class));
//            }
//        });

//        Button picassoFragmentBtn = (Button) findViewById(R.id.picasso_fragment_to_fragment_btn);
//        picassoFragmentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AnimatHomeActivity.this, PicassoFragmentToFragmentActivity.class));
//            }
//        });
//
//        Button glideActivityBtn = (Button) findViewById(R.id.glide_activity_to_activity_btn);
//        glideActivityBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AnimatHomeActivity.this, GlideActivityA.class));
//            }
//        });
//
//        Button glideFragmentBtn = (Button) findViewById(R.id.glide_fragment_to_fragment_btn);
//        glideFragmentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AnimatHomeActivity.this, GlideFragmentToFragmentActivity.class));
//            }
//        });

        Button recyclerViewToAcitivtyBtn = (Button) findViewById(R.id.recycler_view_to_activity_btn);
        recyclerViewToAcitivtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimatHomeActivity.this, RecyclerViewActivity.class));
            }
        });

        Button recyclerViewToFragmentBtn = (Button) findViewById(R.id.recycler_view_to_fragment_btn);
        recyclerViewToFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimatHomeActivity.this, RecyclerViewToFragmentActivity.class));
            }
        });

        Button recyclerViewToViewPagerBtn = (Button) findViewById(R.id.recycler_view_to_view_pager_btn);
        recyclerViewToViewPagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimatHomeActivity.this, RecyclerViewToViewPagerActivity.class));
            }
        });

        Button programmaticFlashFixActivity = (Button) findViewById(R.id.flash_fix_programmatic_activity_btn);
        programmaticFlashFixActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimatHomeActivity.this, FlashFixProgrammaticActivityA.class));
            }
        });

        Button xmlFlashFixActivity = (Button) findViewById(R.id.flash_fix_xml_activity_btn);
        xmlFlashFixActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimatHomeActivity.this, FlashFixXmlActivityA.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
    }
}
