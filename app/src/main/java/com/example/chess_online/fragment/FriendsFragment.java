package com.example.chess_online.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chess_online.R;
import com.example.chess_online.adapter.FriendAdapter;
import com.example.chess_online.cache.UserCache;
import com.example.chess_online.domain.Friend;
import com.example.chess_online.domain.User;
import com.example.chess_online.rest.mapper.AppApiVolley;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {
    private RecyclerView recyclerView;
    private final List<Friend> friends = new ArrayList<>();
    private FriendAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_menu_fragment, container, false);

        recyclerView = view.findViewById(R.id.rv_friends);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new FriendAdapter(friends, getContext(), FriendsFragment.this);
        recyclerView.setAdapter(adapter);
        getFriends();
        return view;
    }

    public void getFriends() {
        new AppApiVolley(this).getFriends(UserCache.getCurrentUser());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFriends(List<Friend> friends) {
        this.friends.clear();
        this.friends.addAll(friends);
        adapter.notifyDataSetChanged();
    }
}
