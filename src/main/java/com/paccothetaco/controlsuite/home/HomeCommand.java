package com.paccothetaco.controlsuite.home;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

public class HomeCommand implements CommandExecutor {

    private final FileConfiguration config;

    public HomeCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();

        if (args.length == 0) {
            if (config.contains("homes." + playerName)) {
                Location loc = (Location) config.get("homes." + playerName);
                player.teleport(loc);
                player.sendMessage("§aTeleported to your home!");
            } else {
                player.sendMessage("§cYou don't have a home set. Use /sethome to set your home.");
            }
            return true;
        } else {
            player.sendMessage("§cUsage: /home");
            return true;
        }
    }
}
