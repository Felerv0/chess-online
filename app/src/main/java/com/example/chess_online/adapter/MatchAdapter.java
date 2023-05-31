package com.example.chess_online.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chess_online.R;
import com.example.chess_online.cache.UserCache;
import com.example.chess_online.domain.GameState;
import com.example.chess_online.fragment.MatchesFragment;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private List<GameState> parties;
    private Context context;
    private LayoutInflater layoutInflater;
    private MatchesFragment matchesFragment;
    private final String currentUsername = UserCache.getCurrentUser().getUsername();

    public MatchAdapter(List<GameState> parties, Context context, MatchesFragment matchesFragment) {
        this.parties = parties;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.matchesFragment = matchesFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.match_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameState party = parties.get(position);
        holder.tv_username.setText(party.getEnemy(currentUsername));
        holder.match_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICKED_RV", Long.toString(party.getId()));
                //TODO
            }
        });
    }

    @Override
    public int getItemCount() {
        return parties.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_username;
        LinearLayout match_main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image);
            tv_username = itemView.findViewById(R.id.profile_username);
            match_main = itemView.findViewById(R.id.match_main);
        }
    }
}
