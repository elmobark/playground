package com.example.playground.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.playground.CircleProgressBar;
import com.example.playground.R;

public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        SeekBar seekBarProgress, seekBarThickness;

        seekBarProgress =  findViewById(R.id.seekBar_progress);

        seekBarThickness = findViewById(R.id.seekBar_thickness);

        final Button button = findViewById(R.id.button);

        final CircleProgressBar circleProgressBar = findViewById(R.id.custom_progressBar);

        //Using ColorPickerLibrary to pick color for our CustomProgressbar





                circleProgressBar.setColor(Color.GREEN);


        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {



            }

        });

        seekBarProgress.setProgress((int) circleProgressBar.getProgress());

        seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override

            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if(b)

                    circleProgressBar.setProgressWithAnimation(i);

                else

                    circleProgressBar.setProgress(i);

            }



            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {



            }



            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {



            }

        });

        seekBarThickness.setProgress((int) circleProgressBar.getStrokeWidth());

        seekBarThickness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override

            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                circleProgressBar.setStrokeWidth(i);

            }



            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {



            }



            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {



            }

        });
    }
}
