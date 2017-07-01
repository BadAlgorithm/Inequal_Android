package com.hazup.isochoric.greaterorlessthan.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hazup.isochoric.greaterorlessthan.R;
import com.hazup.isochoric.greaterorlessthan.controller.facade.GreaterOrLessThanGame;
import com.hazup.isochoric.greaterorlessthan.controller.facade.SharedPreferenceManager;
import com.hazup.isochoric.greaterorlessthan.dao.sqlite_open_helper.DbGameOpenHelper;
import com.hazup.isochoric.greaterorlessthan.model.Number;
import com.hazup.isochoric.greaterorlessthan.model.Score;
import com.hazup.isochoric.greaterorlessthan.model.UserChoice;
import com.hazup.isochoric.greaterorlessthan.utils.Background;
import com.hazup.isochoric.greaterorlessthan.utils.GameTimeUtil;
import com.hazup.isochoric.greaterorlessthan.utils.MediaPlayerSingleton;
import com.hazup.isochoric.greaterorlessthan.utils.SoundPoolUtil;
import com.hazup.isochoric.greaterorlessthan.utils.TwitterIntentKeys;

public class GameScreenActivity extends AppCompatActivity {

    private GreaterOrLessThanGame greaterOrLessThanGame;
    private String activePlayerName;

    private TextView currentNumberTextView;
    private TextView leftNumberTextView;
    private TextView rightNumberTextView;
    private TextView remainingTimeTextView;
    private TextView pointsTextView;
    private TextView playerNameView;

    private AccelerometerListener accelerometer;
    private SoundPoolUtil soundPoolUtil;

    private Button twitterBtn;

    private LinearLayout gameOverContainer;

    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        GestureListener gestureListener = new GestureListener();
        GameScreenButtonListener gameScreenButtonListener = new GameScreenButtonListener();

