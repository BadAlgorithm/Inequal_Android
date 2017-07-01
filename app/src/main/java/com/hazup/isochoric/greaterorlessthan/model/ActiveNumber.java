package com.hazup.isochoric.greaterorlessthan.model;

/**
 * Created by minir on 18/05/2017.
 */

public class ActiveNumber {
    private Number leftValue;
    private Number rightValue;
    private int targetValue;

    public ActiveNumber (Number leftValue, Number rightValue, int targetValue){
        this.leftValue = leftValue;
        this.rightValue = rightValue;
        this.targetValue = targetValue;
    }

    public Number getLeftValue() {
        return leftValue;
    }

    public Number getRightValue() {
        return rightValue;
    }

    public int getTargetValue() {
        return targetValue;
    }
}
