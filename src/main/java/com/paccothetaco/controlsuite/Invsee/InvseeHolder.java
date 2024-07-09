package com.paccothetaco.controlsuite.Invsee;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InvseeHolder implements InventoryHolder {
    private final Player player;

    public InvseeHolder(Player player) {
        this.player = player;
    }

    @Override
    public Inventory getInventory() {
        return player.getInventory();
    }

    public Player getPlayer() {
        return player;
    }
}
