package me.TahaCheji.itemData;

import me.TahaCheji.GameMain;
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

    public boolean ifCanUse(MasterItems masterItems) {
        if(GameMain.getCoolDownHashMap().containsKey(masterItems)) {
            gamePlayer.getPlayer().sendMessage(ChatColor.RED + "Cant use that");
            gamePlayer.getPlayer().playSound(gamePlayer.getPlayer(), Sound.BLOCK_GLASS_BREAK, 10, 10);
            return true;
        }
        return false;
    }

    public void addPlayerToCoolDown() {
        GameMain.getCoolDownHashMap().put(masterItems, gamePlayer);
        Bukkit.getScheduler().scheduleSyncDelayedTask(GameMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                removePlayerFromCoolDown();
            }
        }, 20L * (int) masterItems.getMasterAbility().getCoolDown()); //20 Tick (1 Second) delay before run() is called
    }

    public void removePlayerFromCoolDown() {
        GameMain.getCoolDownHashMap().remove(masterItems, gamePlayer);
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public MasterItems getMasterItems() {
        return masterItems;
    }
}
