package me.TahaCheji.events;

import me.TahaCheji.Main;
import me.TahaCheji.gameData.Game;
import me.TahaCheji.gameData.PlayerLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {


    @EventHandler
    public void lobbyDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        Game game = Main.getInstance().getGame(player);
        if(e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            e.setCancelled(true);
        }
        if (game != null) {
            if (game.isState(Game.GameState.LOBBY) || game.isState(Game.GameState.STARTING) || game.isState(Game.GameState.PREPARATION) || game.isState(Game.GameState.ENDING) ||
                    e.getCause() == EntityDamageEvent.DamageCause.FALL || Main.getInstance().getPlayer(player).getPlayerLocation() == PlayerLocation.GAMELOBBY) {
                e.setCancelled(true);
            }
        } else if (!Main.getInstance().isInGame(player)) {
            e.setCancelled(true);
        }

    }
}