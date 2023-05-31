package com.example.chess_online.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chess_online.R;
import com.example.chess_online.domain.Friend;
import com.example.chess_online.domain.User;
import com.example.chess_online.fragment.FriendsFragment;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private List<Friend> friends;
    private Context context;
    private LayoutInflater layoutInflater;
    private FriendsFragment fragment;

    public FriendAdapter(List<Friend> friends, Context context, FriendsFragment fragment) {
        this.friends = friends;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend user = friends.get(position);
        holder.tv_username.setText(user.getUsername());
        //holder.iv_profile_image.setImageDrawable(R.drawable.profile_icon); TODO
        holder.btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile_image;
        TextView tv_username;
        AppCompatButton btn_play;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile_image = itemView.findViewById(R.id.iv_profile_image);
            tv_username = itemView.findViewById(R.id.tv_username);
            btn_play = itemView.findViewById(R.id.btn_play);
        }
    }
}
