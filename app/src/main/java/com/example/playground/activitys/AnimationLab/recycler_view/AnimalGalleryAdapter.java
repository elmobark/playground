package com.example.playground.activitys.AnimationLab.recycler_view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.playground.R;

import java.util.ArrayList;

/**
 * Created by msc10 on 16/02/2017.
 */

public class AnimalGalleryAdapter extends RecyclerView.Adapter<AnimalGalleryAdapter.ImageViewHolder> {

    private final AnimalItemClickListener animalItemClickListener;
    private ArrayList<AnimalItem> animalItems;

    public AnimalGalleryAdapter(ArrayList<AnimalItem> animalItems, AnimalItemClickListener animalItemClickListener) {
        this.animalItems = animalItems;
        this.animalItemClickListener = animalItemClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal_square, parent, false));
    }

    @Override
    public int getItemCount() {
        return animalItems.size();
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final AnimalItem animalItem = animalItems.get(position);
        holder.animalImageView.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(animalItem.imageUrl));
//        Picasso.with(holder.itemView.getContext())
//                .load(animalItem.imageUrl)
//                .into(holder.animalImageView);

        ViewCompat.setTransitionName(holder.animalImageView, animalItem.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animalItemClickListener.onAnimalItemClick(holder.getAdapterPosition(), animalItem, holder.animalImageView);
            }
        });
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView animalImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            animalImageView = (ImageView) itemView.findViewById(R.id.item_animal_square_image);
        }
    }
}
