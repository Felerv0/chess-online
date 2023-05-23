package com.example.chess_online.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chess_online.R;
import com.example.chess_online.domain.Party;
import com.example.chess_online.fragment.MatchesFragment;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private List<Party> parties;
    private Context context;
    private LayoutInflater layoutInflater;
    private MatchesFragment matchesFragment;

    public MatchAdapter(List<Party> parties, Context context, MatchesFragment matchesFragment) {
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
        //TODO
    }

    @Override
    public int getItemCount() {
        return parties.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image);
            tv_username = itemView.findViewById(R.id.profile_username);
        }
    }
}
