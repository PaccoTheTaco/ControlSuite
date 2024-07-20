package com.paccothetaco.controlsuite.warp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

public class SetWarpCommand implements CommandExecutor {

    private final FileConfiguration config;

    public SetWarpCommand(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        if (!sender.isOp()) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 1) {
            String warpName = args[0];
            Location loc = player.getLocation();

            config.set("warps." + warpName, loc);
            player.sendMessage("§aWarp " + warpName + " set!");

            return true;
        } else {
            player.sendMessage("§cUsage: /setwarp <warpname>");
            return true;
        }
    }
}
