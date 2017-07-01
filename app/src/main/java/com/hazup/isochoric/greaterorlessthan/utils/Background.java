package com.hazup.isochoric.greaterorlessthan.utils;

/**
 * Created by minir on 19/05/2017.
 */

public class Background {

    public static void runInBackground(Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.start();
    }

}
