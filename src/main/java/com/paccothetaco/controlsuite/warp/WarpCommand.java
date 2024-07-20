package com.paccothetaco.controlsuite.warp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

public class WarpCommand implements CommandExecutor {

    private final FileConfiguration config;

    public WarpCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 1) {
            String warpName = args[0];
            if (config.contains("warps." + warpName)) {
                Location loc = (Location) config.get("warps." + warpName);
                player.teleport(loc);
                player.sendMessage("§aTeleported to warp " + warpName + "!");
            } else {
                player.sendMessage("§cWarp " + warpName + " does not exist.");
            }
            return true;
        } else {
            player.sendMessage("§cUsage: /warp <warpname>");
            return true;
        }
    }
}
