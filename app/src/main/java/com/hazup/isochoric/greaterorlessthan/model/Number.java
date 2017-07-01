package com.hazup.isochoric.greaterorlessthan.model;

/**
 * Created by minir on 18/05/2017.
 */

public class Number {
    private String condition;
    private int value;

    public static final String GREATER_THAN = "GT";
    public static final String LESS_THAN = "LT";

    public Number(){
    }

    public Number(int value, String condition){
        this.value = value;
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public int getValue() {
        return value;
    }
}
