package com.example.chess_online.fragment;

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
import com.example.chess_online.domain.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_menu_fragment, container, false);

        //TODO
        List<User> friends = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.rv_friends);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
