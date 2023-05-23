package com.example.chess_online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.chess_online.fragment.FriendsFragment;
import com.example.chess_online.fragment.MatchesFragment;
import com.example.chess_online.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bnv_main);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.bnv_main)
                        .getChildFragmentManager().getFragments().get(0);
                switch (item.getItemId()) {
                    case (R.id.navigation_matches):
                        if (!currentFragment.getClass().equals(MatchesFragment.class))
                            NavHostFragment.findNavController(currentFragment).navigate(R.id.action_loginFragment_to_matchesFragment);
                        return true;
                    case (R.id.navigation_friends):
                        if (!currentFragment.getClass().equals(FriendsFragment.class))
                            NavHostFragment.findNavController(currentFragment).navigate(R.id.action_matchesFragment_to_friendsFragment);
                    case (R.id.navigation_profile):
                        if (!currentFragment.getClass().equals(ProfileFragment.class))
                            NavHostFragment.findNavController(currentFragment).navigate(R.id.action_friendsFragment_to_profileFragment);
                }
                return false;
            }
        });
        hideNavigationBar();
    }

    public void hideNavigationBar() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    public void showNavigationBar() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
}