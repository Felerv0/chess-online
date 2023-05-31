package com.example.chess_online.domain;

public class GameState {
    private long id;
//    private List<FigureDto> figures;
//    private FigureColorDto currentPlayer;
//    private MoveDto lastOpponentMove;

    private String user1;
    private String user2;

//    private boolean myTurn = false;
//    private boolean gameFinished = false;
//    private boolean check = false;
//    private FigureColorDto winner = null;
//    private String gameFinishedReason = null;
//
//    public boolean isCheck() {
//        return check;
//    }
//
//    public boolean isMyTurn() {
//        return myTurn;
//    }

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
}
