package com.example.chainreactiongameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.chainreactiongameapp.GameAdapter;
import com.example.chainreactiongameapp.R;

import java.util.ArrayList;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GameAdapter adapter;
    private String[][] game_array = new String[10][8];
    private final int[][] game_max_values = new int[10][8];
    private final ArrayList<Player> players = PlayFriends.getPlayers();
    private int pos_me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        recyclerView = findViewById(R.id.game_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(GameActivity.this,8));

        for(int i=0;i<game_max_values.length;i++) {
            for (int j=0;j<game_max_values[0].length;j++) {
                game_array[i][j] = "Player";
                if (i==0 && j==0 || i==game_max_values.length-1 && j==game_max_values[0].length-1 || i==0 && j==game_max_values[0].length-1 || j==0 && i==game_max_values.length-1) {
                    game_max_values[i][j] = 1;
                }else if (i==0 || j==0 || j==game_max_values[0].length-1 || i==game_max_values.length-1){
                    game_max_values[i][j] = 2;
                }else {
                    game_max_values[i][j] = 3;
                }
            }
        }

        adapter = new GameAdapter(GameActivity.this,game_array,game_max_values);
        adapter.setOnItemClickListener(new GameAdapter.onItemClickListener() {
            @Override
            public void onClickListener(int position) {
                if (pos_me==players.size()) {
                    pos_me=0;
                }
                int xPos = position/8;
                int yPos = position%8;
                String player_name = players.get(pos_me).getName();
                if (game_array[xPos][yPos].equals("Player") && game_max_values[xPos][yPos]>=1){
                    game_array[xPos][yPos] = "one" + player_name;
                    pos_me++;
                } else if (game_array[xPos][yPos].contains("one") && game_array[xPos][yPos].contains(player_name) && game_max_values[xPos][yPos]>=2) {
                    game_array[xPos][yPos] = "two" + player_name;
                    pos_me++;
                }else if (game_array[xPos][yPos].contains("two") && game_array[xPos][yPos].contains(player_name) && game_max_values[xPos][yPos]>=3) {
                    game_array[xPos][yPos] = "three" + player_name;
                    pos_me++;
                }
                Log.d("message",Arrays.deepToString(game_array));
                adapter.setGame_array(game_array,position);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}