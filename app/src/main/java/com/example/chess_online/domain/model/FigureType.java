package com.example.chess_online.domain.model;

public enum FigureType {
    BISHOP,
    KING,
    KNIGHT,
    PAWN,
    QUEEN,
    ROOK;

    public static FigureType fromString(String s) {
        if (s.equals("null")) {
            return null;
        }
        return FigureType.valueOf(s);
    }
}
