package com.paccothetaco.controlsuite.clan;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ClanCommand implements CommandExecutor, Listener {

    public static List<Clan> clans = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openClanGUI(player);
        }
        return true;
    }

    public void openClanGUI(Player player) {
        Inventory clanGUI = Bukkit.createInventory(null, 54, "Clans");

        for (int i = 0; i < clans.size(); i++) {
            Clan clan = clans.get(i);
            ItemStack clanItem = new ItemStack(Material.CYAN_BANNER);
            ItemMeta meta = clanItem.getItemMeta();
            meta.setDisplayName(clan.getName());
            List<String> lore = new ArrayList<>();
            lore.add("Owner: " + clan.getOwner());
            meta.setLore(lore);
            clanItem.setItemMeta(meta);
            clanGUI.setItem(i, clanItem);
        }

        ItemStack createClanItem = new ItemStack(Material.EMERALD);
        ItemMeta meta = createClanItem.getItemMeta();
        meta.setDisplayName("Create a new Clan");
        createClanItem.setItemMeta(meta);
        clanGUI.setItem(53, createClanItem);

        player.openInventory(clanGUI);
    }
}
