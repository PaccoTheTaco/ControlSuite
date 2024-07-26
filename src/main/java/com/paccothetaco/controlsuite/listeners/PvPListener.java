package com.paccothetaco.controlsuite.listeners;

import com.paccothetaco.controlsuite.settings.ConfigManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvPListener implements Listener {

    private final ConfigManager configManager;

    public PvPListener(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player target = (Player) event.getEntity();
            boolean pvpEnabled = configManager.isPvpEnabled();

            if (event.getDamager() instanceof Player) {

                if (!pvpEnabled) {
                    event.setCancelled(true);
                }
            } else if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();

                if (arrow.getShooter() instanceof Player) {

                    if (!pvpEnabled) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
