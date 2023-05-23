package com.example.chess_online.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chess_online.MainActivity;
import com.example.chess_online.R;
import com.example.chess_online.rest.mapper.AppApiVolley;

import org.json.JSONException;

public class LoginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        EditText et_login = view.findViewById(R.id.et_login);
        EditText et_password = view.findViewById(R.id.et_password);
        AppCompatButton btn_login = view.findViewById(R.id.btn_login);
        AppCompatButton btn_toRegistration = view.findViewById(R.id.btn_registrate);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AppApiVolley volley = new AppApiVolley(LoginFragment.this);
                    volley.findUserByUsername(et_login.getText().toString(), et_password.getText().toString());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn_toRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_registrationFragment);
            }
        });
        return view;
    }

    public void login() {
        NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_matchesFragment);
        ((MainActivity) getActivity()).showNavigationBar();
    }

    public void makeToastBadCredentials() {
        Toast.makeText(getContext(), "Неверный логин или пароль", Toast.LENGTH_LONG).show();
    }
}
