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
        return loc.getBlockX() + ", " + loc.getBlockZ();
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
        Location prev = movement.getFrom();

        Scoreboard scoreboard = player.getScoreboard();

        Objective x = scoreboard.getObjective(player.getName());
        if(x == null) {
            x = scoreboard.registerNewObjective(player.getName(), "dummy", player.getName());
            x.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        Team team = scoreboard.getTeam(player.getName()+"_pos");
        if(team == null){
            team = scoreboard.registerNewTeam(player.getName()+"_pos");
        }
        team.setPrefix(parent.coordColor.toString() + formatPos(prev));

        team.addEntry(createEmptyName('a'));
        x.getScore(createEmptyName('a')).setScore(15);

        scoreboard.resetScores(formatRot(prev));
        x.getScore(formatRot(loc)).setScore(14);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent join){
        join.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
