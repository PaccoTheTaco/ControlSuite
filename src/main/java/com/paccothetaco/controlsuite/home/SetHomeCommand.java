package com.paccothetaco.controlsuite.home;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

import com.paccothetaco.controlsuite.settings.ConfigManager;

public class SetHomeCommand implements CommandExecutor {

    private final FileConfiguration config;
    private final ConfigManager configManager;

    public SetHomeCommand(FileConfiguration config, ConfigManager configManager) {
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
                player.sendMessage(ChatColor.RED + "You are not authorized to set a home.");
                return true;
            }
        }

        String playerName = player.getName();
        Location loc = player.getLocation();

        config.set("homes." + playerName, loc);
        player.sendMessage(ChatColor.GREEN + "Home set!");

        return true;
    }
}
