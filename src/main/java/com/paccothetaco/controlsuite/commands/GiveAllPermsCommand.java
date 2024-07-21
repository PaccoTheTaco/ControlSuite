package com.paccothetaco.controlsuite.commands;

import com.paccothetaco.controlsuite.settings.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GiveAllPermsCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public GiveAllPermsCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /giveallperms <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        UUID playerUUID = target.getUniqueId();
        configManager.addAuthorizedPlayer(playerUUID);
        configManager.addHomeAuthorizedPlayer(playerUUID);
        sender.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " has been given all permissions.");
        return true;
    }
}
