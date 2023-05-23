package com.example.chess_online.cache;

import com.example.chess_online.domain.User;

public class UserCache {
    private static User CURRENT_USER;

    public static User getCurrentUser() {
        return CURRENT_USER;
    }

    public static void setCurrentUser(User user) {
        CURRENT_USER = user;
    }
}
