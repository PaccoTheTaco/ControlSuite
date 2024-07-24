package com.paccothetaco.controlsuite.listeners;

import com.paccothetaco.controlsuite.Main;
import com.paccothetaco.controlsuite.clan.Clan;
import com.paccothetaco.controlsuite.clan.ClanCommand;
import com.paccothetaco.controlsuite.mute.MuteManager;
import com.paccothetaco.controlsuite.settings.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final Main plugin;
    private final ConfigManager configManager;
    private final MuteManager muteManager;

    public ChatListener(Main plugin, MuteManager muteManager) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.muteManager = muteManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (muteManager.isPlayerMuted(player.getName())) {
            player.sendMessage("You are muted and cannot send messages.");
            event.setCancelled(true);
            return;
        }

        if (!configManager.isClanSystemEnabled()) {
            return;
        }

        if (player.hasMetadata("creatingClan")) {
            event.setCancelled(true);
            String clanName = event.getMessage();

            Clan newClan = new Clan(clanName, player.getName());
            ClanCommand.clans.add(newClan);
            player.sendMessage("Clan '" + clanName + "' has been created!");

            player.removeMetadata("creatingClan", plugin);
        }
    }
}
