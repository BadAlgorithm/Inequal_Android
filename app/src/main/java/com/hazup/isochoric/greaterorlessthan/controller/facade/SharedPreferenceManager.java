package com.hazup.isochoric.greaterorlessthan.controller.facade;

import android.content.Context;
import android.content.SharedPreferences;

import com.hazup.isochoric.greaterorlessthan.model.Player;

/**
 * Created by minir on 20/05/2017.
 */

public class SharedPreferenceManager {

    public static final String GAME_PREFERENCES = "GAME_PREFERENCES";
    public static final String ACTIVE_USER_KEY = "ACTIVE_USER_NAME";
    public static final String ACTIVE_USER_ID_KEY = "ACTIVE_USER_ID";
    public static final String GAMEPLAY_MODE = "GAME_MODE";

    public static final String ACCEL_INDICATOR = "ACCEL";
    public static final String GESTURE_INDICATOR = "GEST";

    public static void submitLatestUserPref(Player player, Context context) {
        String userName  = player.getPlayerName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACTIVE_USER_KEY, userName);
        editor.apply();
    }

    public static Player getActivePlayer(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString(ACTIVE_USER_KEY, null);
        String userId = sharedPreferences.getString(ACTIVE_USER_ID_KEY, null);
        return new Player(userName, userId);
    }

    public static void submitGameplayMode(String modeSelected, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GAMEPLAY_MODE, modeSelected);
        editor.apply();
    }

    public static String getGameplayMode(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE);
        String selectedMode = sharedPreferences.getString(GAMEPLAY_MODE, GESTURE_INDICATOR);
        return selectedMode;
    }


}
