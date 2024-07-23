package com.paccothetaco.controlsuite.settings;

import com.paccothetaco.controlsuite.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class SettingsListener implements Listener {

    private final ConfigManager configManager;
    private final Main plugin;

    public SettingsListener(ConfigManager configManager, Main plugin) {
        this.configManager = configManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (title.equals(ChatColor.DARK_GREEN + "Settings")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            if (clickedItem.getType() == Material.ENDER_CHEST) {
                SettingsGUI.openEnderchestSettingsMenu((Player) event.getWhoClicked());
            } else if (clickedItem.getType() == Material.BLUE_BED) {
                SettingsGUI.openHomeSettingsMenu((Player) event.getWhoClicked());
            } else if (clickedItem.getType() == Material.CYAN_BANNER) {
                SettingsGUI.openClanSettingsMenu((Player) event.getWhoClicked());
            } else if (clickedItem.getType() == Material.FEATHER) {
                SettingsGUI.openFlySettingsMenu((Player) event.getWhoClicked());
            } else if (clickedItem.getType() == Material.DIAMOND_SWORD) {
                SettingsGUI.openPvpSettingsMenu((Player) event.getWhoClicked());
            }
        } else if (title.equals(ChatColor.GOLD + "Use /enderchest")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            String settingValue = "noone";
            if (clickedItem.getType() == Material.RED_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "No one can use /enderchest");
                settingValue = "noone";
            } else if (clickedItem.getType() == Material.GREEN_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "Everyone can use /enderchest");
                settingValue = "everyone";
            } else if (clickedItem.getType() == Material.YELLOW_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.YELLOW + "Specific players can use /enderchest");
                settingValue = "specific";
            }

            configManager.saveSetting("enderchest", settingValue);
            event.getWhoClicked().closeInventory();
        } else if (title.equals(ChatColor.GOLD + "Use /home")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            String settingValue = "noone";
            if (clickedItem.getType() == Material.RED_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "No one can use /home");
                settingValue = "noone";
            } else if (clickedItem.getType() == Material.GREEN_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "Everyone can use /home");
                settingValue = "everyone";
            } else if (clickedItem.getType() == Material.YELLOW_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.YELLOW + "Specific players can use /home");
                settingValue = "specific";
            }

            configManager.saveSetting("home", settingValue);
            event.getWhoClicked().closeInventory();
        } else if (title.equals(ChatColor.GOLD + "Clans")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            boolean enableClans = false;
            if (clickedItem.getType() == Material.RED_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "Clans system deactivated");
                enableClans = false;
            } else if (clickedItem.getType() == Material.GREEN_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "Clans system activated");
                enableClans = true;
            }

            configManager.saveSetting("clan-system-enabled", enableClans);
            event.getWhoClicked().closeInventory();

            if (enableClans) {
                plugin.registerClanFeatures();
            } else {
                plugin.unregisterClanFeatures();
            }
        } else if (title.equals(ChatColor.GOLD + "Use /fly")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            String settingValue = "noone";
            if (clickedItem.getType() == Material.RED_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "No one can use /fly");
                settingValue = "noone";
            } else if (clickedItem.getType() == Material.GREEN_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "Everyone can use /fly");
                settingValue = "everyone";
            } else if (clickedItem.getType() == Material.YELLOW_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.YELLOW + "Specific players can use /fly");
                settingValue = "specific";
            }

            configManager.saveSetting("fly", settingValue);
            event.getWhoClicked().closeInventory();
        } else if (title.equals(ChatColor.RED + "PvP Settings")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            boolean enablePvp = false;
            if (clickedItem.getType() == Material.RED_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "PvP deactivated");
                enablePvp = false;
            } else if (clickedItem.getType() == Material.GREEN_WOOL) {
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "PvP activated");
                enablePvp = true;
            }

            configManager.saveSetting("pvp-enabled", enablePvp);
            configManager.loadConfig();
            event.getWhoClicked().closeInventory();
        }
    }
}
