package me.TahaCheji.gameData;

import me.TahaCheji.Main;
import me.TahaCheji.gameData.Game;
import me.TahaCheji.gameData.GameMode;
import me.TahaCheji.mapUtil.GameMap;
import me.TahaCheji.mapUtil.LocalGameMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameData {

    public static void saveGame(Game game) throws IOException {
        File gameData = new File("plugins/SkyWiz/", "games/" + game.getName() + ".yml");
        FileConfiguration pD = YamlConfiguration.loadConfiguration(gameData);
        if (!gameData.exists()) {
            gameData.createNewFile();
            Location location = new Location(game.getMap().getWorld(), 0, 0, 0);
            pD.set("data.gameName", game.getName());
            pD.set("data.gameIcon", game.getGameIcon());
            pD.set("data.gameMode", game.getGameMode().toString());
            pD.set("data.gameMap", game.getMap().getName());

            pD.set("p1Spawn.location", location);
            pD.set("p2Spawn.location", location);
            pD.set("p3Spawn.location", location);
            pD.set("p4Spawn.location", location);

            pD.set("lobby.x", 0);
            pD.set("lobby.y", 0);
            pD.set("lobby.z", 0);
            pD.save(gameData);
        }
    }

    public static List<Game> getAllSavedGames() {
        List<Game> arrayList = new ArrayList<>();
        File dataFolder = new File("plugins/SkyWiz/", "games");
        File[] files = dataFolder.listFiles();
        for (File file : files) {
            FileConfiguration pD = YamlConfiguration.loadConfiguration(file);
            String gameName = pD.getString("data.gameName");
            ItemStack material = pD.getItemStack("data.gameIcon");
            //gameMode
            File gameMapsFolder = new File("plugins/SkyWiz/", "maps");
            Location p1Location = pD.getLocation("p1Spawn.location");
            Location p2Location = pD.getLocation("p2Spawn.location");
            Location p3Location = pD.getLocation("p3Spawn.location");
            Location p4Location = pD.getLocation("p4Spawn.location");
            Location lobbySpawn = new Location(Bukkit.getWorld("world"), pD.getInt("lobby.x"), pD.getInt("lobby.y"), pD.getInt("lobby.z"));
            GameMap gameMap = new LocalGameMap(gameMapsFolder, pD.getString("data.gameMap"), false);
            Game game = new Game(gameName, material, GameMode.NORMAL, gameMap);
            List<Location> locations = new ArrayList<>();
            locations.add(p1Location);
            locations.add(p2Location);
            locations.add(p3Location);
            locations.add(p4Location);
            game.setPlayerSpawnLocations(locations);
            game.setLobbySpawn(lobbySpawn);
            arrayList.add(game);
        }
        return arrayList;
    }

    public static Game getGame(String name) {
        Game getGame = null;
        for (Game game : getAllSavedGames()) {
            if (game.getName().contains(name)) {
                getGame = game;
            }
        }
        return getGame;
    }

    public static void removeGame(String gameName) {
        File dataFolder = new File(Main.getInstance().getDataFolder(), "games");
        File[] files = dataFolder.listFiles();
        for (File file : files) {
            if (file.getName().contains(gameName)) {
                file.delete();
            }
        }
    }


}
