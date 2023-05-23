package com.example.chess_online.rest.mapper;

import com.example.chess_online.domain.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserMapper {
    public static User getFromJson(JSONObject jsonObject, String password) throws JSONException {
        return new User(jsonObject.getString("username"), jsonObject.getString("fullname"),
                jsonObject.getString("email"), password);
    }
}
