package com.example.chainreactiongameapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {

    private Context context;
    private String[][] game_array;
    private int[][] max_game_values;
    private int pos_me;
    private final ArrayList<Player> players = PlayFriends.getPlayers();
    private int xPos;
    private int yPos;
    private onItemClickListener listener;
    private int pos = -1;
    private static ReentrantLock lock = new ReentrantLock(true);

    public GameAdapter(Context context, String[][] game_array, int[][] max_game_values) {
        this.context = context;
        this.game_array = game_array;
        this.max_game_values = max_game_values;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.game_activity_view, parent, false);
        return new GameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {

        if (game_array[position / 8][position % 8].equals("Player")) {
            holder.imageView.setImageResource(R.color.black);
            holder.imageView.setColorFilter(context.getResources().getColor(R.color.black));
        }
        for (Player player : players) {
            if (game_array[position / 8][position % 8].contains(player.getName())) {
                holder.imageView.setColorFilter(context.getResources().getColor(player.getColorID()));
            }
            if (game_array[position / 8][position % 8].contains("one")) {
                holder.imageView.setImageResource(R.drawable.ic_one_circle_red);
            } else if (game_array[position / 8][position % 8].contains("two")) {
                holder.imageView.setImageResource(R.drawable.ic_two_circles_red);
            } else if (game_array[position / 8][position % 8].contains("three")) {
                holder.imageView.setImageResource(R.drawable.ic_three_circles_red);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (game_array.length * game_array[0].length);
    }


    public class GameHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public GameHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cellImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Log.d("message", position + "");
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onClickListener(position);
                    }
                }
            });
        }

    }


    public interface onItemClickListener {
        void onClickListener(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void setGame_array(String[][] game_array, int pos) {
        this.game_array = game_array;
        this.pos = pos;
        notifyDataSetChanged();
    }

}
