package com.hazup.isochoric.greaterorlessthan.utils;

import android.content.Context;
import android.media.SoundPool;

import com.hazup.isochoric.greaterorlessthan.R;

/**
 * Created by minir on 23/05/2017.
 */

public class SoundPoolUtil {

    private SoundPool pool;
    private int[] soundIds;
    public static final int CORRECT = 0;
    public static final int INCORRECT = 1;
    private int loaded;

    public SoundPoolUtil(Context context) {
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(10);
        pool = builder.build();
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded += 1;
            }
        });

        soundIds = new int[2];
        soundIds[CORRECT] = pool.load(context, R.raw.correct_complete_bonus, 0);
        soundIds[INCORRECT] = pool.load(context, R.raw.incorrect_negative_tone, 0);
    }

    private boolean loadIncomplete() {
        return loaded != soundIds.length;
    }

    public void playSound(int soundId) {
        if (loadIncomplete()) return;
        else if (soundId < 0 && soundId >= soundIds.length) return;

        pool.play(soundIds[soundId], 1, 1, 1, 0, 1);
    }

}
