package com.hazup.isochoric.greaterorlessthan.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.hazup.isochoric.greaterorlessthan.R;

/**
 * Created by minir on 23/05/2017.
 */

public class MediaPlayerSingleton {

    private static MediaPlayerSingleton instance;

    private MediaPlayer player;

    private MediaPlayerSingleton(Context context) {
        player = MediaPlayer.create(context, R.raw.background_music);
        player.setLooping(true);
    }

    public static MediaPlayerSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MediaPlayerSingleton(context);
        }
        return instance;
    }

    public void startMusic(){
        if (!player.isPlaying()){
            player.start();
        }
    }

    public void pauseMusic(){
        if (player.isPlaying()){
            player.pause();
        }
    }
}
