package com.paccothetaco.controlsuite.tpa;

import com.paccothetaco.controlsuite.settings.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaDenyCommand implements CommandExecutor {
    private final TpaManager tpaManager;
    private final ConfigManager configManager;

    public TpaDenyCommand(TpaManager tpaManager, ConfigManager configManager) {
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

        Player requester = tpaManager.getRequester(player);
        if (requester != null) {
            requester.sendMessage("Teleport request denied by " + player.getName());
            player.sendMessage("You have denied the teleport request from " + requester.getName());
            tpaManager.removeRequest(player);
        } else {
            player.sendMessage("No teleport request found to deny.");
        }

        return true;
    }
}
