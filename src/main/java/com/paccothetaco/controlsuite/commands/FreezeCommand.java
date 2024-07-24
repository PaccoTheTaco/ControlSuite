package com.paccothetaco.controlsuite.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class FreezeCommand implements CommandExecutor, Listener {
    private final JavaPlugin plugin;
    private final Set<Player> frozenPlayers = new HashSet<>();

    public FreezeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /freeze <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (frozenPlayers.contains(target)) {
            frozenPlayers.remove(target);
            player.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " has been unfrozen.");
        } else {
            frozenPlayers.add(target);
            player.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " has been frozen.");
        }

        return true;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (frozenPlayers.contains(player)) {
            if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
                event.setTo(event.getFrom());
                player.sendMessage(ChatColor.RED + "You are frozen and cannot move.");
            }

            if (event.getFrom().getY() != event.getTo().getY() && event.getTo().getY() > event.getFrom().getY()) {
                event.setTo(event.getFrom());
                player.sendMessage(ChatColor.RED + "You are frozen and cannot move.");
            }
        }
    }
}
