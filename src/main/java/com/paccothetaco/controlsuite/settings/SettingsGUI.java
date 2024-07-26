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
        Inventory settingsMenu = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Settings");

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

        ItemStack clanItem = new ItemStack(Material.CYAN_BANNER);
        ItemMeta clanMeta = clanItem.getItemMeta();
        clanMeta.setDisplayName(ChatColor.GOLD + "Clans");
        clanItem.setItemMeta(clanMeta);
        settingsMenu.setItem(2, clanItem);

        ItemStack flyItem = new ItemStack(Material.FEATHER);
        ItemMeta flyMeta = flyItem.getItemMeta();
        flyMeta.setDisplayName(ChatColor.GOLD + "Use /fly");
        flyItem.setItemMeta(flyMeta);
        settingsMenu.setItem(3, flyItem);

        ItemStack pvpItem = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta pvpMeta = pvpItem.getItemMeta();
        pvpMeta.setDisplayName(ChatColor.RED + "PvP Settings");
        pvpItem.setItemMeta(pvpMeta);
        settingsMenu.setItem(4, pvpItem);

        ItemStack tpaItem = new ItemStack(Material.ENDER_PEARL);
        ItemMeta tpaMeta = tpaItem.getItemMeta();
        tpaMeta.setDisplayName(ChatColor.GOLD + "Use /tpa");
        tpaItem.setItemMeta(tpaMeta);
        settingsMenu.setItem(5, tpaItem);

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

    public static void openClanSettingsMenu(Player player) {
        Inventory clanSettingsMenu = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Clans");

        ItemStack activateItem = new ItemStack(Material.GREEN_WOOL);
        ItemMeta activateMeta = activateItem.getItemMeta();
        activateMeta.setDisplayName(ChatColor.GREEN + "Activate");
        activateItem.setItemMeta(activateMeta);

        ItemStack deactivateItem = new ItemStack(Material.RED_WOOL);
        ItemMeta deactivateMeta = deactivateItem.getItemMeta();
        deactivateMeta.setDisplayName(ChatColor.RED + "Deactivate");
        deactivateItem.setItemMeta(deactivateMeta);

        clanSettingsMenu.setItem(3, activateItem);
        clanSettingsMenu.setItem(5, deactivateItem);

        player.openInventory(clanSettingsMenu);
    }

    public static void openFlySettingsMenu(Player player) {
        Inventory flySettingsMenu = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Use /fly");

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

        flySettingsMenu.setItem(2, everyoneItem);
        flySettingsMenu.setItem(4, specificPlayersItem);
        flySettingsMenu.setItem(6, noOneItem);

        player.openInventory(flySettingsMenu);
    }
    public static void openPvpSettingsMenu(Player player) {
        Inventory pvpSettingsMenu = Bukkit.createInventory(null, 9, ChatColor.RED + "PvP Settings");

        ItemStack activateItem = new ItemStack(Material.GREEN_WOOL);
        ItemMeta activateMeta = activateItem.getItemMeta();
        activateMeta.setDisplayName(ChatColor.GREEN + "Activate PvP");
        activateItem.setItemMeta(activateMeta);

        ItemStack deactivateItem = new ItemStack(Material.RED_WOOL);
        ItemMeta deactivateMeta = deactivateItem.getItemMeta();
        deactivateMeta.setDisplayName(ChatColor.RED + "Deactivate PvP");
        deactivateItem.setItemMeta(deactivateMeta);

        pvpSettingsMenu.setItem(3, activateItem);
        pvpSettingsMenu.setItem(5, deactivateItem);

        player.openInventory(pvpSettingsMenu);
    }

    public static void openTpaSettingsMenu(Player player) {
        Inventory tpaSettingsMenu = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Use /tpa");

        ItemStack activeItem = new ItemStack(Material.GREEN_WOOL);
        ItemMeta activeMeta = activeItem.getItemMeta();
        activeMeta.setDisplayName(ChatColor.GREEN + "Active");
        activeItem.setItemMeta(activeMeta);

        ItemStack deactiveItem = new ItemStack(Material.RED_WOOL);
        ItemMeta deactiveMeta = deactiveItem.getItemMeta();
        deactiveMeta.setDisplayName(ChatColor.RED + "Deactive");
        deactiveItem.setItemMeta(deactiveMeta);

        tpaSettingsMenu.setItem(3, activeItem);
        tpaSettingsMenu.setItem(5, deactiveItem);

        player.openInventory(tpaSettingsMenu);
    }
}
