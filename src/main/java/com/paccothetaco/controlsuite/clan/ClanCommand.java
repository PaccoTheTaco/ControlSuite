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
import org.bukkit.configuration.file.FileConfiguration;
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

            player.sendMessage(ChatColor.GREEN + "Clan command executed.");

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
                        player.sendMessage(ChatColor.RED + "No invitation found from clan " + clanName + ".");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /clan decline <clan>");
                }
            } else {
                player.sendMessage(ChatColor.GREEN + "Opening clan GUI.");
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

    public void openInviteListGUI(Player player) {
        Inventory inviteGUI = Bukkit.createInventory(null, 54, "Clan Invites");

        ClanCommand.clans.stream()
                .filter(clan -> clan.getInvitations().contains(player.getName()))
                .forEach(clan -> {
                    ItemStack inviteItem = new ItemStack(Material.PAPER);
                    ItemMeta meta = inviteItem.getItemMeta();
                    meta.setDisplayName(clan.getName());
                    List<String> lore = new ArrayList<>();
                    lore.add("Click to respond to the invite.");
                    meta.setLore(lore);
                    inviteItem.setItemMeta(meta);
                    inviteGUI.addItem(inviteItem);
                });

        player.openInventory(inviteGUI);
    }

    public void openInviteResponseGUI(Player player, Clan clan) {
        Inventory responseGUI = Bukkit.createInventory(null, 9, "Respond to " + clan.getName());

        ItemStack acceptItem = new ItemStack(Material.GREEN_WOOL);
        ItemMeta acceptMeta = acceptItem.getItemMeta();
        acceptMeta.setDisplayName("Accept");
        acceptItem.setItemMeta(acceptMeta);
        responseGUI.setItem(3, acceptItem);

        ItemStack declineItem = new ItemStack(Material.RED_WOOL);
        ItemMeta declineMeta = declineItem.getItemMeta();
        declineMeta.setDisplayName("Decline");
        declineItem.setItemMeta(declineMeta);
        responseGUI.setItem(5, declineItem);

        player.openInventory(responseGUI);
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

        ItemStack inviteItem = new ItemStack(Material.PAPER);
        ItemMeta inviteMeta = inviteItem.getItemMeta();
        inviteMeta.setDisplayName("Clan Invites");
        inviteItem.setItemMeta(inviteMeta);
        clanGUI.setItem(45, inviteItem);

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

    public static void loadClans(FileConfiguration config) {
        clans.clear();
        if (config.contains("clans")) {
            config.getConfigurationSection("clans").getKeys(false).forEach(clanName -> {
                String owner = config.getString("clans." + clanName + ".owner");
                List<String> members = config.getStringList("clans." + clanName + ".members");
                List<String> invitations = config.getStringList("clans." + clanName + ".invitations");

                Clan clan = new Clan(clanName, owner);
                clan.getMembers().addAll(members);
                clan.getInvitations().addAll(invitations);
                clans.add(clan);
            });
        }
    }

    public static void saveClans(FileConfiguration config) {
        config.set("clans", null);
        clans.forEach(clan -> {
            String path = "clans." + clan.getName();
            config.set(path + ".owner", clan.getOwner());
            config.set(path + ".members", new ArrayList<>(clan.getMembers()));
            config.set(path + ".invitations", new ArrayList<>(clan.getInvitations()));
        });
    }
}
