package me.TahaCheji.scoreboards;

import me.TahaCheji.GameMain;
import me.TahaCheji.gameData.Game;
import me.TahaCheji.gameData.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

public class ActiveGameScoreBoard {


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
        gameInfo.setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Name: " + game.getName() + " | Mode: " + game.getGameMode().toString());
        obj.getScore(ChatColor.BLACK + "" + ChatColor.BLACK).setScore(14);

        Score emptyText2 = obj.getScore("   ");
        emptyText2.setScore(13);

        Team time = board.registerNewTeam("GameTime");
        time.addEntry(ChatColor.BLACK + "" + ChatColor.GOLD);
        time.setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Time Left: " + game.getGameTime());
        obj.getScore(ChatColor.BLACK + "" + ChatColor.GOLD).setScore(12);

        Score emptyText3 = obj.getScore("    ");
        emptyText3.setScore(11);

        Team players = board.registerNewTeam("AlivePlayers");
        players.addEntry(ChatColor.YELLOW + "" + ChatColor.YELLOW);
        players.setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Players Alive: " + game.getPlayers().size());
        obj.getScore(ChatColor.YELLOW + "" + ChatColor.YELLOW).setScore(10);

        Score emptyText5 = obj.getScore("      ");
        emptyText5.setScore(9);

        Team kills = board.registerNewTeam("Kills");
        kills.addEntry(ChatColor.YELLOW + "" + ChatColor.GREEN);
        kills.setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Kills: " + player.getKills());
        obj.getScore(ChatColor.YELLOW + "" + ChatColor.GREEN).setScore(8);

        Score emptyText6 = obj.getScore("           ");
        emptyText6.setScore(7);

        Score score7 = obj.getScore(ChatColor.GOLD + "-=-=-=-Mafana.us.to-=-=-=-");
        score7.setScore(6);

        player.getPlayer().setScoreboard(board);
    }

    public void updateScoreBoard(Game game) {
        TaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(GameMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (GamePlayer player : game.getPlayers()) {
                    if(!player.getPlayer().isOnline()) {
                        stopUpdating();
                        return;
                    }
                    Scoreboard board = player.getPlayer().getScoreboard();
                    if(board.getTeam("AlivePlayers") != null) {
                        board.getTeam("AlivePlayers").setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Players Alive: " + game.getPlayers().size());
                    }
                    if(board.getTeam("Kills") != null) {
                        board.getTeam("Kills").setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Kills: " + player.getKills());
                    }
                    if(board.getTeam("GameTime") != null) {
                        if (game.getGameTime() <= 0) {
                            board.getTeam("GameTime").setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Time Left: " + "Ending");
                        } else {
                            board.getTeam("GameTime").setPrefix(ChatColor.GRAY + ">> " + ChatColor.GOLD + "Time Left: " + game.getGameTime());
                        }
                    }
                }
            }
        }, 0, 5);
    }

    public void stopUpdating() {
        Bukkit.getScheduler().cancelTask(TaskId);
    }

    public int getTaskId() {
        return TaskId;
    }

}
