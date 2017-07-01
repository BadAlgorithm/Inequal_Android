package com.hazup.isochoric.greaterorlessthan.model;

/**
 * Created by minir on 19/05/2017.
 */

public class Player {

    private String playerName;
    private String playerId;

    public Player(String playerName, String playerId){
        this.playerName  = playerName;
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerId() {
        return playerId;
    }
}
