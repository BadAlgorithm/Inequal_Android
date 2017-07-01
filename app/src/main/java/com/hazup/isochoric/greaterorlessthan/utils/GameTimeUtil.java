package com.hazup.isochoric.greaterorlessthan.utils;

import android.provider.CalendarContract;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by minir on 23/05/2017.
 */

public class GameTimeUtil {
    public static String getGameTime(){
        Calendar calendar = Calendar.getInstance();
        String period = "am";
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMin = calendar.get(Calendar.MINUTE);
        if (currentHour > 12){
            currentHour -= 12;
            period = "pm";
        }
        return String.format(Locale.ENGLISH,"%1d:%2d %s", currentHour, currentMin, period);
    }
}
