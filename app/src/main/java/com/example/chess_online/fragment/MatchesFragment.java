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
import com.example.chess_online.adapter.MatchAdapter;
import com.example.chess_online.domain.Party;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MatchesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matches_menu_fragment, container, false);

        //TODO
        List<Party> parties = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.rv_matches);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        MatchAdapter adapter = new MatchAdapter(parties, getContext(), MatchesFragment.this);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
