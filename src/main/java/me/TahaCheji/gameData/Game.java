package me.TahaCheji.gameData;


import me.TahaCheji.Main;
import me.TahaCheji.chestData.EpicItems;
import me.TahaCheji.chestData.LootItem;
import me.TahaCheji.chestData.NormalItems;
import me.TahaCheji.chestData.PlayerBoostItems;
import me.TahaCheji.lobbyData.Lobby;
import me.TahaCheji.mapUtil.GameMap;
import me.TahaCheji.scoreboards.InGameScoreBoard;
import me.TahaCheji.scoreboards.LobbyScoreBoard;
import me.TahaCheji.tasks.GameCountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.*;

public class Game implements GameManager {

    private List<GamePlayer> players = new ArrayList<>();
    private List<Location> playerSpawnLocations = new ArrayList<>();

    private final String name;
    private final ItemStack gameIcon;
    private final GameMode gameMode;
    private final GameMap map;
    private Location lobbySpawn;

    private GameState gameState = GameState.LOBBY;
    private boolean movementFrozen = false;
    private int gameTime = 450;

    private List<GamePlayer> activePlayers = new ArrayList<>();
    private Map<GamePlayer, Location> gamePlayerToSpawnPoint = new HashMap<>();

    private Set<Chest> opened = new HashSet<>();

    public InGameScoreBoard inGameScoreBoard = new InGameScoreBoard();
    public GameCountdownTask gameCountdownTask = null;

    public Game(String name, ItemStack gameIcon, GameMode gameMode, GameMap map, List<Location> playerSpawnLocations, Location lobbySpawn) {
        this.name = name;
        this.gameIcon = gameIcon;
        this.gameMode = gameMode;
        this.map = map;
        this.playerSpawnLocations = playerSpawnLocations;
        this.lobbySpawn = lobbySpawn;
    }

    public Game(String name, ItemStack gameIcon, GameMode gameMode, GameMap map) {
        this.name = name;
        this.gameIcon = gameIcon;
        this.gameMode = gameMode;
        this.map = map;
    }


    @Override
    public void playerJoin(GamePlayer gamePlayer) {
        if (isState(GameState.LOBBY) || isState(GameState.ACTIVE)) {
            if (activePlayers.size() == 4) {
                gamePlayer.sendMessage(ChatColor.GOLD + "[Game Manager] " + "Error: This game is active.");
                return;
            }
        }
        if (!map.isLoaded()) {
            map.load();
            World world = map.getWorld();
            for (Location location : playerSpawnLocations) {
                location.setWorld(world);
            }
            lobbySpawn.setWorld(world);
        }
        if (Main.getInstance().isInGame(gamePlayer.getPlayer())) {
            gamePlayer.sendMessage(ChatColor.RED + "[Game Manager] " + "You are already in a game");
            return;
        }
        activePlayers.add(gamePlayer);
        players.add(gamePlayer);
        gamePlayer.sendMessage(ChatColor.GOLD + "[Game Manager] " + "[" + activePlayers.size() + "/" + "4" + "]");
        gamePlayer.setPlayerLocation(PlayerLocation.GAMELOBBY);
        gamePlayer.getPlayer().getInventory().clear();
        gamePlayer.getPlayer().getInventory().setArmorContents(null);
        gamePlayer.getPlayer().setGameMode(org.bukkit.GameMode.ADVENTURE);
        gamePlayer.getPlayer().setHealth(gamePlayer.getPlayer().getMaxHealth());
        gamePlayer.teleport(lobbySpawn);
        Main.getInstance().setGame(gamePlayer.getPlayer(), this);
        inGameScoreBoard.setGameScoreboard(gamePlayer);
        setState(GameState.LOBBY);
        if (activePlayers.size() == 2 && !isState(GameState.STARTING)) {
            setState(GameState.STARTING);
            inGameScoreBoard.updateScoreBoard(this);
            sendMessage(ChatColor.GOLD + "[Game Manager] " + "The game will begin in 20 seconds...");
            Main.getInstance().addActiveGame(this);
            start();
            startCountDown();
        }
    }

    public void joinAdmin(GamePlayer gamePlayer) {
        for (Player online : Bukkit.getOnlinePlayers())
            online.hidePlayer(gamePlayer.getPlayer());
        gamePlayer.teleport(lobbySpawn);
        gamePlayer.getPlayer().setGameMode(org.bukkit.GameMode.CREATIVE);
    }

    @Override
    public void start() {
        setState(GameState.ACTIVE);
        sendMessage(ChatColor.GOLD + "[Game Manager] " + "The game has started.");
        setMovementFrozen(false);
    }

    public void adminStart() {
        setState(GameState.STARTING);
        inGameScoreBoard.updateScoreBoard(this);
        sendMessage(ChatColor.GOLD + "[Game Manager] " + "The game will begin in 20 seconds...");
        Main.getInstance().addActiveGame(this);
        start();
        startCountDown();
    }

