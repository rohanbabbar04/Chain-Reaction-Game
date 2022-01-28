package com.example.chainreactiongameapp;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayersRecyclerViewAdapter extends RecyclerView.Adapter<PlayersRecyclerViewAdapter.PlayerHolder> {

    private Context context;
    private ArrayList<Player> players;
    private onItemClickListener listener;

    public PlayersRecyclerViewAdapter(Context context, ArrayList<Player> players) {
        this.context = context;
        this.players = players;
    }

    @NonNull
    @NotNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.players_list,parent,false);
        return new PlayerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlayerHolder holder, int position) {
        holder.playerName.setText(players.get(position).getName());
        holder.colorImage.setImageResource(players.get(position).getColorID());
        holder.tapEdit.setText(new SpannableString("Tap to edit"));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public Player getPlayerDetails(int position) {
        return players.get(position);
    }

    public class PlayerHolder extends RecyclerView.ViewHolder {
        private TextView playerName;
        private CircleImageView colorImage;
        private TextView tapEdit;
        public PlayerHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.playerName = itemView.findViewById(R.id.textViewName);
            this.colorImage = itemView.findViewById(R.id.color_image);
            this.tapEdit = itemView.findViewById(R.id.textViewEdit);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener!=null && position!=RecyclerView.NO_POSITION) {
                        listener.onClickListener(players.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onClickListener(Player player);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener=listener;
    }
}
