package com.example.chess_online.domain.model;

import android.util.Log;

import com.example.chess_online.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Figure {
    private Position position;
    private FigureColor color;
    private FigureType figureType;
    private List<Position> validMoves;

    public static Map<FigureColor, Map<FigureType, Integer>> FIGURE_IMAGES = Map.of(
            FigureColor.WHITE, Map.of(
                    FigureType.BISHOP, R.drawable.white_bishop,
                    FigureType.KING, R.drawable.white_king,
                    FigureType.KNIGHT, R.drawable.white_knight,
                    FigureType.PAWN, R.drawable.white_pawn,
                    FigureType.QUEEN, R.drawable.white_queen,
                    FigureType.ROOK, R.drawable.white_rook),
            FigureColor.BLACK, Map.of(
                    FigureType.BISHOP, R.drawable.black_bishop,
                    FigureType.KING, R.drawable.black_king,
                    FigureType.KNIGHT, R.drawable.black_knight,
                    FigureType.PAWN, R.drawable.black_pawn,
                    FigureType.QUEEN, R.drawable.black_queen,
                    FigureType.ROOK, R.drawable.black_rook));

    public Figure(String position, String color, String figureType, List<String> validMoves) {
        this.position = new Position(position);
        this.color = FigureColor.fromString(color);
        this.figureType = FigureType.fromString(figureType);
        List<Position> positions = new ArrayList<>();
        for (String s : validMoves) {
            positions.add(new Position(s));
        }
        this.validMoves = positions;
    }

    public Figure(JSONObject object) {
        try {
            position = new Position(object.getString("position"));
            color = FigureColor.fromString(object.getString("color"));
            figureType = FigureType.fromString(object.getString("figureType"));
            if (object.isNull("validMoves")) {
                validMoves = null;
            }
            else {
                validMoves = new ArrayList<>();
                for (int i = 0; i < object.getJSONArray("validMoves").length(); i++) {
                    validMoves.add(new Position(object.getJSONArray("validMoves").getString(i)));
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public Position getPosition() {
        return position;
    }

    public FigureColor getColor() {
        return color;
    }

    public FigureType getFigureType() {
        return figureType;
    }

    public List<Position> getValidMoves() {
        return validMoves;
    }

    public int getImage() {
        return FIGURE_IMAGES.get(color).get(figureType);
    }

}
