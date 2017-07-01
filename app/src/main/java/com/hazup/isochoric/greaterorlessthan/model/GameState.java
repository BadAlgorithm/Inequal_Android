package com.hazup.isochoric.greaterorlessthan.model;

/**
 * Created by minir on 18/05/2017.
 */

public class GameState {
    private int level;
    private int timeRemaining;
    private boolean isGameOn;

    public GameState(){
        level = 0;
        timeRemaining = 20;
    }

    public void beginGame(){
        isGameOn = true;
    }

    public boolean isGameOn(){
        return isGameOn;
    }

    public void endGame(){
        isGameOn = false;
    }

    public void incrementLevel(){
        level++;
    }

    public void resetLevel(){
        level = 0;
    }

    public void decrementTime(){
        timeRemaining--;
    }

    public int getTimeRemaining(){
        return timeRemaining;
    }

    public int getLevel() {
        return level;
    }
}
