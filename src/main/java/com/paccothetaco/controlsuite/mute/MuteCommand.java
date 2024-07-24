package com.paccothetaco.controlsuite.mute;

import com.paccothetaco.controlsuite.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {
    private final Main plugin;
    private final MuteManager muteManager;

    public MuteCommand(Main plugin) {
        this.plugin = plugin;
        this.muteManager = plugin.getMuteManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                if (!muteManager.isPlayerMuted(target.getName())) {
                    muteManager.mutePlayer(target.getName());
                    sender.sendMessage(ChatColor.GREEN + target.getName() + " has been muted.");
                } else {
                    muteManager.unmutePlayer(target.getName());
                    sender.sendMessage(ChatColor.GREEN + target.getName() + " has been unmuted.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Player not found.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Please specify a player to mute/unmute.");
        }
        return true;
    }
}
