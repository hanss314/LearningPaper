package ca.hanss.learningpaper;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardInfo implements Listener {
    private LearningPaper parent;
    ScoreboardInfo(LearningPaper parent){
        this.parent = parent;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent movement){
        Player player = movement.getPlayer();
        Location loc = movement.getTo();
        Scoreboard scoreboard = player.getScoreboard();

        Objective x = scoreboard.getObjective(player.getName());
        if(x == null) {
            x = scoreboard.registerNewObjective(player.getName(), "dummy", player.getName());
            x.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        for(String e : scoreboard.getEntries()) {
            scoreboard.resetScores(e);
        }

        String coord = parent.coordColor + String.valueOf(loc.getBlockX()) + ", " + loc.getBlockZ();
        x.getScore(coord).setScore(15);

        String xf = loc.getYaw() < 180 ? "+" : "-";
        String zf = loc.getYaw() < 90 || loc.getYaw() > 270 ? "+" : "-";
        String facing = ChatColor.RED + xf + "X " + ChatColor.BLUE + zf + "Z";
        x.getScore(facing).setScore(14);
        //player.setScoreboard(scoreboard);

        //movement.getPlayer().spigot().sendMessage(new TextComponent("a"));
    }
}
