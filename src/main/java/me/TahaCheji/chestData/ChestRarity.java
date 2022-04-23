package me.TahaCheji.chestData;

import org.bukkit.ChatColor;

public enum ChestRarity {

    NORMAL(ChatColor.GREEN + "Normal"),
    EPIC(ChatColor.DARK_PURPLE + "Epic"),
    PLAYER_BOOST(ChatColor.YELLOW + "Player Consumable");

    private final String text;

    ChestRarity(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

}
