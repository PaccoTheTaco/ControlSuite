package com.paccothetaco.controlsuite.Invsee;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /invsee <player>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            openInventory(player, target);
            player.sendMessage("You are now viewing " + target.getName() + "'s inventory.");
            return true;
        } else if (sender instanceof ConsoleCommandSender) {
            try {
                saveInventoryToFile(target);
                sender.sendMessage("Inventory of " + target.getName() + " has been saved to file and opened.");
                return true;
            } catch (IOException e) {
                sender.sendMessage("An error occurred while saving the inventory to file.");
                e.printStackTrace();
                return false;
            }
        } else {
            sender.sendMessage("This command can only be used by players or console.");
            return false;
        }
    }

    private void openInventory(Player viewer, Player target) {
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

        viewer.openInventory(invseeInventory);
    }

    private void saveInventoryToFile(Player target) throws IOException {
        File file = new File("inventories", target.getName() + "_inventory.txt");
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < 36; i++) {
                ItemStack item = target.getInventory().getItem(i);
                writer.write("Slot " + i + ": " + (item != null ? item.toString() : "empty") + "\n");
            }

            writer.write("Helmet: " + (target.getInventory().getHelmet() != null ? target.getInventory().getHelmet().toString() : "empty") + "\n");
            writer.write("Chestplate: " + (target.getInventory().getChestplate() != null ? target.getInventory().getChestplate().toString() : "empty") + "\n");
            writer.write("Leggings: " + (target.getInventory().getLeggings() != null ? target.getInventory().getLeggings().toString() : "empty") + "\n");
            writer.write("Boots: " + (target.getInventory().getBoots() != null ? target.getInventory().getBoots().toString() : "empty") + "\n");
            writer.write("OffHand: " + (target.getInventory().getItemInOffHand() != null ? target.getInventory().getItemInOffHand().toString() : "empty") + "\n");
        }

        openFile(file);
    }

    private void openFile(File file) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        } else {
            throw new UnsupportedOperationException("Desktop is not supported");
        }
    }
}
