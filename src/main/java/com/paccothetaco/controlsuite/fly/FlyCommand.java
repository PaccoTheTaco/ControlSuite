package com.paccothetaco.controlsuite.fly;

import com.paccothetaco.controlsuite.settings.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public FlyCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        String flySetting = configManager.getSetting("fly");
        player.sendMessage("Fly setting is: " + flySetting);

        if (flySetting.equalsIgnoreCase("noone")) {
            player.sendMessage(ChatColor.RED + "No one is allowed to use the fly command.");
            return true;
        } else if (flySetting.equalsIgnoreCase("specific") && !configManager.isFlyPlayerAuthorized(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You are not authorized to use this command.");
            return true;
        }

        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.sendMessage(ChatColor.GREEN + "Fly mode disabled.");
        } else {
            player.setAllowFlight(true);
            player.sendMessage(ChatColor.GREEN + "Fly mode enabled.");
        }

        return true;
    }
}
