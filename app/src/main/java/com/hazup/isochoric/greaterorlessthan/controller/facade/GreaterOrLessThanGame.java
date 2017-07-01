package com.hazup.isochoric.greaterorlessthan.controller.facade;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hazup.isochoric.greaterorlessthan.dao.HighScoreDao;
import com.hazup.isochoric.greaterorlessthan.model.ActiveNumber;
import com.hazup.isochoric.greaterorlessthan.model.GameState;
import com.hazup.isochoric.greaterorlessthan.model.Number;
import com.hazup.isochoric.greaterorlessthan.model.Score;
import com.hazup.isochoric.greaterorlessthan.model.UserChoice;

import static java.lang.Thread.sleep;

/**
 * Created by minir on 18/05/2017.
 */

public class GreaterOrLessThanGame {

    private GameState gameState;
    private Number leftNumber;
    private Number rightNumber;
    private int currentNumber;

    private PointsManager pointsManager;

    public GreaterOrLessThanGame(){
        pointsManager = new PointsManager();
    }

    public void startNewGame(){
        gameState = new GameState();
        runGame();
        setNewNumbers();
    }

    public void runGame(){
        gameState.beginGame();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (gameState.isGameOn()){
                        sleep(1000);
                        gameState.decrementTime();
                        if (gameState.getTimeRemaining() <= 0){
                            gameState.endGame();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void userChoiceSwipe(UserChoice choice){
        boolean isAnswerCorrect = evaluateChoice(choice);
        if (isAnswerCorrect){
            gameState.incrementLevel();
            pointsManager.addPoints(gameState.getLevel());
        } else {
            gameState.resetLevel();
        }
        setNewNumbers();
    }

    public boolean evaluateChoice(UserChoice choice){
        Number chosenNumber;
        Boolean result;
        switch (choice){
            case LEFT:
                chosenNumber = leftNumber;
                break;
            case RIGHT:
                chosenNumber = rightNumber;
                break;
            case NONE:
                chosenNumber = new Number();
                break;
            default:
                chosenNumber = null;
        }

        if (chosenNumber == null || chosenNumber.getCondition() == null){
            Log.d("choice", "none was chosen");
            result = checkBothValues();
        } else {
            result = checkValue(chosenNumber);
        }
        return result;
    }

    private boolean checkBothValues(){
        if (!checkValue(leftNumber) && !checkValue(rightNumber)){
            return true;
        } else {
            return false;
        }
    }

    private boolean checkValue(Number number){
        String condition = number.getCondition();
        switch (condition){
            case Number.GREATER_THAN:
                return currentNumber > number.getValue();
            case Number.LESS_THAN:
                return currentNumber < number.getValue();
            default:
                return false;
        }
    }

    public void setNewNumbers(){
        int level = gameState.getLevel();
        ActiveNumber activeNumber = NumberManager.generateGameNumbers(level);
        Log.d("numberGen", "number from the generator is " + activeNumber.getLeftValue().getValue());
        leftNumber = activeNumber.getLeftValue();
        Log.d("numberGen", String.valueOf(leftNumber.getValue()));
        rightNumber = activeNumber.getRightValue();
        currentNumber = activeNumber.getTargetValue();
    }

    public void postScore(SQLiteDatabase db, Score score){
        HighScoreDao highScoreDao = HighScoreDao.getInstance();
        highScoreDao.insertHighScore(db, score);
    }

    public GameState getGameState() {
        return gameState;
    }

    public Number getLeftNumber() {
        return leftNumber;
    }

    public Number getRightNumber() {
        return rightNumber;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public int getPoints(){
        return pointsManager.getPoints();
    }
}