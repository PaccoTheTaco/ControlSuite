package com.paccothetaco.controlsuite.enderchest;

import com.paccothetaco.controlsuite.settings.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddAuthorizedPlayerCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public AddAuthorizedPlayerCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /addauth <playername>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            configManager.addAuthorizedPlayer(target.getUniqueId());
            sender.sendMessage("§a" + target.getName() + " has been authorized to use /enderchest.");
        } else {
            sender.sendMessage("§cPlayer not found.");
        }
        return true;
    }
}
