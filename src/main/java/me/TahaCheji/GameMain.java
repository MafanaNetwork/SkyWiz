package me.TahaCheji;

import me.TahaCheji.chestData.ItemData;
import me.TahaCheji.commands.AdminCommand;
import me.TahaCheji.commands.MainCommand;
import me.TahaCheji.gameData.*;
import me.TahaCheji.itemData.MasterItems;
import me.TahaCheji.lobbyData.Lobby;
import me.TahaCheji.mapUtil.GameMap;
import me.TahaCheji.util.FileUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import me.TahaCheji.util.Files;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;

public final class GameMain extends JavaPlugin {

    private static GameMain instance;
    private Set<Game> games = new HashSet<>();
    private Set<Game> activeGames = new HashSet<>();
    public Map<Player, Game> playerGameMap = new HashMap<>();
    public static Set<GamePlayer> players = new HashSet<>();
    private static HashMap<Player, GameMap> playerGameHashMap = new HashMap<>();
    private static HashMap<Player, Game> playerCreateGameHashMap = new HashMap<>();
    public static List<GameMap> activeMaps = new ArrayList<>();
    public static Map<String, MasterItems> items = new HashMap();
    public static Map<Integer, MasterItems> itemIDs = new HashMap();
    public static List<MasterItems> allItems = new ArrayList<>();
    private static HashMap<Chest, Inventory> chestGameGui = new HashMap<>();
    private static HashMap<MasterItems, GamePlayer> coolDownHashMap = new HashMap<>();
    public static Map<String, ItemData> blocks = new HashMap();
    public static Map<Integer, ItemData> blocksIDs = new HashMap();
    private static Economy econ = null;

    @Override
    public void onEnable() {
        instance = this;
        File dataFolder = getServer().getWorldContainer();
        File[] files = dataFolder.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.getName().contains("active")) {
                FileUtil.delete(file);
            }
        }
        String packageName = getClass().getPackage().getName();
        for (Class<?> clazz : new Reflections(packageName, ".events").getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        for (Class<?> clazz : new Reflections(packageName).getSubTypesOf(MasterItems.class)) {
            try {
                MasterItems masterWeapons = (MasterItems) clazz.getDeclaredConstructor().newInstance();
                masterWeapons.registerItem();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        for (Class<?> clazz : new Reflections(packageName).getSubTypesOf(ItemData.class)) {
            try {
                ItemData masterWeapons = (ItemData) clazz.getDeclaredConstructor().newInstance();
                masterWeapons.registerBlock();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        getServer().getPluginManager().getPlugin("Citizens").onLoad();
        if(getServer().getPluginManager().getPlugin("Citizens") == null || !Objects.requireNonNull(getServer().getPluginManager().getPlugin("Citizens")).isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!setupEconomy()) {
            System.out.print("No econ plugin found.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        try {
            Files.initFiles();
        } catch (IOException | InvalidConfigurationException e2) {
            e2.printStackTrace();
        }
        for (Game game : GameData.getAllSavedGames()) {
            addGame(game);
        }

        for(Game game : activeGames) {
            game.end();
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(new Lobby().getLobbyPoint());
            players.clear();
            GamePlayer gamePlayer = new GamePlayer(player, PlayerLocation.LOBBY);
            addGamePlayer(gamePlayer);
            player.sendMessage(ChatColor.RED + "It is very recommended for you to re join the server this is a reboot");
        }
        getCommand("swAdmin").setExecutor(new AdminCommand());
        getCommand("sw").setExecutor(new MainCommand());

    }

    @Override
    public void onDisable() {
        for(Game game : activeGames) {
            game.end();
        }
    }

    public static MasterItems getItemFromID(int id) {
        MasterItems item = (MasterItems) itemIDs.get(id);

        return item == null ? (MasterItems) items.get("null") : item;
    }

    public static ItemData getBlockDataID(int id) {
        ItemData item = (ItemData) blocksIDs.get(id);

        return item == null ? (ItemData) blocks.get("null") : item;
    }

    public static void putItem(String name, MasterItems item) {
        items.put(name, item);
        itemIDs.put(item.getUUID(), item);
    }

    public static void putBlock(String name, ItemData item) {
        blocks.put(name, item);
        blocksIDs.put(item.getUUID(), item);
    }

    public static HashMap<MasterItems, GamePlayer> getCoolDownHashMap() {
        return coolDownHashMap;
    }

    public boolean isInGame(Player player) {
        return this.playerGameMap.containsKey(player);
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void addActiveGame(Game game) {
        activeGames.add(game);
    }

    public void removeActiveGame(Game game) {
        activeGames.remove(game);
    }

    public static HashMap<Chest, Inventory> getChestGui() {
        return chestGameGui;
    }

    public Game getActiveGame(String name) {
        Game game = GameData.getGame(name);
        if(activeGames.contains(game)) {
            return game;
        } else {
            return null;
        }
    }

    public Game getGame(String gameName) {
        for (Game game : games) {
            if (game.getName().equalsIgnoreCase(gameName)) {
                return game;
            }
        }

        return null;
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        players.add(gamePlayer);
    }

    public Game getGame(Player player) {
        return this.playerGameMap.get(player);
    }

    public void setGame(Player player, Game game) {
        if (game == null) {
            this.playerGameMap.remove(player);
        } else {
            this.playerGameMap.put(player, game);
        }
    }

    public GamePlayer getPlayer(Player player) {
        GamePlayer gPlayer = null;
        for(GamePlayer gamePlayer : players) {
            if(gamePlayer.getPlayer().getUniqueId().toString().contains(player.getUniqueId().toString())) {
                gPlayer = gamePlayer;
            }
        }
        return gPlayer;
    }

    public static Economy getEcon() {
        return econ;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Location getLobbyPoint() {
        return new Lobby().getLobbyPoint();
    }

    public void addMap (Player player, GameMap gameMap) {
        playerGameHashMap.put(player, gameMap);
    }

    public void removeMap (Player player, GameMap gameMap) {
        playerGameHashMap.remove(player, gameMap);
    }

    public GameMap getMap (Player player) {
        return getPlayerGameHashMap().get(player);
    }

    public Set<Game> getGames() {
        return games;
    }

    public Set<Game> getActiveGames() {
        return activeGames;
    }

    public Map<Player, Game> getPlayerGameMap() {
        return playerGameMap;
    }

    public static Set<GamePlayer> getPlayers() {
        return players;
    }

    public static HashMap<Player, GameMap> getPlayerGameHashMap() {
        return playerGameHashMap;
    }

    public static List<GameMap> getActiveMaps() {
        return activeMaps;
    }

    public static HashMap<Player, Game> getPlayerCreateGameHashMap() {
        return playerCreateGameHashMap;
    }

    public static GameMain getInstance() {
        return instance;
    }
}
