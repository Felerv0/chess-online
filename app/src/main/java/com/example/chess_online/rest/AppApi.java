package com.example.chess_online.rest;

import com.example.chess_online.domain.User;

public interface AppApi {
    void findUserByUsername(String username, String password);

    void insertUser(User user);


}
