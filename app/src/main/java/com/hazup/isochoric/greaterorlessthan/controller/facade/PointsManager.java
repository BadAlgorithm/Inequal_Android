package com.hazup.isochoric.greaterorlessthan.controller.facade;

/**
 * Created by minir on 18/05/2017.
 */

public class PointsManager {
    private int points;
    private int[] pointIncrements = new int[]{1, 5, 20, 50, 100, 250, 500, 1000};

    public PointsManager() {
        points = 0;
    }

    public void addPoints(int level) {
        if (level < 2) {
            points += pointIncrements[0];
        } else if (level < 4) {
            points += pointIncrements[1];
        } else if (level < 8) {
            points += pointIncrements[2];
        } else if (level < 15) {
            points += pointIncrements[3];
        } else if (level < 20) {
            points += pointIncrements[4];
        } else if (level < 25) {
            points += pointIncrements[5];
        } else if (level < 50) {
            points += pointIncrements[6];
        } else {
            points += pointIncrements[7];
        }
    }

    public int getPoints(){
        return points;
    }
}
