package com.paccothetaco.controlsuite.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.GameMode;

public class GamemodeShort implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage("Usage: /gm <0|1|2|3> [player]");
            return true;
        }

        GameMode gameMode;
        switch (args[0].toLowerCase()) {
            case "0":
            case "surv":
            case "sur":
            case "survival":
            case "überleben":
                gameMode = GameMode.SURVIVAL;
                break;
            case "1":
            case "creative":
            case "crea":
            case "krea":
            case "kreativ":
                gameMode = GameMode.CREATIVE;
                break;
            case "2":
            case "adventure":
            case "adv":
                gameMode = GameMode.ADVENTURE;
                break;
            case "3":
            case "spectator":
            case "spec":
                gameMode = GameMode.SPECTATOR;
                break;
            default:
                sender.sendMessage("Invalid gamemode. Use 0, 1, 2, 3");
                return true;
        }

        Player targetPlayer;
        if (args.length == 2) {
            targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) {
                sender.sendMessage("Player not found.");
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }
            targetPlayer = (Player) sender;
        }

        targetPlayer.setGameMode(gameMode);
        targetPlayer.sendMessage("Your gamemode has been set to " + gameMode.toString().toLowerCase());
        if (!targetPlayer.equals(sender)) {
            sender.sendMessage("Set " + targetPlayer.getName() + "'s gamemode to " + gameMode.toString().toLowerCase());
        }
        return true;
    }
}
