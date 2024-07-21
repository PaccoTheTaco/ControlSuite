package com.paccothetaco.controlsuite.home;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

import com.paccothetaco.controlsuite.settings.ConfigManager;

public class HomeCommand implements CommandExecutor {

    private final FileConfiguration config;
    private final ConfigManager configManager;

    public HomeCommand(FileConfiguration config, ConfigManager configManager) {
        this.config = config;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.isOp()) {
            String setting = configManager.getSetting("home");
            if ("noone".equals(setting) || ("specific".equals(setting) && !configManager.isHomePlayerAuthorized(player.getUniqueId()))) {
                player.sendMessage(ChatColor.RED + "You are not authorized to use /home.");
                return true;
            }
        }

        String playerName = player.getName();

        if (args.length == 0) {
            if (config.contains("homes." + playerName)) {
                Location loc = (Location) config.get("homes." + playerName);
                player.teleport(loc);
                player.sendMessage(ChatColor.GREEN + "Teleported to your home!");
            } else {
                player.sendMessage(ChatColor.RED + "You don't have a home set. Use /sethome to set your home.");
            }
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "Usage: /home");
            return true;
        }
    }
}
