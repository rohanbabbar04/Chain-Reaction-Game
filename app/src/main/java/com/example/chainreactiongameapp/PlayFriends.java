package com.example.chainreactiongameapp;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class PlayFriends extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static ArrayList<Player> players;
    private PlayersRecyclerViewAdapter adapter;
    private Integer[] colorsImages = new Integer[]{R.color.black,R.color.blue,R.color.red,R.color.yellow};
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_friends);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Players");
        recyclerView = findViewById(R.id.recyclerView);
        playButton = findViewById(R.id.gamePlayButton);

        players = new ArrayList<>();
        Player player1 = new Player("Player-1","Red",R.color.red);
        Player player2 = new Player("Player-2","Blue",R.color.blue);
        players.add(player1);
        players.add(player2);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlayFriends.this));
        adapter = new PlayersRecyclerViewAdapter(PlayFriends.this, players);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PlayersRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Player player) {
                Bundle bundle = new Bundle();
                bundle.putInt("currentPos",players.indexOf(player));
                Log.d("currentPos",String.valueOf(players.indexOf(player)));
                FragmentManager manager = getSupportFragmentManager();
                AddPlayerFragment addPlayerFragment = new AddPlayerFragment();
                bundle.putString("updateName",player.getName());
                bundle.putInt("updateColorID",player.getColorID());
                addPlayerFragment.setArguments(bundle);
                addPlayerFragment.show(manager,"AddPlayerDialog");
            }
        });

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
                Bundle bundle = new Bundle();
                addPlayerFragment.setArguments(bundle);
                addPlayerFragment.show(manager,"AddPlayerDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addItems(Player player) {
        players.add(player);
        adapter.notifyDataSetChanged();
    }

    public void updateItem(int index,Player player) {
        players.set(index,player);
        adapter.notifyDataSetChanged();
    }

    public void onClickButton(View view) {
        Intent intent = new Intent(PlayFriends.this,GameActivity.class);
        startActivity(intent);
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }
}