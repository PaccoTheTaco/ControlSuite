package com.paccothetaco.controlsuite.enderchest;

import com.paccothetaco.controlsuite.settings.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveAuthorizedPlayerCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public RemoveAuthorizedPlayerCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /removeauth <playername>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            configManager.removeAuthorizedPlayer(target.getUniqueId());
            sender.sendMessage("§a" + target.getName() + " is no longer authorized to use /enderchest.");
        } else {
            sender.sendMessage("§cPlayer not found.");
        }
        return true;
    }
}
