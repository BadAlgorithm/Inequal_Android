package com.hazup.isochoric.greaterorlessthan.controller.facade;

import android.util.Log;

import com.hazup.isochoric.greaterorlessthan.model.ActiveNumber;
import com.hazup.isochoric.greaterorlessthan.model.Difficulty;
import com.hazup.isochoric.greaterorlessthan.model.Number;

import java.util.Random;

/**
 * Created by minir on 18/05/2017.
 */

public class NumberManager {

    public static ActiveNumber generateGameNumbers(int level) {
        if (level < 2) {
            return generateActiveNumbers(new Difficulty(5, 1, 2), level);
        } else if (level < 4) {
            return generateActiveNumbers(new Difficulty(10, 3, 3), level);
        } else if (level < 8) {
            return generateActiveNumbers(new Difficulty(20, 10, 5), level);
        } else if (level < 15) {
            return generateActiveNumbers(new Difficulty(50, 30, 7), level);
        } else if (level < 20) {
            return generateActiveNumbers(new Difficulty(75, 50, 2), level);
        } else if (level < 25) {
            return generateActiveNumbers(new Difficulty(150, 100, 20), level);
        } else if (level < 50) {
            return generateActiveNumbers(new Difficulty(400, 250, 30), level);
        } else {
            return generateActiveNumbers(new Difficulty(level * 12, level, level / 2), level);
        }
    }

    private static ActiveNumber generateActiveNumbers(Difficulty difficulty, int level) {
        int upperValue = difficulty.getMaxiumValue();
        int lowerValue = difficulty.getMinValue();
        Random random = new Random();
        int generatedValue;
        // Random 1 in 5 chance of the numbers being negative
        if (random.nextInt(5) == 2 && level > 15) {
            generatedValue = -1 * random.nextInt(upperValue - lowerValue) + lowerValue;
        } else {
            generatedValue = random.nextInt(upperValue - lowerValue) + lowerValue;
        }
        Number leftNumber = generateSurroundingNumber(generatedValue, difficulty);
        Number rightNumber = generateSurroundingNumber(generatedValue, difficulty);
        Log.d("numberGen", String.valueOf(generatedValue));
        return new ActiveNumber(leftNumber, rightNumber, generatedValue);
    }

    private static Number generateSurroundingNumber(int targetNumber, Difficulty difficulty) {
        Random random = new Random();
        int maxDifference = difficulty.getMaxDifferenceToTarget();
        int surroundingValue = random.nextInt((targetNumber + maxDifference) - (targetNumber - maxDifference)) + (targetNumber - maxDifference);
        String inequalityVal;
        boolean conditionVal = random.nextBoolean();

        if (conditionVal) {
            inequalityVal = Number.GREATER_THAN;
        } else {
            inequalityVal = Number.LESS_THAN;
        }
        Log.d("numberGen", String.valueOf(surroundingValue));
        return new Number(surroundingValue, inequalityVal);
    }
}
