package com.example.chainreactiongameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AIGameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AIGameAdapter aiGameAdapter;
    private String[][] game_array = new String[10][8];
    private final int[][] game_max_values = new int[10][8];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aigame);
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
        recyclerView = findViewById(R.id.game_ai_recycler_view);
        aiGameAdapter = new AIGameAdapter(AIGameActivity.this,game_array,game_max_values);
        recyclerView.setLayoutManager(new GridLayoutManager(AIGameActivity.this,8));
        aiGameAdapter.setOnItemClickListener(new AIGameAdapter.onItemClickListener() {
            @Override
            public void onClickListener(int position) {
                int xPos = position/8;
                int yPos = position%8;
                if (chain_break(xPos,yPos,"Player-1")) {
                    game_array[xPos][yPos] = "Player";
                }else {
                    move(xPos, yPos, "Player-1");
                }
//                move(xPos,yPos,"Player-1");
//                set_condition(xPos,yPos,"Player-1");
                while (true) {
                    int aiXPos = (int) (Math.random() * 8);
                    int aiYPos = (int) (Math.random() * 8);
                    if (game_array[aiXPos][aiYPos].contains("AI") || game_array[aiXPos][aiYPos].contains("Player")) {
                        if (chain_break(aiXPos,aiYPos,"AI")) {
                            game_array[aiXPos][aiYPos] = "Player";
                        }else {
                            move(aiXPos, aiYPos, "AI");
                        }
                        break;
                    }
                }
                aiGameAdapter.setGame_array(game_array,position);
            }
        });
        recyclerView.setAdapter(aiGameAdapter);
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
}