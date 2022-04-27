package com.example.chainreactiongameapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.chainreactiongameapp.GameAdapter;
import com.example.chainreactiongameapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GameAdapter adapter;
    private String[][] game_array = new String[10][8];
    private final int[][] game_max_values = new int[10][8];
    private final ArrayList<Player> players = PlayFriends.getPlayers();
    private int pos_me;
    private int turn;
    private HashMap<String, Integer> map;
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClickListener(int position) {
                if (pos_me>=players.size()) {
                    pos_me=0;
                }
                int xPos = position/8;
                int yPos = position%8;
                String player_name = players.get(pos_me).getName();

                if (chain_break(xPos,yPos,player_name)) {
                    game_array[xPos][yPos] = "Player";
                }else {
                    move(xPos, yPos, player_name);
                }
                pos_me++;
                turn++;
                adapter.setGame_array(game_array,position);
                check_game();
            }
        });
        recyclerView.setAdapter(adapter);
    }


    /*
        chain_break(9,7,red)
                |-> chain_break(8,7,red)
     */


    public boolean chain_break(int i,int j,String name) {
        if ((game_max_values[i][j]==1 && game_array[i][j].contains("one")) || (game_max_values[i][j]==2 &&
                game_array[i][j].contains("two")) || (game_max_values[i][j]==3 &&
                game_array[i][j].contains("three"))) {
            if (i-1>=0) {
                chain_break(i-1,j,name);
                set_condition(i-1,j,name);
                move(i - 1, j, name);
            }
            if (j-1>=0) {
                chain_break(i,j-1,name);
                set_condition(i,j-1,name);
                move(i, j - 1, name);
            }
            if (i+1<game_array.length) {
                chain_break(i+1,j,name);
                set_condition(i+1,j,name);
                move(i+1,j,name);
            }
            if (j+1<game_array[0].length) {
                chain_break(i,j+1,name);
                set_condition(i,j+1,name);
                move(i,j+1,name);
            }
            return true;
        }else {
            return false;
        }
    }

    public void move(int xPos,int yPos,String player_name) {
        if (game_array[xPos][yPos].equals("Player") && game_max_values[xPos][yPos]>=1){
            game_array[xPos][yPos] = "one" + player_name;
        } else if (game_array[xPos][yPos].contains("one") && game_array[xPos][yPos].contains(player_name) && game_max_values[xPos][yPos]>=2) {
            game_array[xPos][yPos] = "two" + player_name;
        }else if (game_array[xPos][yPos].contains("two") && game_array[xPos][yPos].contains(player_name) && game_max_values[xPos][yPos]>=3) {
            game_array[xPos][yPos] = "three" + player_name;
        }
    }

    public void set_condition(int i,int j,String name) {
        if (game_array[i][j].contains("one") && !game_array[i][j].contains(name)) {
            game_array[i][j] = game_array[i][j].substring(0,3) + name;
        }else if (game_array[i][j].contains("two") && !game_array[i][j].contains(name)) {
            game_array[i][j] = game_array[i][j].substring(0,3) + name;
        }else if (game_array[i][j].contains("three") && !game_array[i][j].contains(name)) {
            game_array[i][j] = game_array[i][j].substring(0,6) + name;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void check_game() {
        map = new LinkedHashMap<>();
        for (int i=0;i<players.size();i++) {
            map.put(players.get(i).getName(),0);
        }

        for(Player player : players) {
            for (int i=0;i<game_array.length;i++) {
                for (int j=0;j<game_array[i].length;j++) {
                    if (game_array[i][j].contains(player.getName())) {
                        Integer value = map.get(player.getName());
                        value+=1;
                        map.replace(player.getName(),value);
                    }
                }
            }
        }
        Log.d("message", map.toString());
        if (turn>2) {
            for (Player player : players) {
                if(map.get(player.getName())==0) {
                    game_ends();
                }
            }
        }


    }

    public void game_ends() {
        String winner = null;
        for (Player player : players) {
            if(map.get(player.getName())!=0) {
                winner = player.getName();
                break;
            }
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(GameActivity.this);
        dialog.setTitle("Game Over");
        dialog.setMessage(winner + " has won");
        dialog.create();
        dialog.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                Intent intent = new Intent(GameActivity.this, PlayFriends.class);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }).show();
    }
}