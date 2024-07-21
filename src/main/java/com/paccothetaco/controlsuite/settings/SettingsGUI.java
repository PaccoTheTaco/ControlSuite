package com.paccothetaco.controlsuite.settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsGUI {

    public static void openSettingsMenu(Player player) {
        Inventory settingsMenu = Bukkit.createInventory(null, 18, ChatColor.DARK_GREEN + "Settings");

        ItemStack enderchestItem = new ItemStack(Material.ENDER_CHEST);
        ItemMeta enderchestMeta = enderchestItem.getItemMeta();
        enderchestMeta.setDisplayName(ChatColor.GOLD + "Use /enderchest");
        enderchestItem.setItemMeta(enderchestMeta);
        settingsMenu.setItem(0, enderchestItem);

        ItemStack homeItem = new ItemStack(Material.BLUE_BED);
        ItemMeta homeMeta = homeItem.getItemMeta();
        homeMeta.setDisplayName(ChatColor.GOLD + "Use /home");
        homeItem.setItemMeta(homeMeta);
        settingsMenu.setItem(1, homeItem);

        player.openInventory(settingsMenu);
    }

    public static void openEnderchestSettingsMenu(Player player) {
        Inventory enderchestSettingsMenu = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Use /enderchest");

        ItemStack everyoneItem = new ItemStack(Material.GREEN_WOOL);
        ItemMeta everyoneMeta = everyoneItem.getItemMeta();
        everyoneMeta.setDisplayName(ChatColor.GREEN + "Everyone");
        everyoneItem.setItemMeta(everyoneMeta);

        ItemStack specificPlayersItem = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta specificPlayersMeta = specificPlayersItem.getItemMeta();
        specificPlayersMeta.setDisplayName(ChatColor.YELLOW + "Specific Players");
        specificPlayersItem.setItemMeta(specificPlayersMeta);

        ItemStack noOneItem = new ItemStack(Material.RED_WOOL);
        ItemMeta noOneMeta = noOneItem.getItemMeta();
        noOneMeta.setDisplayName(ChatColor.RED + "No One");
        noOneItem.setItemMeta(noOneMeta);

        enderchestSettingsMenu.setItem(2, everyoneItem);
        enderchestSettingsMenu.setItem(4, specificPlayersItem);
        enderchestSettingsMenu.setItem(6, noOneItem);

        player.openInventory(enderchestSettingsMenu);
    }

    public static void openHomeSettingsMenu(Player player) {
        Inventory homeSettingsMenu = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Use /home");

        ItemStack everyoneItem = new ItemStack(Material.GREEN_WOOL);
        ItemMeta everyoneMeta = everyoneItem.getItemMeta();
        everyoneMeta.setDisplayName(ChatColor.GREEN + "Everyone");
        everyoneItem.setItemMeta(everyoneMeta);

        ItemStack specificPlayersItem = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta specificPlayersMeta = specificPlayersItem.getItemMeta();
        specificPlayersMeta.setDisplayName(ChatColor.YELLOW + "Specific Players");
        specificPlayersItem.setItemMeta(specificPlayersMeta);

        ItemStack noOneItem = new ItemStack(Material.RED_WOOL);
        ItemMeta noOneMeta = noOneItem.getItemMeta();
        noOneMeta.setDisplayName(ChatColor.RED + "No One");
        noOneItem.setItemMeta(noOneMeta);

        homeSettingsMenu.setItem(2, everyoneItem);
        homeSettingsMenu.setItem(4, specificPlayersItem);
        homeSettingsMenu.setItem(6, noOneItem);

        player.openInventory(homeSettingsMenu);
    }
}