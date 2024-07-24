package com.paccothetaco.controlsuite.tpa;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaDenyCommand implements CommandExecutor {
    private final TpaManager tpaManager;

    public TpaDenyCommand(TpaManager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Player requester = tpaManager.getRequester(player);

            if (requester != null) {
                requester.sendMessage("Teleport request denied by " + player.getName());
                player.sendMessage("You have denied the teleport request from " + requester.getName());
                tpaManager.removeRequest(player);
            } else {
                player.sendMessage("No teleport request found to deny.");
            }
        }
        return true;
    }
}
