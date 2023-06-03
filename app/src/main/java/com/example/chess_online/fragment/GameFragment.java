package com.example.chess_online.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chess_online.MainActivity;
import com.example.chess_online.R;
import com.example.chess_online.cache.UserCache;
import com.example.chess_online.domain.GameState;
import com.example.chess_online.domain.model.Figure;
import com.example.chess_online.domain.model.FigureMove;
import com.example.chess_online.domain.model.Position;
import com.example.chess_online.rest.mapper.AppApiVolley;
import com.example.chess_online.view.BoardView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment {
    private BoardView boardView;
    private AppCompatButton btn_back;
    private GameState currentState;
    private long match_id;
    private TextView tv_user1;
    private TextView tv_user2;

    private final Handler handler = new Handler();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);
        match_id = getArguments().getLong("id");
        Log.i("MATCH_ID", String.valueOf(match_id));
        boardView = (BoardView) view.findViewById(R.id.chess_board);
        tv_user1 = view.findViewById(R.id.user1_tv);
        tv_user2 = view.findViewById(R.id.user2_tv);
        tv_user1.setText(getArguments().getString("user1"));
        tv_user2.setText(getArguments().getString("user2"));
        btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(
                        getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).
                                getChildFragmentManager().getFragments().get(0)).
                        navigate(R.id.action_gameFragment_to_matchesFragment);
                ((MainActivity)getActivity()).showNavigationBar();
            }
        });
        boardView.setBoardListener(new BoardView.BoardListener() {
            @Override
            public void onClickPiece(BoardView.Pos pos, boolean isSameLast) {
                if (isSameLast) {
                    return;
                }
                if (!currentState.isMyTurn()) {
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
                FigureMove figureMove = new FigureMove(new Position(posPiece), new Position(posTile), null);
                Log.i("MATCH_MOVE", figureMove.getFrom().toString() + " " + figureMove.getTo().toString());
                try {
                    new AppApiVolley(GameFragment.this).applyMove(match_id, figureMove);
                    currentState.setMyTurn(false);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        new AppApiVolley(this).getMatch(match_id, UserCache.getCurrentUser(), true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread(() -> {
                    try {
                        if (!currentState.isMyTurn()) {
                            Log.i("MATCH_GET_MOVE", "CHECK");
                            new AppApiVolley(GameFragment.this).getMatch(match_id, UserCache.getCurrentUser(), false);
                        }
                    } catch (Exception e) {
                        Log.e("MATCH_GET_MOVE", e.toString());
                    } finally {
                        handler.postDelayed(this, 3000);
                    }
                }).start();
            }
        }, 1000);

        return view;
    }

    public void refreshBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardView.removePiece(i, j);
            }
        }
        for (Figure figure : currentState.getFigures()) {
            boardView.setPiece(figure.getPosition().getY(), figure.getPosition().getX(),
                    figure.getImage());
        }
    }

    public void setCurrentState(GameState gameState, boolean isRefresh) {
        currentState = gameState;
        refreshBoard();
//        if (isRefresh) {
//            currentState = gameState;
//            refreshBoard();
//        }
//        else {
//            if (!currentState.isMyTurn() && gameState.isMyTurn()) {
//                currentState = gameState;
//                FigureMove figureMove = currentState.getLastOpponentMove();
//                boardView.movePiece(new BoardView.Pos(figureMove.getFrom()), new BoardView.Pos(figureMove.getTo()));
//            }
//        }
    }
}
