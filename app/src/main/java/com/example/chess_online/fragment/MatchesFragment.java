package com.example.chess_online.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chess_online.R;
import com.example.chess_online.adapter.MatchAdapter;
import com.example.chess_online.cache.UserCache;
import com.example.chess_online.domain.GameState;
import com.example.chess_online.rest.mapper.AppApiVolley;

import java.util.ArrayList;
import java.util.List;

public class MatchesFragment extends Fragment {
    private RecyclerView recyclerView;
    private final List<GameState> parties = new ArrayList<>();
    private MatchAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matches_menu_fragment, container, false);
        recyclerView = view.findViewById(R.id.rv_matches);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new MatchAdapter(parties, getContext(), MatchesFragment.this);
        recyclerView.setAdapter(adapter);
        getMatches();
        return view;
    }

    public void getMatches() {
        new AppApiVolley(this).getMatches(UserCache.getCurrentUser());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMatches(List<GameState> parties) {
        this.parties.clear();
        this.parties.addAll(parties);
        adapter.notifyDataSetChanged();
    }

    public void makeToastFailedLoad(){
        Toast.makeText(getContext(), "Ошибка при загрузке матчей", Toast.LENGTH_SHORT).show();
    }
}
