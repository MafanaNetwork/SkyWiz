package me.TahaCheji.chestData;


import me.TahaCheji.Main;
import me.TahaCheji.gameData.Game;
import me.TahaCheji.gameData.GamePlayer;
import me.TahaCheji.gameData.PlayerLocation;
import me.TahaCheji.util.NBTUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;
import java.util.UUID;


public class ChestInteract implements Listener {

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Game game = Main.getInstance().getGame(player);
        if (game != null && game.getGamePlayer(player) != null) {
            if (game.isState(Game.GameState.LOBBY) || game.isState(Game.GameState.PREPARATION) || game.isState(Game.GameState.STARTING) || Main.getInstance().getPlayer(player).getPlayerLocation() == PlayerLocation.GAMELOBBY) {
                event.setCancelled(true);
                return;
            }

            GamePlayer gamePlayer = game.getGamePlayer(player);
                if (gamePlayer.getPlayer() == player) {
                    handle(event, game);
                    event.setCancelled(true);
                }
            }
    }

    private void handle(PlayerInteractEvent event, Game game) {
        if (event.hasBlock() && event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Chest) {
            Chest chest = (Chest) event.getClickedBlock().getState();

            if (game.getOpened().contains(chest) || game.getEpicItems().size() == 0 || game.getNormalItems().size() == 0 || game.getPlayerBoostItems().size() == 0) {
                Main.getChestGui().get(chest).getGui().open(event.getPlayer());
                return;
            }

            chest.getBlockInventory().clear();
            Block block = chest.getLocation().set(chest.getX(), chest.getY() - 1, chest.getZ()).getBlock();
            if(block.getType() == Material.GREEN_WOOL) {
                new ChestGUI().getGameGui(ChestRarity.NORMAL, chest).open(event.getPlayer());
            }
            if(block.getType() == Material.PURPLE_WOOL) {
                new ChestGUI().getGameGui(ChestRarity.PLAYER_BOOST, chest).open(event.getPlayer());
            }
            if(block.getType() == Material.BLACK_WOOL) {
                new ChestGUI().getGameGui(ChestRarity.EPIC, chest).open(event.getPlayer());
            }

            game.getOpened().add(chest);
        }
    }

}


