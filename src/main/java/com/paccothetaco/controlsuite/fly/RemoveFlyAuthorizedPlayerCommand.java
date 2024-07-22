package com.paccothetaco.controlsuite.commands;

import com.paccothetaco.controlsuite.settings.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RemoveFlyAuthorizedPlayerCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public RemoveFlyAuthorizedPlayerCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /removefly <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        UUID playerUUID = target.getUniqueId();
        configManager.removeFlyAuthorizedPlayer(playerUUID);
        sender.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " is no longer authorized to use /fly.");
        return true;
    }
}
