package com.paccothetaco.controlsuite.enderchest;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderchestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
            player.sendMessage("§aDu siehst jetzt deine Enderchest!");
            return true;
        } else if (args.length == 1) {
            if (player.isOp()) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    player.openInventory(target.getEnderChest());
                    player.sendMessage("§aDu siehst jetzt die Enderchest von " + target.getName() + "!");
                    return true;
                } else {
                    player.sendMessage("§cSpieler nicht gefunden.");
                    return true;
                }
            } else {
                player.sendMessage("§cDu hast keine Berechtigung, die Enderchest eines anderen Spielers zu öffnen.");
                return true;
            }
        } else {
            player.sendMessage("§cVerwendung: /ec [playername]");
            return true;
        }
    }
}
