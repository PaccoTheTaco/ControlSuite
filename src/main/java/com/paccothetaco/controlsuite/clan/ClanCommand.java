package com.paccothetaco.controlsuite.clan;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
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
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ClanCommand implements CommandExecutor, Listener {

    public static List<Clan> clans = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0 && args[0].equalsIgnoreCase("invite")) {
                if (args.length == 2) {
                    String inviteeName = args[1];
                    Player invitee = Bukkit.getPlayer(inviteeName);
                    if (invitee != null && invitee.isOnline()) {
                        Clan playerClan = getPlayerClan(player.getName());
                        if (playerClan != null) {
                            playerClan.addInvitation(inviteeName);
                            sendInvitationMessage(invitee, playerClan);
                            player.sendMessage(ChatColor.GREEN + "Invitation sent to " + inviteeName);
                        } else {
                            player.sendMessage(ChatColor.RED + "You are not in a clan.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Player not found or not online.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /clan invite <player>");
                }
            } else if (args.length > 0 && args[0].equalsIgnoreCase("accept")) {
                if (args.length == 2) {
                    String clanName = args[1];
                    Clan clan = getClanByName(clanName);
                    if (clan != null && clan.getInvitations().contains(player.getName())) {
                        clan.addMember(player.getName());
                        clan.removeInvitation(player.getName());
                        player.sendMessage(ChatColor.GREEN + "You have joined the clan " + clan.getName() + ".");
                    } else {
                        player.sendMessage(ChatColor.RED + "No invitation found from clan " + clanName + ".");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /clan accept <clan>");
                }
            } else if (args.length > 0 && args[0].equalsIgnoreCase("decline")) {
                if (args.length == 2) {
                    String clanName = args[1];
                    Clan clan = getClanByName(clanName);
                    if (clan != null && clan.getInvitations().contains(player.getName())) {
                        clan.removeInvitation(player.getName());
                        player.sendMessage(ChatColor.GREEN + "You have declined the invitation from clan " + clan.getName() +".");
                    } else {
                        player.sendMessage(ChatColor.RED + "No invitation found from clan " + clanName +".");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /clan decline <clan>");
                }
            } else {
                openClanGUI(player);
            }
        } else {
            sender.sendMessage("This command can only be run by a player.");
        }
        return true;
    }

    private void sendInvitationMessage(Player invitee, Clan clan) {
        TextComponent message = new TextComponent("You have been invited to the clan " + clan.getName() + ". ");
        TextComponent accept = new TextComponent("[Accept]");
        accept.setColor(ChatColor.GREEN);
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan accept " + clan.getName()));

        TextComponent decline = new TextComponent("[Decline]");
        decline.setColor(ChatColor.RED);
        decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan decline " + clan.getName()));

        message.addExtra(accept);
        message.addExtra(" ");
        message.addExtra(decline);

        invitee.spigot().sendMessage(message);
    }

    public void openClanGUI(Player player) {
        Inventory clanGUI = Bukkit.createInventory(null, 54, "Clans");

        for (int i = 0; i < clans.size(); i++) {
            Clan clan = clans.get(i);
            ItemStack clanItem = new ItemStack(Material.CYAN_BANNER);
            ItemMeta meta = clanItem.getItemMeta();
            meta.setDisplayName(clan.getName());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.RED + "Owner: " + ChatColor.WHITE + clan.getOwner());
            meta.setLore(lore);
            clanItem.setItemMeta(meta);
            clanGUI.setItem(i, clanItem);
        }

        Clan playerClan = getPlayerClan(player.getName());

        if (playerClan == null) {
            ItemStack createClanItem = new ItemStack(Material.EMERALD);
            ItemMeta meta = createClanItem.getItemMeta();
            meta.setDisplayName("Create a new Clan");
            createClanItem.setItemMeta(meta);
            clanGUI.setItem(53, createClanItem);
        } else {
            ItemStack clanSettingsItem = new ItemStack(Material.CRAFTING_TABLE);
            ItemMeta meta = clanSettingsItem.getItemMeta();
            meta.setDisplayName("Clan Settings");
            clanSettingsItem.setItemMeta(meta);
            clanGUI.setItem(53, clanSettingsItem);
        }

        player.openInventory(clanGUI);
    }

    public void openClanMembersGUI(Player player, Clan clan) {
        Inventory membersGUI = Bukkit.createInventory(null, 54, "Clan: " + clan.getName());

        for (int i = 0; i < clan.getMembers().size(); i++) {
            String member = clan.getMembers().get(i);
            ItemStack skull = getPlayerSkull(member);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setDisplayName(member);
            skull.setItemMeta(meta);
            membersGUI.setItem(i, skull);
        }

        player.openInventory(membersGUI);
    }

    private ItemStack getPlayerSkull(String playerName) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public Clan getPlayerClan(String playerName) {
        for (Clan clan : clans) {
            if (clan.getOwner().equals(playerName) || clan.getMembers().contains(playerName)) {
                return clan;
            }
        }
        return null;
    }

    public Clan getClanByName(String name) {
        for (Clan clan : clans) {
            if (clan.getName().equalsIgnoreCase(name)) {
                return clan;
            }
        }
        return null;
    }

    public void openClanSettingsGUI(Player player, Clan clan) {
        Inventory settingsGUI = Bukkit.createInventory(null, 54, "Settings: " + clan.getName());

        ItemStack disbandItem = new ItemStack(Material.BARRIER);
        ItemMeta disbandMeta = disbandItem.getItemMeta();
        disbandMeta.setDisplayName("Disband Clan");
        disbandItem.setItemMeta(disbandMeta);
        settingsGUI.setItem(22, disbandItem);

        ItemStack manageMembersItem = new ItemStack(Material.PAPER);
        ItemMeta manageMembersMeta = manageMembersItem.getItemMeta();
        manageMembersMeta.setDisplayName("Manage Members");
        manageMembersItem.setItemMeta(manageMembersMeta);
        settingsGUI.setItem(23, manageMembersItem);

        ItemStack invitePlayerItem = new ItemStack(Material.OAK_SIGN);
        ItemMeta invitePlayerMeta = invitePlayerItem.getItemMeta();
        invitePlayerMeta.setDisplayName("Invite Player");
        invitePlayerItem.setItemMeta(invitePlayerMeta);
        settingsGUI.setItem(24, invitePlayerItem);

        player.openInventory(settingsGUI);
    }

    public void disbandClan(Player player, Clan clan) {
        clans.remove(clan);
        player.sendMessage("Clan '" + clan.getName() + "' has been disbanded!");
    }

    public void openManageMembersGUI(Player player, Clan clan) {
        Inventory manageMembersGUI = Bukkit.createInventory(null, 54, "Manage Members: " + clan.getName());

        for (int i = 0; i < clan.getMembers().size(); i++) {
            String member = clan.getMembers().get(i);
            ItemStack skull = getPlayerSkull(member);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setDisplayName(member);
            meta.setLore(List.of("Click to remove"));
            skull.setItemMeta(meta);
            manageMembersGUI.setItem(i, skull);
        }

        player.openInventory(manageMembersGUI);
    }

    public void openInvitePlayerGUI(Player player, Clan clan) {
        Inventory invitePlayerGUI = Bukkit.createInventory(null, 54, "Invite Player: " + clan.getName());

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!clan.getMembers().contains(onlinePlayer.getName()) && !clan.getInvitations().contains(onlinePlayer.getName())) {
                ItemStack skull = getPlayerSkull(onlinePlayer.getName());
                SkullMeta meta = (SkullMeta) skull.getItemMeta();
                meta.setDisplayName(onlinePlayer.getName());
                meta.setLore(List.of("Click to invite"));
                skull.setItemMeta(meta);
                invitePlayerGUI.addItem(skull);
            }
        }

        player.openInventory(invitePlayerGUI);
    }

    public void removeMember(Player player, Clan clan, String memberName) {
        if (clan.getMembers().remove(memberName)) {
            player.sendMessage("Member '" + memberName + "' has been removed from the clan.");
        } else {
            player.sendMessage("Member '" + memberName + "' is not in the clan.");
        }
    }
}
