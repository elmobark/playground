package com.example.playground.activitys;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.example.playground.DataBox;
import com.example.playground.FabInteractor;
import com.example.playground.PersistentUiController;
import com.example.playground.R;
import com.example.playground.UiState;
import com.example.playground.ViewHider;
import com.example.playground.activitys.AnimationLab.AnimatHomeActivity;
import com.google.android.material.button.MaterialButton;

import java.util.EnumMap;

public class Rlogin extends AppCompatActivity implements PersistentUiController {
    protected static final int HIDER_DURATION = 300;
    private static final String UI_STATE = "APP_UI_STATE";
    private ViewHider fabHider;
    private FabInteractor fabInteractor;
    protected UiState uiState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rlogin);
        uiState = savedInstanceState == null ? UiState.freshState() : savedInstanceState.getParcelable(UI_STATE);
        MaterialButton fab = findViewById(R.id.fab);
        fabHider = ViewHider.of(fab).setDuration(HIDER_DURATION).setDirection(ViewHider.BOTTOM).build();
        fabInteractor = new FabInteractor(fab);

        CardView crd  = findViewById(R.id.crd);
        DataBox.InsertData("aa",crd);
        final FrameLayout header = findViewById(R.id.header);
        final ScrollView srl= findViewById(R.id.srlview);
        Button drawbt  = findViewById(R.id.drawBT);
        drawbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Rlogin.this,AnimLab.class));
                startActivity(new Intent(Rlogin.this, AnimatHomeActivity.class));
            }
        });
        crd.setStateListAnimator(AnimatorInflater.loadStateListAnimator(this, R.animator.slip));
        Button lab = findViewById(R.id.button2);
        lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rlogin.this,SecLab.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            srl.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    header.setSelected( srl.canScrollVertically(-1));
                    if(srl.canScrollVertically(-1))fabHider.show();else fabHider.hide();
                   // fabInteractor.setExtended(!srl.canScrollVertically(-1));
                    Log.d( "onScrollChange: ","Y="+scrollY);
                }
            });
        }
        Button camera = findViewById(R.id.button4);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rlogin.this,CameraAct.class));
            }
        });
    }
    @Override
    public void setFabIcon(@DrawableRes int icon, @StringRes int title) {
        runOnUiThread(() -> {
            if (icon != 0 && title != 0 && fabInteractor != null) fabInteractor.update(icon, title);
        });
    }

    @Override
    public void updateMainToolBar(int menu, CharSequence title) {

    }

    @Override
    public void updateAltToolbar(int menu, CharSequence title) {

    }

    @Override
    public void setFabExtended(boolean expanded) {
        if (fabInteractor != null) fabInteractor.setExtended(expanded);
    }

    @Override
    public void showSnackBar(CharSequence message) {

    }

    @Override
    public void setFabClickListener(@Nullable View.OnClickListener clickListener) {
        fabInteractor.setOnClickListener(clickListener);
    }
    private void updateUI(boolean force, UiState state) {
        uiState = uiState.diff(force,
                state,
                this::toggleFab,
                this::toggleToolbar,
                this::toggleAltToolbar,
                this::toggleBottombar,
                this::toggleSystemUI,
                this::toggleLightNavBar,
                this::setNavBarColor,
                insetFlag -> {},
                this::setFabIcon,
                this::updateMainToolBar,
                this::updateAltToolbar,
                this::setFabClickListener
        );
    }
    @Override
    protected void onStart() {
        super.onStart();
        updateUI(true, uiState);
    }

    @Override
    public void update(UiState state) {
        updateUI(false, state);
    }

    @Override
    public void toggleToolbar(boolean show) {

    }

    @Override
    public void toggleAltToolbar(boolean show) {

    }

    @Override
    public void toggleBottombar(boolean show) {

    }

    @Override
    public void toggleFab(boolean show) {
        if (fabHider == null) return;
        if (show) fabHider.show();
        else fabHider.hide();
    }

    @Override
    public void toggleProgress(boolean show) {

    }

    @Override
    public void toggleSystemUI(boolean show) {

    }

    @Override
    public void toggleLightNavBar(boolean isLight) {

    }

    @Override
    public void setNavBarColor(int color) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(UI_STATE, uiState);
        super.onSaveInstanceState(outState);
    }
}
