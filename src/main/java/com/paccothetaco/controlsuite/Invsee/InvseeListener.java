package com.paccothetaco.controlsuite.Invsee;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InvseeListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof InvseeHolder) {
            InvseeHolder invseeHolder = (InvseeHolder) holder;
            Player targetPlayer = invseeHolder.getPlayer();
            PlayerInventory targetInventory = targetPlayer.getInventory();

            int slot = event.getRawSlot();
            ItemStack clickedItem = event.getCurrentItem();

            event.setCancelled(false);

            if (slot >= 0 && slot < 54) {
                if (slot < 5) {
                    switch (slot) {
                        case 0:
                            targetInventory.setHelmet(clickedItem);
                            break;
                        case 1:
                            targetInventory.setChestplate(clickedItem);
                            break;
                        case 2:
                            targetInventory.setLeggings(clickedItem);
                            break;
                        case 3:
                            targetInventory.setBoots(clickedItem);
                            break;
                        case 4:
                            targetInventory.setItemInOffHand(clickedItem);
                            break;
                    }
                } else if (slot >= 9 && slot < 45) {
                    targetInventory.setItem(slot - 9, clickedItem);
                }
            }
        }
    }
}
