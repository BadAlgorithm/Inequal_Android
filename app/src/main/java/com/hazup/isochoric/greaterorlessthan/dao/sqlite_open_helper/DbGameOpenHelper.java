package com.hazup.isochoric.greaterorlessthan.dao.sqlite_open_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;

import com.hazup.isochoric.greaterorlessthan.schemas.HighScoreSchema;
import com.hazup.isochoric.greaterorlessthan.schemas.PlayerSchema;
import com.hazup.isochoric.greaterorlessthan.schemas.Schema;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by minir on 19/05/2017.
 */

public class DbGameOpenHelper extends SQLiteOpenHelper {
    private static DbGameOpenHelper instance;

    //Database Information
    private static final String DB_NAME = "GreaterOrLess.db";
    private static final int DB_VERSION = 5;

    private static final List<Schema> tablesList = new ArrayList<>();

    private DbGameOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        tablesList.add(new PlayerSchema());
        tablesList.add(new HighScoreSchema());
    }

    public static synchronized DbGameOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbGameOpenHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Schema schema : tablesList){
            db.execSQL(schema.createTableQuery());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Schema schema : tablesList){
            db.execSQL(schema.dropTableQuery());
        }
        onCreate(db);
    }
}
