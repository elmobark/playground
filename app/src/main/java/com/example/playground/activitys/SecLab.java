package com.example.playground.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.playground.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.playground.activitys.LabActivity.manipulateColor;

public class SecLab extends AppCompatActivity {
ViewPager pager;
List<Fragment> list = new ArrayList<>();
Integer[] colors = null;
Toolbar toolbar;
TextView precent;
ProgressBar progressBar;
ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec_lab);
        filler();
     colorFiller();
     toolbar = findViewById(R.id.toolbar2);
    pager = findViewById(R.id.secLabPager);
    precent = findViewById(R.id.textView11);
    
    progressBar = findViewById(R.id.progressBar);
    pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    });
    progressBar.setMax(list.size()*100);
    progressBar.setProgress(1);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                Log.d( "onPageScrolled: ","this triggert"+position);
                if(position < (pager.getAdapter().getCount()-1)&& position < colors.length -1){

                    int color = (Integer)argbEvaluator.evaluate(positionOffset,colors[position],colors[position+1]);
                    if (LabActivity.isColorDark(color)){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            View view = getWindow().getDecorView();
                            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        }else{
                            View view = getWindow().getDecorView();

                                view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                        }
                    }
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(manipulateColor(color,0.8f));
                 //   pager.setBackgroundColor(color);
                  toolbar.setBackgroundColor(color);
                    Log.d( "onPageScrolled: ","and this happend"+position+positionOffset);
                }
                if(positionOffset==0.00)return;
                String superprecent=String.valueOf(Double.valueOf((position+positionOffset)+1)).substring(0,4);
                int progessprecent = Integer.parseInt(superprecent.replace(".",""))+1;
                progressBar.setProgress(progessprecent);
//                Log.d(TAG, "onPageScrolled: ");
                precent.setText("%"+superprecent);

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
 private void colorFiller(){
        colors = new Integer[list.size()];
     for (int i = 0; i < colors.length; i++) {
         Random random = new Random();
         colors[i]=Color.parseColor(String.format("#%06x",random.nextInt(0xffffff-1)));
     }
 }
    private void filler(){
        class foo extends Fragment{
            @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                FrameLayout frameLayout = new FrameLayout(getContext());
                CardView card = new CardView(getContext());
                ViewGroup.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                frameLayout.setLayoutParams(param);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params2.setMargins(20,90,20,90);
                card.setLayoutParams(params2);
                card.setElevation(10);
                card.setRadius(10);
                frameLayout.addView(card);
                return frameLayout;
            }
        }
        for (int i = 0; i < 9; i++) {
            list.add(new foo());
        }
    }
}
