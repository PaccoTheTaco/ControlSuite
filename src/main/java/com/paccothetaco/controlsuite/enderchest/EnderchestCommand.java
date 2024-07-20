package com.paccothetaco.controlsuite.enderchest;

import com.paccothetaco.controlsuite.settings.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderchestCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public EnderchestCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        String setting = configManager.getSetting("enderchest");

        if (setting.equals("noone")) {
            player.sendMessage("§cYou are not authorized to use this command.");
            return true;
        } else if (setting.equals("everyone")) {
            return openEnderChest(player, args);
        } else if (setting.equals("specific")) {
            if (configManager.isPlayerAuthorized(player.getUniqueId()) || player.isOp()) {
                return openEnderChest(player, args);
            } else {
                player.sendMessage("§cYou are not authorized to use this command.");
                return true;
            }
        }

        player.sendMessage("§cUnexpected error in settings.");
        return true;
    }

    private boolean openEnderChest(Player player, String[] args) {
        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
            player.sendMessage("§aYou are now viewing your enderchest!");
            return true;
        } else if (args.length == 1) {
            if (player.isOp()) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    player.openInventory(target.getEnderChest());
                    player.sendMessage("§aYou are now viewing the enderchest of " + target.getName() + "!");
                    return true;
                } else {
                    player.sendMessage("§cPlayer not found.");
                    return true;
                }
            } else {
                player.sendMessage("§cYou are not authorized to open another player's enderchest.");
                return true;
            }
        } else {
            player.sendMessage("§cUsage: /ec [playername]");
            return true;
        }
    }
}
