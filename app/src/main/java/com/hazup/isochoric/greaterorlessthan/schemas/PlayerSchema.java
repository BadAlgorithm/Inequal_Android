package com.hazup.isochoric.greaterorlessthan.schemas;

/**
 * Created by minir on 19/05/2017.
 */

public class PlayerSchema implements Schema {

    public static final String TABLE_NAME = "PLAYER";

    public static final String COL_PLAYER_ID = "PLAYER_ID"; // PK
    public static final String COL_USER_NAME = "USER_NAME";

    public PlayerSchema() {
    }

    @Override
    public String createTableQuery() {
        return "CREATE TABLE " + TABLE_NAME +
                " (" +
                COL_PLAYER_ID + " TEXT PRIMARY KEY, " +
                COL_USER_NAME + " TEXT " +
                ")";
    }

    @Override
    public String dropTableQuery() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
