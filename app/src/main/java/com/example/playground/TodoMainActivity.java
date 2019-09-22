package com.example.playground;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.example.playground.shortcuts.CompletedTasksShortcutManager;
import com.example.playground.shortcuts.InProgressTasksShortcutManager;
import com.example.playground.shortcuts.NewTaskShortcutManager;

import java.util.Arrays;
import java.util.Collections;


public class TodoMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_main);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
                ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
                Intent in = new Intent(TodoMainActivity.this,MainActivity.class);
                in.setAction(Intent.ACTION_VIEW);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this,"newTashShortCutId")
                        .setRank(0).setShortLabel("smallText").setIcon(Icon.createWithResource(this,R.drawable.ic_launcher_foreground))
                        .setLongLabel("LongText")
                        .setIntent(in)
                        .build();

                shortcutManager.addDynamicShortcuts(Collections.singletonList(shortcutInfo));
            }

        //NewTaskShortcutManager.enableInProgressShortcut(this);
        //CompletedTasksShortcutManager.enableCompletedShortcut(this);
        //InProgressTasksShortcutManager.enableInProgressShortcut(this);
    }
}
