package me.TahaCheji.util;

import me.TahaCheji.GameMain;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Files {
	
	
	static File configFile = new File("plugins/SkyWiz/config.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
	
	public static void initFiles() throws FileNotFoundException, IOException, InvalidConfigurationException {
		
		if(!new File("plugins/SkyWiz").exists()) {
			new File("plugins/SkyWiz").mkdir();
		}
		
		if(!configFile.exists()) {
			GameMain.getInstance().saveDefaultConfig();
		}
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

		File maps = new File("plugins/SkyWiz/maps");
		if(!maps.exists()) {
			maps.mkdir();
		}

		File games = new File("plugins/SkyWiz/games");
		if(!games.exists()) {
			games.mkdir();
		}

		File playerData = new File("plugins/SkyWiz/playerData");
		if(!playerData.exists()) {
			playerData.mkdir();
		}
		
		loadFiles();
		
	}

	private static void loadFiles() throws FileNotFoundException, IOException, InvalidConfigurationException {
		cfg.load(configFile);
	}


	private static void loadFile(InputStream paramInputStream, File paramFile) throws IOException, InvalidConfigurationException {

		if(!paramFile.exists()) {
			FileUtils.copyInputStreamToFile(paramInputStream, paramFile);
		}
		((FileConfiguration)YamlConfiguration.loadConfiguration(paramFile)).load(paramFile);
	}
	
}
