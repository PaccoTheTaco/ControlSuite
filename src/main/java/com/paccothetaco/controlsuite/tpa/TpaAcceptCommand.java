package com.paccothetaco.controlsuite.tpa;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaAcceptCommand implements CommandExecutor {
    private final TpaManager tpaManager;

    public TpaAcceptCommand(TpaManager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Player requester = tpaManager.getRequester(player);

            if (requester != null) {
                requester.teleport(player.getLocation());
                requester.sendMessage("Teleport request accepted by " + player.getName());
                player.sendMessage("You have accepted the teleport request from " + requester.getName());
                tpaManager.removeRequest(player);
            } else {
                player.sendMessage("No teleport request found to accept.");
            }
        }
        return true;
    }
}
