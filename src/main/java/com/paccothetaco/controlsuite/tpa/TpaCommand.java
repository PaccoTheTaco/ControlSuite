package com.paccothetaco.controlsuite.tpa;

import com.paccothetaco.controlsuite.settings.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand implements CommandExecutor {
    private final TpaManager tpaManager;
    private final ConfigManager configManager;

    public TpaCommand(TpaManager tpaManager, ConfigManager configManager) {
        this.tpaManager = tpaManager;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;

        if (!configManager.isTpaEnabled()) {
            player.sendMessage("The /tpa command is currently disabled.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("Usage: /tpa <player>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            tpaManager.addRequest(player, target);
            player.sendMessage("Teleport request sent to " + target.getName());
            target.sendMessage(player.getName() + " wants to teleport to you. Type /tpa accept or /tpa deny.");
        } else {
            player.sendMessage("Player not found.");
        }

        return true;
    }
}
