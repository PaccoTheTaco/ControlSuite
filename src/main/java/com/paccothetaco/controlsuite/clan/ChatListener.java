package com.paccothetaco.controlsuite.clan;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import com.paccothetaco.controlsuite.Main;
import com.paccothetaco.controlsuite.settings.ConfigManager;

public class ChatListener implements Listener {

    private final Main plugin;

    public ChatListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ConfigManager configManager = plugin.getConfigManager();

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
