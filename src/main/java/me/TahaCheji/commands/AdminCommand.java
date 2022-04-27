package me.TahaCheji.commands;

import me.TahaCheji.GameMain;
import me.TahaCheji.chestData.ChestGUI;
import me.TahaCheji.chestData.ChestRarity;
import me.TahaCheji.gameData.ActiveGameGui;
import me.TahaCheji.gameData.Game;
import me.TahaCheji.gameData.GameMode;
import me.TahaCheji.gameData.PlayerLocation;
import me.TahaCheji.itemData.ItemGui;
import me.TahaCheji.mapUtil.GameMap;
import me.TahaCheji.mapUtil.LocalGameMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminCommand implements CommandExecutor {

    String gameName = null;
    ItemStack gameIcon = null;
    GameMode gameMode = null;
    GameMap gameMap;
    int maxPlayers;

    Game game;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(label.equalsIgnoreCase("swAdmin")) {
            Player player = (Player) sender;
            if(!player.isOp()) {
                player.sendMessage(ChatColor.RED + "You Do Not Have The Permission To Do This Command");
            }
            if(args[0].equalsIgnoreCase("test")) {
                new ChestGUI().getGameGui(ChestRarity.NORMAL, null).open(player);
            }
            if(args[0].equalsIgnoreCase("spawnPoint")) {
                Game game = GameMain.getInstance().getGame(args[1]);
                if(game == null) {
                    return true;
                }
                List<Location> locationList = new ArrayList<>();
                if(args.length == 3 && args[2].equalsIgnoreCase("save")) {
                    game.setPlayerSpawnLocations(locationList);
                    try {
                        game.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                locationList.add(player.getLocation());
            }
            if (args[0].equalsIgnoreCase("edit")) {
                File gameMapsFolder = new File("plugins/SkyWiz/", "maps");
                GameMap gameMap = new LocalGameMap(gameMapsFolder, args[1], false);
                if(args.length == 3 && args[2].equalsIgnoreCase("save")) {
                    GameMap newGameMap = GameMain.getPlayerGameHashMap().get(player);
                    player.teleport(GameMain.getInstance().getLobbyPoint());
                    newGameMap.saveMap();
                    GameMain.getInstance().removeMap(player, newGameMap);
                    return true;
                }
                gameMap.load();
                player.teleport(gameMap.getWorld().getSpawnLocation());
                GameMain.getInstance().getPlayer(player).setPlayerLocation(PlayerLocation.GAME);
                player.setGameMode(org.bukkit.GameMode.CREATIVE);
                GameMain.getInstance().addMap(player, gameMap);
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (args[1].equalsIgnoreCase("Items")) {
                    new ItemGui().getAllItemsGui(GameMain.getInstance().getPlayer(player)).open(player);
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("remove")) {
                GameMain.getInstance().getGame(player).getPlayers().remove(GameMain.getInstance().getPlayer(player));
            }
            if(args[0].equalsIgnoreCase("start")) {
                GameMain.getInstance().getGame(player).adminStart();
            }
            if (args[0].equalsIgnoreCase("active")) {
                new ActiveGameGui().getGameGui().open(player);
            }
            if (args[0].equalsIgnoreCase("troll")) {
                Player trollingPlayer = Bukkit.getPlayer(args[1]);
                if (trollingPlayer == null) {
                    return true;
                }
                if (!trollingPlayer.isOnline()) {
                    return true;
                }
                //create the troll and troll the player
            }
            if (args[0].equalsIgnoreCase("stop")) {
                Game game = GameMain.getInstance().getGame(args[1]);
                if(game == null) {
                    return true;
                }
                game.end();
            }
            if (args[0].equalsIgnoreCase("create")) {
                if (args[1].equalsIgnoreCase("game")) {
                    if (args[2].equalsIgnoreCase("save")) {
                        game = new Game(gameName, gameIcon, gameMode, gameMap, maxPlayers);
                        try {
                            game.save();
                            player.sendMessage(ChatColor.GOLD + "[GameBuilder]: " + "Great now edit the spawn in the yml!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                    if (!(args.length >= 6)) {
                        return true;
                    }
                    gameName = args[2];
                    if (args[3].equalsIgnoreCase("gameIcon")) {
                        gameIcon = player.getItemInHand();
                    }
                    gameMode = GameMode.NORMAL;
                    File gameMapsFolder = new File(GameMain.getInstance().getDataFolder(), "maps");
                    gameMap = new LocalGameMap(gameMapsFolder, args[4], false);
                    player.sendMessage(ChatColor.GOLD + "[GameBuilder]: " + "Great now all you need to do is save your game then add the spawn points! [You can do that in the yml game file]");
                    maxPlayers = Integer.parseInt(args[5]);
                }
            }
        }
        return false;
    }
}
