package com.example.playground.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playground.BackBag.Rbar;
import com.example.playground.Coolad;
import com.example.playground.ItemDecoration;
import com.example.playground.PlayView;
import com.example.playground.R;
import com.example.playground.StickyRecyclerView;
import com.google.android.material.snackbar.Snackbar;

import java.util.zip.Inflater;

import  com.example.playground.Utils;

public class AnimLab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_lab);
        Switch darkswitch = findViewById(R.id.DarkSwitch);
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerSetup();
        Button snackBT = findViewById(R.id.snack);
        final LinearLayout view = findViewById(R.id.DrawV);
        darkswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utils.isDark = isChecked;
              //  FragmentManager fragmentManager = ;
                Coolad.MayDialog(AnimLab.this);
              //  Toast.makeText(AnimLab.this, Utils.isDark+"/"+ Utils.backColor, Toast.LENGTH_SHORT).show();
            }
        });
        ConstraintLayout constraintLayout = findViewById(R.id.Anim_layout);
        constraintLayout.setBackgroundColor(Utils.backColor);
        snackBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //  Coolad.FingerInput(AnimLab.this);
              //  Coolad.Bombee(v,new String[]{"مرحباً مبارك","جاري جلب بقية بياناتك"},Coolad.LENGTH_LONG);
            }
        });
    }
    private void recyclerSetup(){
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new ItemDecoration(200,9));
        class adp extends RecyclerView.Adapter<adp.cardHolder>{
            @NonNull
            @Override
            public cardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                CardView cardView = new CardView(parent.getContext());
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200,200);
                cardView.setLayoutParams(params);
                cardView.setRadius(100);

                cardView.setPadding(9,9,9,9);
                cardView.setCardElevation(9);
                return new cardHolder(cardView);
            }

            @Override
            public void onBindViewHolder(@NonNull cardHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 14;
            }

            class cardHolder extends RecyclerView.ViewHolder{

                public cardHolder(@NonNull View itemView) {
                    super(itemView);
//                    itemView.
//                    itemView.setPadding(9,9,9,9);
                }
            }
        }
        recyclerView.setAdapter(new adp());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    }


}
