package com.example.playground.activitys.AnimationLab.recycler_view;

import android.content.Context;

import com.example.playground.R;

import java.util.ArrayList;

/**
 * Created by msc10 on 19/02/2017.
 */

public class Utils {

    public static ArrayList<AnimalItem> generateAnimalItems(Context context) {
        ArrayList<AnimalItem> animalItems = new ArrayList<>();
        animalItems.add(new AnimalItem("Dog", context.getString(R.string.dog_blurb), R.drawable.lion));
        animalItems.add(new AnimalItem("Penguin", context.getString(R.string.penguin_blurb), R.drawable.lion));
        animalItems.add(new AnimalItem("Eagle", context.getString(R.string.eagle_blurb), R.drawable.lion));
        animalItems.add(new AnimalItem("Rabbit", context.getString(R.string.rabbit_blurb), R.drawable.lion));
        animalItems.add(new AnimalItem("Dolphin", context.getString(R.string.dolphin_blurb), R.drawable.lion));
        animalItems.add(new AnimalItem("Snek", context.getString(R.string.snek_blurb), R.drawable.lion));
        animalItems.add(new AnimalItem("Seal", context.getString(R.string.seal_blurb), R.drawable.lion));
        animalItems.add(new AnimalItem("Rhino", context.getString(R.string.rhino_blurb), R.drawable.lion));
        animalItems.add(new AnimalItem("Leopard", context.getString(R.string.leopard_blurb), R.drawable.lion));
        animalItems.add(new AnimalItem("Hippo", context.getString(R.string.hippo_blurb), R.drawable.lion));
        return animalItems;
    }
}
