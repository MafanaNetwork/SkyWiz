package me.TahaCheji.scoreboards;

import me.TahaCheji.GameMain;
import me.TahaCheji.gameData.Game;
import me.TahaCheji.gameData.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

public class InGameLobbyScoreBoard {

    public int TaskId;

    public void setGameScoreboard(GamePlayer player) {
        Game game = GameMain.getInstance().getGame(player.getPlayer());
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("SkyWizzards", "dummy", ChatColor.GRAY + "♧" + ChatColor.GOLD + "SkyWizzards" + ChatColor.GRAY + "♧");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score name = obj.getScore(ChatColor.GOLD + "=-=-=-=-=-=-=-=-=-=-=-=-");
        name.setScore(16);

        Score emptyText1 = obj.getScore(" ");
        emptyText1.setScore(15);

        Team gameInfo = board.registerNewTeam("GameInfo");
        gameInfo.addEntry(ChatColor.BLACK + "" + ChatColor.BLACK);
        gameInfo.setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Name: " + game.getName() + " | Mode: " + game.getGameMode().toString() + " | Map: " + game.getMap().getName());
        obj.getScore(ChatColor.BLACK + "" + ChatColor.BLACK).setScore(14);

        Score emptyText2 = obj.getScore("   ");
        emptyText2.setScore(13);

        Team time = board.registerNewTeam("GameTime");
        time.addEntry(ChatColor.BLACK + "" + ChatColor.GOLD);
        time.setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Time: " + game.getGameTime());
        obj.getScore(ChatColor.BLACK + "" + ChatColor.GOLD).setScore(12);

        Score emptyText3 = obj.getScore("    ");
        emptyText3.setScore(11);

        Team players = board.registerNewTeam("AlivePlayers");
        players.addEntry(ChatColor.YELLOW + "" + ChatColor.YELLOW);
        players.setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Players: " + game.getPlayers().size());
        obj.getScore(ChatColor.YELLOW + "" + ChatColor.YELLOW).setScore(10);

        Score emptyText5 = obj.getScore("      ");
        emptyText5.setScore(9);

        Score score7 = obj.getScore(ChatColor.GOLD + "-=-=-=-Mafana.us.to-=-=-=-");
        score7.setScore(8);

        player.getPlayer().setScoreboard(board);
    }

    public void stopUpdating() {
        Bukkit.getScheduler().cancelTask(TaskId);
    }

    public int getTaskId() {
        return TaskId;
    }
}
