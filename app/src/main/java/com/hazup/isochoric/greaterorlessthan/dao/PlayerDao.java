package com.hazup.isochoric.greaterorlessthan.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hazup.isochoric.greaterorlessthan.model.Player;
import com.hazup.isochoric.greaterorlessthan.model.Score;
import com.hazup.isochoric.greaterorlessthan.schemas.HighScoreSchema;
import com.hazup.isochoric.greaterorlessthan.schemas.PlayerSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minir on 19/05/2017.
 */

public class PlayerDao {

    private static PlayerDao instance;

    private PlayerDao() {

    }

    public static PlayerDao getInstance() {
        if (instance == null) {
            instance = new PlayerDao();
        }
        return instance;
    }

    public List<Player> findAllPlayers(SQLiteDatabase db) {
        List<Player> allPlayers = new ArrayList<>();
        db.beginTransaction();
        try {
            Cursor cursor = db.query(PlayerSchema.TABLE_NAME,
                    null,
                    null, null, null, null, PlayerSchema.COL_USER_NAME + " ASC");
            while (cursor.moveToNext()) {
                allPlayers.add(extractPlayerFromCursor(cursor));
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.endTransaction();
        }
        Log.d("projects", allPlayers.size() + "");
        return allPlayers;
    }

    public void insertPlayer(SQLiteDatabase db, Player player) {
        db.beginTransaction();
        try {
            ContentValues contentValues = putInContentValue(player);
            db.insert(PlayerSchema.TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    private Player extractPlayerFromCursor(Cursor cursor) {
        String playerName = cursor.getString(cursor.getColumnIndex(PlayerSchema.COL_USER_NAME));
        String playerId = cursor.getString(cursor.getColumnIndex(PlayerSchema.COL_PLAYER_ID));
        return new Player(playerName, playerId);
    }

    private ContentValues putInContentValue(Player player) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PlayerSchema.COL_PLAYER_ID, player.getPlayerId());
        contentValues.put(HighScoreSchema.COL_USER_NAME, player.getPlayerName());
        return contentValues;
    }
}
