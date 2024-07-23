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
            System.out.println("PvP status checked: " + pvpEnabled);

            if (event.getDamager() instanceof Player) {
                Player damager = (Player) event.getDamager();

                if (!pvpEnabled) {
                    event.setCancelled(true);
                    System.out.println("PvP damage cancelled for player: " + damager.getName());
                }
            } else if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();

                if (arrow.getShooter() instanceof Player) {
                    Player shooter = (Player) arrow.getShooter();

                    if (!pvpEnabled) {
                        event.setCancelled(true);
                        System.out.println("PvP damage cancelled for shooter: " + shooter.getName());
                    }
                }
            }
        }
    }
}
