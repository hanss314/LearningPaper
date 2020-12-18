package ca.hanss.learningpaper;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardInfo implements Listener {
    private LearningPaper parent;
    ScoreboardInfo(LearningPaper parent){
        this.parent = parent;
    }

    String formatPos(Location loc){
        return parent.coordColor.toString()  + loc.getBlockX() + ", " + loc.getBlockZ();
    }

    String formatRot(Location loc){
        double yaw = ((loc.getYaw() % 360) + 360) % 360;
        String xf = yaw < 180 ? "+" : "-";
        String zf = yaw < 90 || yaw > 270 ? "+" : "-";
        return ChatColor.RED + xf + "X " + ChatColor.BLUE + zf + "Z";
    }

    String createEmptyName(char c){
        return ChatColor.translateAlternateColorCodes('&', "&"+c);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent movement){
        Player player = movement.getPlayer();
        Location loc = movement.getTo();
        Scoreboard scoreboard = player.getScoreboard();

        Team position = scoreboard.getTeam("position");
        Team rotation = scoreboard.getTeam("rotation");

        position.setPrefix(formatPos(loc));
        rotation.setPrefix(formatRot(loc));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent join){
        Player player = join.getPlayer();

        Scoreboard newBoard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(newBoard);

        Objective hud = newBoard.registerNewObjective("hud", "dummy", player.getName());
        hud.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team position = newBoard.registerNewTeam("position");
        Team rotation = newBoard.registerNewTeam("rotation");

        position.addEntry(createEmptyName('a'));
        rotation.addEntry(createEmptyName('b'));

        hud.getScore(createEmptyName('a')).setScore(15);
        hud.getScore(createEmptyName('b')).setScore(14);
    }
}