        remainingTimeTextView = (TextView) findViewById(R.id.time_text_view);
        currentNumberTextView = (TextView) findViewById(R.id.target_text_view);
        leftNumberTextView = (TextView) findViewById(R.id.left_number_textview);
        rightNumberTextView = (TextView) findViewById(R.id.right_number_text_view);
        pointsTextView = (TextView) findViewById(R.id.points_text_view);
        gameOverContainer = (LinearLayout) findViewById(R.id.game_over_container);
        twitterBtn = (Button) findViewById(R.id.twitter_btn);
        twitterBtn.setOnClickListener(gameScreenButtonListener);
        Button homeBtn = (Button) findViewById(R.id.game_screen_home_btn);
        homeBtn.setOnClickListener(gameScreenButtonListener);
        playerNameView = (TextView) findViewById(R.id.player_name_text_view);
        gameOverContainer.setVisibility(View.INVISIBLE);
        greaterOrLessThanGame = new GreaterOrLessThanGame();
        greaterOrLessThanGame.startNewGame();
        activePlayerName = SharedPreferenceManager.getActivePlayer(this).getPlayerName();
        soundPoolUtil = new SoundPoolUtil(this);
        String gameplayMode = SharedPreferenceManager.getGameplayMode(this);
        switch (gameplayMode){
            case SharedPreferenceManager.GESTURE_INDICATOR:
                gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);
                break;
            case SharedPreferenceManager.ACCEL_INDICATOR:
                accelerometer = new AccelerometerListener(this);
        }
        setPlayerName();
        updateNumbers();
        triggerTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null){
            accelerometer.registerListener();
        }
        MediaPlayerSingleton.getInstance(this).startMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (accelerometer != null){
            accelerometer.unregisterListener();
        }
        MediaPlayerSingleton.getInstance(this).pauseMusic();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetectorCompat != null){
            this.gestureDetectorCompat.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    private void updateNumbers() {
        String points = String.valueOf(greaterOrLessThanGame.getPoints());
        pointsTextView.setText(getString(R.string.game_heading_str_template, "Points", points));
        currentNumberTextView.setText(String.valueOf(greaterOrLessThanGame.getCurrentNumber()));
        leftNumberTextView.setText(getLeftValString());
        rightNumberTextView.setText(getRightValString());
    }

    private void triggerTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int timeRemaining = greaterOrLessThanGame.getGameState().getTimeRemaining();
                String timeRemainingStr = String.valueOf(timeRemaining);
                remainingTimeTextView.setText(getString(R.string.game_heading_str_template, "Time", timeRemainingStr));
                if (timeRemaining <= 5){
                    remainingTimeTextView.setTextColor(Color.parseColor("#EF5350"));
                } else if (timeRemaining <= 10) {
                    remainingTimeTextView.setTextColor(Color.parseColor("#ffc65e"));
                }
                if (!greaterOrLessThanGame.getGameState().isGameOn()) {
                    endGameTasks();
                } else {
                    handler.postDelayed(this, 500);
                }
            }
        });
    }

    private String getLeftValString() {
        Number leftNumber = greaterOrLessThanGame.getLeftNumber();
        Log.d("leftNumber", "The left value is " + leftNumber);
        String inputString = String.valueOf(leftNumber.getValue()) + " ";
        switch (leftNumber.getCondition()) {
            case Number.GREATER_THAN:
                inputString += "<";
                break;
            case Number.LESS_THAN:
                inputString += ">";
                break;
        }
        return inputString;
    }

    private String getRightValString() {
        Number rightNumber = greaterOrLessThanGame.getRightNumber();
        String inputString = "";
        switch (rightNumber.getCondition()) {
            case Number.GREATER_THAN:
                inputString += "> ";
                break;
            case Number.LESS_THAN:
                inputString += "< ";
                break;
        }
        inputString += String.valueOf(rightNumber.getValue());
        return inputString;
    }

    private void setPlayerName() {
        playerNameView.setText(getString(R.string.game_heading_str_template, "Player", activePlayerName));
    }

    private void endGameTasks() {
        gameOverContainer.setVisibility(View.VISIBLE);
        submitHighScoreToDb();
    }

    private void submitHighScoreToDb() {
        final SQLiteDatabase db = DbGameOpenHelper.getInstance(this).getWritableDatabase();
        int points = greaterOrLessThanGame.getPoints();
        final Score score = new Score(String.valueOf(points), activePlayerName);
        Background.runInBackground(new Runnable() {
            @Override
            public void run() {
                greaterOrLessThanGame.postScore(db, score);
            }
        });
    }

    private void applyChoice(UserChoice choice) {
        boolean result = greaterOrLessThanGame.evaluateChoice(choice);
        if (result){
            soundPoolUtil.playSound(SoundPoolUtil.CORRECT);
        } else {
            soundPoolUtil.playSound(SoundPoolUtil.INCORRECT);
        }
        greaterOrLessThanGame.userChoiceSwipe(choice);
        animateText(choice);
        updateNumbers();
    }

    private void animateText(UserChoice choice) {
        final long translation;
        switch (choice) {
            case RIGHT:
                translation = 50L;
                break;
            case LEFT:
                translation = -50L;
                break;
            case NONE:
                translation = 0;
                break;
            default:
                translation = 0;
        }
        currentNumberTextView.animate()
                .translationX(translation)
                .alpha(0.0f)
                .setDuration(200L)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        greaterOrLessThanGame.setNewNumbers();
                        updateNumbers();
                        currentNumberTextView.setAlpha(1.0f);
                        currentNumberTextView.setTranslationX(-translation);
                    }
                });
    }

    private void postToTwitter() {
        Intent postToTwitterIntent = new Intent(this, PostToTwitterActivity.class);
        postToTwitterIntent.putExtra(TwitterIntentKeys.USER_NAME_KEY, activePlayerName);
        postToTwitterIntent.putExtra(TwitterIntentKeys.SCORE_KEY, greaterOrLessThanGame.getPoints() + "");
        postToTwitterIntent.putExtra(TwitterIntentKeys.TIMESTAMP_KEY, GameTimeUtil.getGameTime());
        startActivity(postToTwitterIntent);
    }

    private void backToHome(){
        Intent goHomeIntent = new Intent(this, MainMenuActivity.class);
        startActivity(goHomeIntent);
        finish();
    }

    private class GameScreenButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.twitter_btn:
                    postToTwitter();
                    break;
                case R.id.game_screen_home_btn:
                    backToHome();
            }
        }
    }

    private class AccelerometerListener implements SensorEventListener {

        private SensorManager mSensorManager;
        private Sensor mSensor;

        private volatile float[] lastXSwipe = new float[]{0, 0};
        private volatile float lastZSwipe;

        private final float xThreshold = 15f;
        private final float zThreshold = 20f;

        public AccelerometerListener(Context context) {
            mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float xComponent = event.values[0];
            float zComponent = event.values[2];

            if (Math.abs(xComponent) > xThreshold || Math.abs(zComponent) > zThreshold) {
                if (Math.abs(xComponent) > Math.abs(zComponent)) {
                    if (lastXSwipe[0] == 0) {
                        lastXSwipe[0] = xComponent;
                    } else if (lastXSwipe[1] == 0) {
                        lastXSwipe[1] = xComponent;
                    }
                    if (lastXSwipe[0] != 0 && lastXSwipe[1] != 0) {
                        if (lastXSwipe[0] > lastXSwipe[1]) {
                            applyChoice(UserChoice.RIGHT);
                        } else if (lastXSwipe[0] < lastXSwipe[1]) {
                            applyChoice(UserChoice.LEFT);
                        }
                    }
                    resetDetectedSwipe(UserChoice.LEFT);
                } else {
                    if (lastZSwipe == 0) {
                        applyChoice(UserChoice.NONE);
                        lastZSwipe = zComponent;
                    }
                    resetDetectedSwipe(UserChoice.NONE);
                }

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        void unregisterListener() {
            mSensorManager.unregisterListener(this);
        }

        void registerListener() {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        private void resetDetectedSwipe(final UserChoice choice) {
            Background.runInBackground(new Runnable() {
                @Override
                public void run() {

                    if (choice == UserChoice.NONE) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        lastZSwipe = 0;
                    } else {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        lastXSwipe[0] = 0;
                        lastXSwipe[1] = 0;
                    }
                }
            });
        }
    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            super.onFling(e1, e2, velocityX, velocityY);
            Log.d("velocity", greaterOrLessThanGame.getGameState().isGameOn() + "");
            if (greaterOrLessThanGame.getGameState().isGameOn()) {
                if (velocityX > 0) {
                    applyChoice(UserChoice.RIGHT);
                } else {
                    applyChoice(UserChoice.LEFT);
                }
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            super.onDoubleTap(e);
            if (greaterOrLessThanGame.getGameState().isGameOn()) {
                applyChoice(UserChoice.NONE);
            }
            return true;
        }
    }
}