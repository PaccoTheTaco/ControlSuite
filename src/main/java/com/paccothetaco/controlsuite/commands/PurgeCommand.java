package com.paccothetaco.controlsuite.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PurgeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                try {
                    int lines = Integer.parseInt(args[0]);
                    if (lines > 0 && lines <= 100) {
                        for (int i = 0; i < lines; i++) {
                            player.sendMessage("");
                        }
                        player.sendMessage(ChatColor.GREEN + "Purged " + lines + " lines.");
                    } else {
                        player.sendMessage(ChatColor.RED + "Please specify a number between 1 and 100.");
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid number format.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Please specify the number of lines to purge.");
            }
        }
        return true;
    }
}
