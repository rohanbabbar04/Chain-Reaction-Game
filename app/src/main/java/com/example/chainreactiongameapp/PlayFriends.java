package com.example.chainreactiongameapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Objects;

public class PlayFriends extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_friends);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Players");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.players_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_players:
                FragmentManager manager = getSupportFragmentManager();
                AddPlayerFragment addPlayerFragment = new AddPlayerFragment();
                addPlayerFragment.show(manager,"AddPlayerDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}