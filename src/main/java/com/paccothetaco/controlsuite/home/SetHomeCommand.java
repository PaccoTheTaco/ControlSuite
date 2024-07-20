package com.paccothetaco.controlsuite.home;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

public class SetHomeCommand implements CommandExecutor {

    private final FileConfiguration config;

    public SetHomeCommand(FileConfiguration config) {
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
        Location loc = player.getLocation();

        config.set("homes." + playerName, loc);
        player.sendMessage("§aHome set!");

        return true;
    }
}
