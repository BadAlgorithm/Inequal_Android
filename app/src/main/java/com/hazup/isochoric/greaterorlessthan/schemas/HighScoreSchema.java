package com.hazup.isochoric.greaterorlessthan.schemas;

/**
 * Created by minir on 19/05/2017.
 */

public class HighScoreSchema implements Schema {

    public static final String TABLE_NAME = "HIGH_SCORE";

    public static final String COL_USER_NAME = "USER_NAME";
    public static final String COL_POINTS = "POINTS";

    public HighScoreSchema() {
//        Black Constructor
    }

    @Override
    public String createTableQuery() {
        return "CREATE TABLE " + TABLE_NAME +
                " (" +
                COL_USER_NAME + " TEXT, " +
                COL_POINTS + " TEXT " +
                ")";
    }

    @Override
    public String dropTableQuery() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
