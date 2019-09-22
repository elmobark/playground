package com.example.playground;

import android.graphics.Color;

public class Utils {
        public static Boolean isDark = true;

        public static int backColor = isDark?Color.parseColor("#ff212121"):Color.parseColor("#ffffffff");
        public static int frontColor = isDark?Color.parseColor("#ffffffff"):Color.parseColor("#ff212121");
        public static int DemmColor = isDark?Color.parseColor("#ff5a595b"):Color.parseColor("#ffd6d7d7");
}
