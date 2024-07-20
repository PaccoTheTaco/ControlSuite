package com.paccothetaco.controlsuite.settings;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class SettingsListener implements Listener {

    private final ConfigManager configManager;

    public SettingsListener(ConfigManager configManager) {
        this.configManager = configManager;
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
        }
    }
}
