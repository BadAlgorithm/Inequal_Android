package com.hazup.isochoric.greaterorlessthan.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hazup.isochoric.greaterorlessthan.model.Score;
import com.hazup.isochoric.greaterorlessthan.schemas.HighScoreSchema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreDao {

    private static HighScoreDao instance;

    private HighScoreDao() {

    }

    public static HighScoreDao getInstance() {
        if (instance == null) {
            instance = new HighScoreDao();
        }
        return instance;
    }

    public List<Score> findAllScores(SQLiteDatabase db) {
        List<Score> allScores = new ArrayList<>();
        db.beginTransaction();
        try {
            Cursor cursor = db.query(HighScoreSchema.TABLE_NAME,
                    null,
                    null, null, null, null, "CAST(" + HighScoreSchema.COL_POINTS + " AS INTEGER) DESC", "10");
            while (cursor.moveToNext()) {
                allScores.add(extractScoreFromCursor(cursor));
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.endTransaction();
        }

        return allScores;
    }

    public void insertHighScore(SQLiteDatabase db, Score score) {
        db.beginTransaction();
        try {
            ContentValues contentValues = putInContentValue(score);
            db.insert(HighScoreSchema.TABLE_NAME, null, contentValues);
            Log.d("insertScore", "Score inserted successfully");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("insertScore", "Score inserted unsuccessfully");
        } finally {
            db.endTransaction();
        }
    }

    private Score extractScoreFromCursor(Cursor cursor) {
        String playerName = cursor.getString(cursor.getColumnIndex(HighScoreSchema.COL_USER_NAME));
        String points = cursor.getString(cursor.getColumnIndex(HighScoreSchema.COL_POINTS));
        return new Score(points, playerName);
    }

    private ContentValues putInContentValue(Score score) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(HighScoreSchema.COL_USER_NAME, score.getPlayer());
        contentValues.put(HighScoreSchema.COL_POINTS, score.getPoints());
        return contentValues;
    }
}
