package com.example.chess_online.domain.model;

public enum FigureColor {
    WHITE,
    BLACK;

    public FigureColor change() {
        if (this == WHITE)
            return BLACK;
        return WHITE;
    }

    public static FigureColor fromString(String s) {
        if (s == null)
            return null;
        else if (s.equals("WHITE"))
            return WHITE;
        else
            return BLACK;
    }
}
