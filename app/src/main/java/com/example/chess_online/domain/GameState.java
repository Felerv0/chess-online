package com.example.chess_online.domain;

import android.util.Log;

import com.example.chess_online.domain.model.Figure;
import com.example.chess_online.domain.model.FigureColor;
import com.example.chess_online.domain.model.FigureMove;
import com.example.chess_online.domain.model.Position;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private long id;
    private List<Figure> figures;
    private FigureColor currentPlayer;
    private FigureMove lastOpponentMove;

    private String user1;
    private String user2;

    private boolean myTurn = false;
    private boolean gameFinished = false;
    private boolean check = false;
    private FigureColor winner = null;
    private String gameFinishedReason = null;

    public boolean isCheck() {
        return check;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public FigureColor getCurrentPlayer() {
        return currentPlayer;
    }

    public FigureMove getLastOpponentMove() {
        return lastOpponentMove;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public FigureColor getWinner() {
        return winner;
    }

    public String getGameFinishedReason() {
        return gameFinishedReason;
    }

    public GameState(JSONObject object) {
        try {
            id = object.getLong("id");
            Log.i("GAME_STATE", object.getString("winner"));
            currentPlayer = FigureColor.fromString(object.getString("currentPlayer"));
            if (object.isNull("lastOpponentMove")) {
                lastOpponentMove = null;
            }
            else {
                lastOpponentMove = new FigureMove(object.getJSONObject("lastOpponentMove"));
            }
            user1 = object.getString("user1");
            user2 = object.getString("user2");
            myTurn = object.getBoolean("myTurn");
            gameFinished = object.getBoolean("gameFinished");
            check = object.getBoolean("check");
            if (object.isNull("winner")) {
                winner = null;
            }
            else {
                winner = FigureColor.fromString(object.getString("winner"));
            }
            gameFinishedReason = object.getString("gameFinishedReason");

            figures = new ArrayList<>();
            for (int i = 0; i < object.getJSONArray("figures").length(); i++) {
                figures.add(new Figure(object.getJSONArray("figures").getJSONObject(i)));
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public long getId() {
        return id;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public String getEnemy(String username) {
        if (username.equals(user1))
            return user2;
        return user1;
    }

    public Figure getFigure(Position position) {
        for (Figure figure : figures) {
            if (figure.getPosition().equals(position)) {
                return figure;
            }
        }
        return null;
    }
}