    @Override
    public void end() {
        if (inGameScoreBoard != null) {
            inGameScoreBoard.stopUpdating();
        }
        if (gameCountdownTask != null) {
            gameCountdownTask.getGameRunTask().getGameTask().setGameTimer(getGameTime());
            gameCountdownTask.getGameRunTask().getGameTask().cancel();
        }
        for (GamePlayer gamePlayer : players) {
            Player player = gamePlayer.getPlayer();
            player.sendMessage(ChatColor.GOLD + "[Game Manager] " + "Game has ended");
            gamePlayer.teleport(new Lobby().getLobbyPoint());
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setHealth(20);
            gamePlayer.setPlayerLocation(PlayerLocation.LOBBY);
            Main.getInstance().playerGameMap.remove(player.getPlayer(), this);
            LobbyScoreBoard lobbyScoreBoard = new LobbyScoreBoard();
            lobbyScoreBoard.setLobbyScoreBoard(gamePlayer);
            lobbyScoreBoard.updateScoreBoard(gamePlayer);
            player.removePotionEffect(PotionEffectType.SPEED);
        }
        Main.getInstance().getGames().remove(this);
        resetGameInfo();
        Main.getInstance().getGames().add(this);
        Main.getInstance().removeActiveGame(this);
        for (Chest chest : opened) {
            Main.getChestGui().remove(chest);
        }
    }

    @Override
    public void save() throws IOException {
        GameData.saveGame(this);
    }

    @Override
    public void playerLeave(GamePlayer gamePlayer) {
        Player player = gamePlayer.getPlayer();
        if (!Main.getInstance().isInGame(player)) {
            return;
        }
        if (isState(GameState.ACTIVE)) {
            getPlayers().remove(gamePlayer);
            setWinner(getPlayers().get(0));
            return;
        }
        end();
    }

    @Override
    public void resetGameInfo() {
        getPlayers().clear();
        gameTime = 450;
        inGameScoreBoard = new InGameScoreBoard();
        map.unload();
    }

    @Override
    public void setWinner(GamePlayer gamePlayer) {
        gamePlayer.getPlayer().sendMessage(ChatColor.GOLD + gamePlayer.getPlayer().getName() + " has won the game");
        end();
    }

    @Override
    public void assignSpawnPositions() {
        int id = 0;
        for (GamePlayer gamePlayer : getPlayers()) {
            gamePlayerToSpawnPoint.put(gamePlayer, playerSpawnLocations.get(id));
            gamePlayer.teleport(playerSpawnLocations.get(id));
            gamePlayer.setPlayerLocation(PlayerLocation.GAME);
            id += 1;
            gamePlayer.getPlayer().setGameMode(org.bukkit.GameMode.SURVIVAL);
            gamePlayer.getPlayer().setHealth(gamePlayer.getPlayer().getMaxHealth());
        }
    }

    @Override
    public void startCountDown() {
        gameCountdownTask = new GameCountdownTask(this);
        gameCountdownTask.runTaskTimer(Main.getInstance(), 0, 20);
    }

    public List<GamePlayer> getPlayers() {
        return activePlayers;
    }

    public boolean isState(GameState state) {
        return getGameState() == state;
    }

    public void setState(GameState gameState) {
        this.gameState = gameState;
    }

    public void sendMessage(String message) {
        for (GamePlayer gamePlayer : getPlayers()) {
            gamePlayer.sendMessage(message);
        }
    }

    public void setPlayerSpawnLocations(List<Location> playerSpawnLocations) {
        this.playerSpawnLocations = playerSpawnLocations;
    }

    public GamePlayer getGamePlayer(Player player) {
        for (GamePlayer gamePlayer : getPlayers()) {
            if (gamePlayer.getPlayer() == player) {
                return gamePlayer;
            }
        }
        return null;
    }

    public Set<Chest> getOpened() {
        return opened;
    }

    public List<LootItem> getNormalItems() {
        return new NormalItems().getNormalItems();
    }

    public List<LootItem> getEpicItems() {
        return new EpicItems().getEpicItems();
    }

    public List<LootItem> getPlayerBoostItems() {
        return new PlayerBoostItems().getPlayerBoostItems();
    }

    public void setMovementFrozen(boolean movementFrozen) {
        this.movementFrozen = movementFrozen;
    }


    public String getName() {
        return name;
    }

    public ItemStack getGameIcon() {
        return gameIcon;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public GameMap getMap() {
        return map;
    }

    public Location getLobbySpawn() {
        return lobbySpawn;
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean isMovementFrozen() {
        return movementFrozen;
    }

    public int getGameTime() {
        return gameTime;
    }

    public List<GamePlayer> getActivePlayers() {
        return activePlayers;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public void setLobbySpawn(Location lobbySpawn) {
        this.lobbySpawn = lobbySpawn;
    }

    public enum GameState {
        LOBBY, STARTING, PREPARATION, ACTIVE, DEATHMATCH, ENDING
    }
}
