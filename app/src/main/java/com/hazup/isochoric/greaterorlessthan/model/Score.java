package com.hazup.isochoric.greaterorlessthan.model;

/**
 * Created by minir on 18/05/2017.
 */

public class Score {

    private String points;
    private String player;

    public Score(String points, String player) {
        this.points = points;
        this.player = player;
    }

    public String getPoints() {
        return points;
    }

    public String getPlayer() {
        return player;
    }
}
