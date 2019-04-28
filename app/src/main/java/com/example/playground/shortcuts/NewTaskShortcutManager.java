package com.example.playground.shortcuts;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;


import androidx.annotation.NonNull;

import com.example.playground.MainActivity;
import com.example.playground.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by karthik on 27/10/16.
 */

public class NewTaskShortcutManager {

    private static final String NEW_TASK_SHORTCUT_ID = "new_task_shortcut_id";
    private static final List<String> SHORTCUT_IDS = Arrays.asList(NEW_TASK_SHORTCUT_ID);

    private NewTaskShortcutManager() { }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    public static void enableInProgressShortcut(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) return;
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);

        Intent newTaskIntent = new Intent(context, MainActivity.class);
        newTaskIntent.setAction(Intent.ACTION_VIEW);
        newTaskIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ShortcutInfo postShortcut
                = new ShortcutInfo.Builder(context, NEW_TASK_SHORTCUT_ID)
                .setShortLabel(context.getString(R.string.app_name))
                .setLongLabel(context.getString(R.string.app_name))
                .setIcon(Icon.createWithResource(context, R.drawable.ic_launcher_foreground))
                .setIntent(newTaskIntent)
                .build();
        shortcutManager.addDynamicShortcuts(Arrays.asList(postShortcut));
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    public static void disableInProgressShortcut(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) return;
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        shortcutManager.disableShortcuts(SHORTCUT_IDS);
    }

}
