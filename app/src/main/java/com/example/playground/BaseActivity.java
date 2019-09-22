package com.example.playground;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.playground.util.ThemeUtil;

import static com.example.playground.util.ThemeUtil.THEME_RED;


/**
 * Created by Pankaj on 03-11-2017.
 */

public class BaseActivity extends AppCompatActivity {
    public static int mTheme = THEME_RED;
    public static boolean mIsNightMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtil.getThemeId(mTheme));
    }


}
