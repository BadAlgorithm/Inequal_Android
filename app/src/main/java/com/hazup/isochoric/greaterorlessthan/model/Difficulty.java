package com.hazup.isochoric.greaterorlessthan.model;

/**
 * Created by minir on 18/05/2017.
 */

public class Difficulty {
    private int maxiumValue;
    private int minValue;
    private int maxDifferenceToTarget;

    public Difficulty(int maxiumValue, int minValue, int maxDifferenceToTarget){
        this.maxiumValue = maxiumValue;
        this.minValue = minValue;
        this.maxDifferenceToTarget = maxDifferenceToTarget;
    }

    public int getMaxiumValue() {
        return maxiumValue;
    }

    public int getMaxDifferenceToTarget() {
        return maxDifferenceToTarget;
    }

    public int getMinValue() {
        return minValue;
    }
}
