package ca.hanss.learningpaper;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class LearningPaper extends JavaPlugin {
    ChatColor coordColor;
    private ScoreboardInfo scoreDisplay;
    private Commands commandHandler;

    public LearningPaper(){
        coordColor = ChatColor.RED;
        scoreDisplay = new ScoreboardInfo(this);
        commandHandler = new Commands(this);
    }

    @Override
    public void onEnable() {
        commandHandler.registerAll();
        getServer().getPluginManager().registerEvents(scoreDisplay, this);
    }

    @Override
    public void onDisable(){ }
}
