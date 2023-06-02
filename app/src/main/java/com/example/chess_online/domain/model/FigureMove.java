package com.example.chess_online.domain.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FigureMove {
    private Position from;
    private Position to;
    private FigureType promotion;

    public FigureMove(Position from, Position to, FigureType promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }

    public FigureMove(JSONObject jsonObject) {
        try {
            from = new Position(jsonObject.getString("from"));
            to = new Position(jsonObject.getString("to"));
            if (jsonObject.isNull("promotion")) {
                promotion = null;
            }
            else {
                promotion = FigureType.fromString(jsonObject.getString("promotion"));
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public FigureType getPromotion() {
        return promotion;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();

        object.put("from", from.toString());
        object.put("to", to.toString());
        if (promotion == null) {
            object.put("promotion", JSONObject.NULL);
        }
        else  {
            object.put("promotion", promotion.toString());
        }
        return object;
    }
}
