package ca.hanss.learningpaper;

import net.md_5.bungee.api.ChatColor;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.*;

import net.md_5.bungee.api.chat.*;
import java.util.*;
import java.awt.Color;

class Commands {
    private LearningPaper parent;
    Commands(LearningPaper parent){
        this.parent = parent;
    }

    private void echo(){
        List<Argument> arguments = new ArrayList<>();
        arguments.add(new IntegerArgument("r", 0, 255));
        arguments.add(new IntegerArgument("g", 0, 255));
        arguments.add(new IntegerArgument("b", 0, 255));
        arguments.add(new GreedyStringArgument("message"));

        new CommandAPICommand("echo")
        .withArguments(arguments)
        .executes((sender, args) -> {
            int r = (int) args[0];
            int g = (int) args[1];
            int b = (int) args[2];
            BaseComponent message = new TextComponent((String) args[3]);
            message.setColor(ChatColor.of(new Color(r,g,b)));
            sender.spigot().sendMessage(message);
        })
        .register();
    }

    private void square(){
        new CommandAPICommand("square")
        .executes((sender, args) -> {
            BaseComponent message = new TextComponent("");
            for(int r=0; r<256; r+=10){
                for(int b=0; b<256; b+=10) {
                    BaseComponent next = new TextComponent("■");
                    next.setColor(ChatColor.of(new Color(r, 0, b)));
                    message.addExtra(next);
                }
                message.addExtra("\n");
            }
            sender.spigot().sendMessage(message);
        })
        .register();
    }

    private void cc(){
        List<Argument> arguments = new ArrayList<>();
        arguments.add(new IntegerArgument("r", 0, 255));
        arguments.add(new IntegerArgument("g", 0, 255));
        arguments.add(new IntegerArgument("b", 0, 255));

        new CommandAPICommand("cc")
        .withArguments(arguments)
        .executes((sender, args) -> {
            int r = (int) args[0];
            int g = (int) args[1];
            int b = (int) args[2];
            parent.coordColor = ChatColor.of(new Color(r,g,b));
            sender.spigot().sendMessage(new TextComponent(r + " "+ g + " " + b));
        })
        .register();
    }

    void registerAll(){
        echo();
        square();
        cc();
    }
}
