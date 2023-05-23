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
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chess_online.R;
import com.example.chess_online.domain.User;
import com.example.chess_online.rest.mapper.AppApiVolley;

public class RegistrationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_fragment, container, false);
        EditText et_login = view.findViewById(R.id.et_login);
        EditText et_email = view.findViewById(R.id.et_email);
        EditText et_fullname = view.findViewById(R.id.et_name);
        EditText et_password = view.findViewById(R.id.et_password);
        AppCompatButton btn_registrate = view.findViewById(R.id.btn_registrate);
        AppCompatButton btn_toLogin = view.findViewById(R.id.btn_login);
        btn_registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppApiVolley(RegistrationFragment.this).insertUser(
                        new User(et_login.getText().toString(), et_fullname.getText().toString(),
                                et_email.getText().toString(), et_password.getText().toString()));
            }
        });
        btn_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(RegistrationFragment.this).navigate(R.id.action_registrationFragment_to_loginFragment);
            }
        });
        return view;
    }
    public void signIn(){
        NavHostFragment.findNavController(RegistrationFragment.this)
                .navigate(R.id.action_registrationFragment_to_loginFragment);
    }
    public void makeToastFailedRegistration(){
        Toast.makeText(getContext(), "Ошибка при регистрации", Toast.LENGTH_SHORT).show();
    }
}