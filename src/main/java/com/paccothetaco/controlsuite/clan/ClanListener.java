package com.paccothetaco.controlsuite.clan;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import com.paccothetaco.controlsuite.Main;

public class ClanListener implements Listener {

    private final Main plugin;

    public ClanListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Clans")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            Player player = (Player) event.getWhoClicked();

            if (clickedItem.getType() == Material.EMERALD) {
                player.sendMessage("Enter the name of your new clan:");
                player.closeInventory();
                player.setMetadata("creatingClan", new FixedMetadataValue(plugin, true));
            } else if (clickedItem.getType() == Material.CYAN_BANNER) {
                String clanName = clickedItem.getItemMeta().getDisplayName();
                player.sendMessage("You selected the clan: " + clanName);
            }
        }
    }
}
