package me.TahaCheji.events;

import me.TahaCheji.Main;
import me.TahaCheji.gameData.Game;
import me.TahaCheji.gameData.GamePlayer;
import me.TahaCheji.gameData.PlayerLocation;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.IOException;

public class PlayerDeath implements Listener {


    @EventHandler
    public void onDeath (PlayerDeathEvent e) {
        Player player = e.getEntity();
        Game game = Main.getInstance().getGame(player);
        if (game != null && game.getGamePlayer(player) != null) {
            GamePlayer gamePlayer = game.getGamePlayer(player);
            if (gamePlayer.getPlayer().getUniqueId().toString().contains(player.getUniqueId().toString())) {
                handle(e, game);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMoveDeathInGame(PlayerMoveEvent event) throws IOException {
        Player player = event.getPlayer();
        Game game = Main.getInstance().getGame(player);
        if (game != null && game.getGamePlayer(player) != null) {
            GamePlayer gamePlayer = game.getGamePlayer(player);
            if (!(gamePlayer.getPlayerLocation() == PlayerLocation.GAME)) {
                return;
            }
            if (!(event.getTo().getY() <= 0)) {
                return;
            }
            if (gamePlayer.getPlayer().getUniqueId().toString().contains(player.getUniqueId().toString())) {
                handle(event, game);
            }
        }
    }

    private void handle(PlayerDeathEvent event, Game game) {
        Player player = event.getEntity();

        if (!game.isState(Game.GameState.ACTIVE) && !game.isState(Game.GameState.DEATHMATCH)) {
            return;
        }
        GamePlayer gamePlayer = game.getGamePlayer(player);
        gamePlayer.getPlayer().playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 10, 10);
        game.getPlayers().remove(gamePlayer);
        GamePlayer winner = game.getPlayers().get(0);
        game.setWinner(winner);
    }

    private void handle(PlayerMoveEvent event, Game game) {
        Player player = event.getPlayer();

        if (!game.isState(Game.GameState.ACTIVE) && !game.isState(Game.GameState.DEATHMATCH)) {
            return;
        }
        GamePlayer gamePlayer = game.getGamePlayer(player);
        gamePlayer.getPlayer().playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 10, 10);
        game.getPlayers().remove(gamePlayer);
        GamePlayer winner = game.getPlayers().get(0);
        game.setWinner(winner);
    }
}
