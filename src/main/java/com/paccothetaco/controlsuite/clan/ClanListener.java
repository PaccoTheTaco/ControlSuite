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
                Clan selectedClan = ClanCommand.clans.stream()
                        .filter(clan -> clan.getName().equals(clanName))
                        .findFirst()
                        .orElse(null);

                if (selectedClan != null) {
                    ClanCommand clanCommand = new ClanCommand();
                    clanCommand.openClanMembersGUI(player, selectedClan);
                }
            } else if (clickedItem.getType() == Material.CRAFTING_TABLE) {
                Clan playerClan = ClanCommand.clans.stream()
                        .filter(clan -> clan.getOwner().equals(player.getName()))
                        .findFirst()
                        .orElse(null);

                if (playerClan != null) {
                    ClanCommand clanCommand = new ClanCommand();
                    clanCommand.openClanSettingsGUI(player, playerClan);
                }
            }
        } else if (event.getView().getTitle().startsWith("Settings: ")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            Player player = (Player) event.getWhoClicked();
            Clan playerClan = ClanCommand.clans.stream()
                    .filter(clan -> clan.getOwner().equals(player.getName()))
                    .findFirst()
                    .orElse(null);

            if (playerClan != null) {
                ClanCommand clanCommand = new ClanCommand();
                if (clickedItem.getType() == Material.BARRIER) {
                    clanCommand.disbandClan(player, playerClan);
                    player.closeInventory();
                } else if (clickedItem.getType() == Material.PAPER) {
                    clanCommand.openManageMembersGUI(player, playerClan);
                }
            }
        } else if (event.getView().getTitle().startsWith("Manage Members: ")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            Player player = (Player) event.getWhoClicked();
            String memberName = clickedItem.getItemMeta().getDisplayName();
            Clan playerClan = ClanCommand.clans.stream()
                    .filter(clan -> clan.getOwner().equals(player.getName()))
                    .findFirst()
                    .orElse(null);

            if (playerClan != null && clickedItem.getType() == Material.PLAYER_HEAD) {
                ClanCommand clanCommand = new ClanCommand();
                clanCommand.removeMember(player, playerClan, memberName);
                player.closeInventory();
                clanCommand.openManageMembersGUI(player, playerClan);
            }
        }
    }
}
