package com.example.chainreactiongameapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Player implements Comparable<Player> {

    private String name;
    private String colorName;
    private Integer colorID;

    public Player(String name, String colorName, Integer colorID) {
        this.name = name;
        this.colorName = colorName;
        this.colorID = colorID;
    }

    public String getName() {
        return name;
    }

    public String getColorName() {
        return colorName;
    }

    public Integer getColorID() {
        return colorID;
    }

    @Override
    public int compareTo(Player player) {
        return this.name.compareTo(player.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name) &&
                colorName.equals(player.colorName) &&
                colorID.equals(player.colorID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, colorName, colorID);
    }

    @Override
    public String toString() {
        return "Player : " + name;
    }
}
