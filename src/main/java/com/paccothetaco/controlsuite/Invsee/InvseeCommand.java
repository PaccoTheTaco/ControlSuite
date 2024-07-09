package com.paccothetaco.controlsuite.Invsee;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("controlsuite.invsee")) {
            player.sendMessage("Du hast keine Berechtigung, diesen Befehl auszuführen.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("Benutzung: /invsee <Spielername>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("Spieler nicht gefunden.");
            return true;
        }

        Inventory targetInventory = createInvseeInventory(target);
        player.openInventory(targetInventory);
        return true;
    }

    private Inventory createInvseeInventory(Player target) {
        Inventory invseeInventory = Bukkit.createInventory(new InvseeHolder(target), 54, "Inventory von " + target.getName());

        invseeInventory.setItem(0, createGuiItem(target.getInventory().getHelmet(), "Helmet"));
        invseeInventory.setItem(1, createGuiItem(target.getInventory().getChestplate(), "Chestplate"));
        invseeInventory.setItem(2, createGuiItem(target.getInventory().getLeggings(), "Leggings"));
        invseeInventory.setItem(3, createGuiItem(target.getInventory().getBoots(), "Boots"));
        invseeInventory.setItem(4, createGuiItem(target.getInventory().getItemInOffHand(), "Offhand"));

        ItemStack[] targetItems = target.getInventory().getContents();
        for (int i = 0; i < 36; i++) {
            invseeInventory.setItem(i + 9, targetItems[i]);
        }

        return invseeInventory;
    }

    private ItemStack createGuiItem(ItemStack item, String name) {
        if (item == null || item.getType() == Material.AIR) {
            item = new ItemStack(Material.AIR);
        } else {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(name);
                item.setItemMeta(meta);
            }
        }
        return item;
    }
}
