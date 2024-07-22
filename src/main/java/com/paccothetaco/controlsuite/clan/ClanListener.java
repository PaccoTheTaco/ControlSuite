package com.paccothetaco.controlsuite.clan;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import com.paccothetaco.controlsuite.Main;
import org.bukkit.ChatColor;
import com.paccothetaco.controlsuite.settings.ConfigManager;

public class ClanListener implements Listener {

    private final Main plugin;

    public ClanListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        Player player = (Player) event.getWhoClicked();
        ConfigManager configManager = plugin.getConfigManager();

        if (!configManager.isClanSystemEnabled()) {
            return;
        }

        if (title.equals("Clans") || title.startsWith("Clan: ") || title.startsWith("Manage Members: ") || title.startsWith("Invite Player: ") || title.startsWith("Settings: ")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            if (title.equals("Clans")) {
                handleClansInventoryClick(player, clickedItem);
            } else if (title.startsWith("Settings: ")) {
                handleSettingsInventoryClick(player, clickedItem);
            } else if (title.startsWith("Manage Members: ")) {
                handleManageMembersInventoryClick(player, clickedItem);
            } else if (title.startsWith("Invite Player: ")) {
                handleInvitePlayerInventoryClick(player, clickedItem);
            } else if (title.startsWith("Clan: ")) {
            }
        }
    }

    private void handleClansInventoryClick(Player player, ItemStack clickedItem) {
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
    }

    private void handleSettingsInventoryClick(Player player, ItemStack clickedItem) {
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
            } else if (clickedItem.getType() == Material.OAK_SIGN) {
                clanCommand.openInvitePlayerGUI(player, playerClan);
            }
        }
    }

    private void handleManageMembersInventoryClick(Player player, ItemStack clickedItem) {
        String memberName = clickedItem.getItemMeta().getDisplayName();
        Clan playerClan = ClanCommand.clans.stream()
                .filter(clan -> clan.getOwner().equals(player.getName()))
                .findFirst()
                .orElse(null);

        if (playerClan != null && clickedItem.getType() == Material.PLAYER_HEAD) {
            if (playerClan.getOwner().equals(memberName)) {
                player.sendMessage("You cannot remove yourself from the clan.");
            } else {
                ClanCommand clanCommand = new ClanCommand();
                clanCommand.removeMember(player, playerClan, memberName);
                player.closeInventory();
                clanCommand.openManageMembersGUI(player, playerClan);
            }
        }
    }

    private void handleInvitePlayerInventoryClick(Player player, ItemStack clickedItem) {
        String inviteeName = clickedItem.getItemMeta().getDisplayName();
        Player invitee = Bukkit.getPlayer(inviteeName);
        Clan playerClan = ClanCommand.clans.stream()
                .filter(clan -> clan.getOwner().equals(player.getName()))
                .findFirst()
                .orElse(null);

        if (playerClan != null && invitee != null && invitee.isOnline()) {
            playerClan.addInvitation(inviteeName);
            invitee.sendMessage(ChatColor.GREEN + "You have been invited to the clan " + playerClan.getName() + ".");
            invitee.sendMessage(ChatColor.GREEN + "Type /clan accept " + playerClan.getName() + " to join or /clan decline " + playerClan.getName() + " to decline.");
            player.sendMessage(ChatColor.GREEN + "Invitation sent to " + inviteeName);
            player.closeInventory();
        }
    }
}
