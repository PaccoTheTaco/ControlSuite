package com.paccothetaco.controlsuite.Invsee;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 1) {
                player.sendMessage("Usage: /invsee <player>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("Player not found.");
                return false;
            }

            Inventory invseeInventory = Bukkit.createInventory(new InvseeHolder(target), 45, target.getName() + "'s Inventory");

            for (int i = 0; i < 36; i++) {
                invseeInventory.setItem(i, target.getInventory().getItem(i));
            }

            invseeInventory.setItem(36, target.getInventory().getHelmet());
            invseeInventory.setItem(37, target.getInventory().getChestplate());
            invseeInventory.setItem(38, target.getInventory().getLeggings());
            invseeInventory.setItem(39, target.getInventory().getBoots());
            invseeInventory.setItem(40, target.getInventory().getItemInOffHand());

            ItemStack barrier = new ItemStack(Material.BARRIER);
            for (int i = 41; i < 45; i++) {
                invseeInventory.setItem(i, barrier);
            }

            player.openInventory(invseeInventory);
            player.sendMessage("You are now viewing " + target.getName() + "'s inventory.");

            return true;
        } else {
            sender.sendMessage("This command can only be used by players.");
            return false;
        }
    }
}
