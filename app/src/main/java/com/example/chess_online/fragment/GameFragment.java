package com.example.chess_online.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.chess_online.R;
import com.example.chess_online.cache.UserCache;
import com.example.chess_online.domain.GameState;
import com.example.chess_online.domain.model.Figure;
import com.example.chess_online.domain.model.Position;
import com.example.chess_online.rest.mapper.AppApiVolley;
import com.example.chess_online.view.BoardView;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment {
    private BoardView boardView;
    private AppCompatButton btn_back;
    private GameState currentState;
    private long match_id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);
        match_id = getArguments().getLong("id");
        Log.i("MATCH_ID", String.valueOf(match_id));
        boardView = (BoardView) view.findViewById(R.id.chess_board);
        btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        boardView.setBoardListener(new BoardView.BoardListener() {
            @Override
            public void onClickPiece(BoardView.Pos pos, boolean isSameLast) {
                if (isSameLast) {
                    return;
                }
                Figure figure = currentState.getFigure(new Position(pos));
                if (figure == null) {
                    return;
                }
                List<Position> validMoves = figure.getValidMoves();
                List<BoardView.Pos> positions = new ArrayList<>();
                if (validMoves == null) {
                    return;
                }
                for (Position position : validMoves) {
                    positions.add(new BoardView.Pos(position));
                }
                boardView.markTiles(positions);
            }

            @Override
            public void onClickTile(BoardView.Pos posPiece, BoardView.Pos posTile) {
                boardView.movePiece(posPiece, posTile);
            }
        });
        new AppApiVolley(this).getMatch(match_id, UserCache.getCurrentUser());

        return view;
    }

    public void refreshBoard() {
        for (Figure figure : currentState.getFigures()) {
            boardView.setPiece(figure.getPosition().getY(), figure.getPosition().getX(),
                    figure.getImage());
        }
    }

    public void setCurrentState(GameState gameState) {
        currentState = gameState;
        refreshBoard();
    }
}
