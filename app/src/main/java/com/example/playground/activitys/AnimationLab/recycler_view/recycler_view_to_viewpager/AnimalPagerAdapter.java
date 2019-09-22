package com.example.playground.activitys.AnimationLab.recycler_view.recycler_view_to_viewpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.playground.activitys.AnimationLab.recycler_view.AnimalItem;
import com.example.playground.activitys.AnimationLab.recycler_view.recycler_view_to_fragment.AnimalDetailFragment;

import java.util.List;

public class AnimalPagerAdapter extends FragmentStatePagerAdapter {
    private List<AnimalItem> animalItems;

    AnimalPagerAdapter(FragmentManager fm, List<AnimalItem> animalItems) {
        super(fm);
        this.animalItems = animalItems;
    }

    @Override
    public Fragment getItem(int position) {
        AnimalItem animalItem = animalItems.get(position);
        return AnimalDetailFragment.newInstance(animalItem, animalItem.name);
    }

    @Override
    public int getCount() {
        return animalItems.size();
    }
}
