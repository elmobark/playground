package com.example.playground;



import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.VectorDrawable;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.ArrayRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.example.playground.activitys.Frags.FilesListDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executor;

import javax.crypto.NullCipher;

import static com.example.playground.Utils.DemmColor;
import static com.example.playground.Utils.backColor;

import static com.example.playground.Utils.frontColor;
import static com.example.playground.Utils.isDark;


public class Coolad {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;
    public static void Bombee(View v,  String[] Text, @IntDef(value = {LENGTH_INDEFINITE,LENGTH_LONG,LENGTH_SHORT}) @IntRange(from = -2,to = 0) int time){

        Snackbar snackbar = Snackbar.make(v,"",time);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setLeft(18);
        layout.setRight(18);
        layout.removeAllViews();
        layout.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout mainlayout = (LinearLayout) LayoutInflater.from(v.getContext()).inflate(R.layout.coolad_bar_layout,null);
        TextView title = mainlayout.findViewById(R.id.Bambee_title);
        CardView card = mainlayout.findViewById(R.id.Bambee_card);
        card.setCardBackgroundColor(frontColor);
        title.setText(Text[0]);
        title.setTextColor(backColor);
        TextView info =mainlayout.findViewById(R.id.Bambee_info);
        info.setText(Text[1]);
        info.setTextColor(DemmColor);
        layout.setPadding(18,0,18,18);
        layout.addView(mainlayout);
        snackbar.show();
    }
    public static void MayDialog(Context context){
        FilesListDialogFragment dialog = new FilesListDialogFragment();
        Bundle bundle = new Bundle();
        dialog.getDialog().getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        bundle.putInt("item_count",14);
        dialog.setArguments(bundle);
//        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.may_dialog,null);
       // dialog.setContentView(linearLayout);
       // BottomSheetBehavior bottomSheetBehavior = new BottomSheetBehavior();
        //dialog.create();
//        Fragment fragment = new AppCompatDialogFragment();

    dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"");
    }
    public void ran(Context context){


//        VectorDrawableCompat. vectorDrawableCompat = new VectorDrawableCompat();
    }
}