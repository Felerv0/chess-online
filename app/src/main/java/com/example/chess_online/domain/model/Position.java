package com.example.chess_online.domain.model;

import com.example.chess_online.view.BoardView;

import java.util.Map;
import java.util.Objects;

public class Position {
    public static String LETTERS = "abcdefgh";

    private final int x;
    private final int y;

    public Position(String position) {
        this.x = LETTERS.indexOf(position.charAt(0)) + 1;
        this.y = Integer.parseInt(position.substring(1));
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(BoardView.Pos pos) {
        this.x = pos.getJ() + 1;
        this.y = pos.getI() + 1;
    }

    @Override
    public String toString() {
        return LETTERS.charAt(x - 1) + String.valueOf(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
