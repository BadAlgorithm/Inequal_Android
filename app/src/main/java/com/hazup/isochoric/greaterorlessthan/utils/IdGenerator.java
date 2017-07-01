package com.hazup.isochoric.greaterorlessthan.utils;

import java.util.UUID;

/**
 * Created by minir on 19/05/2017.
 */

public class IdGenerator {
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
