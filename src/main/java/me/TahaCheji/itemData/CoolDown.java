package me.TahaCheji.itemData;

import me.TahaCheji.Main;
import me.TahaCheji.gameData.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

public class CoolDown {

    private final MasterItems masterItems;
    private final GamePlayer gamePlayer;

    public CoolDown(MasterItems masterItems, GamePlayer gamePlayer) {
        this.masterItems = masterItems;
        this.gamePlayer = gamePlayer;
    }

    public boolean ifCanUse() {
        if(Main.getCoolDownHashMap().containsKey(gamePlayer)) {
            gamePlayer.getPlayer().sendMessage(ChatColor.RED + "Cant use that");
            gamePlayer.getPlayer().playSound(gamePlayer.getPlayer(), Sound.BLOCK_GLASS_BREAK, 10, 10);
            return true;
        }
        return false;
    }

    public void addPlayerToCoolDown() {
        Main.getCoolDownHashMap().put(gamePlayer, this);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                removePlayerFromCoolDown();
            }
        }, 20L * masterItems.getMasterAbility().getCoolDown()); //20 Tick (1 Second) delay before run() is called
    }

    public void removePlayerFromCoolDown() {
        Main.getCoolDownHashMap().remove(gamePlayer, this);
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public MasterItems getMasterItems() {
        return masterItems;
    }
}
