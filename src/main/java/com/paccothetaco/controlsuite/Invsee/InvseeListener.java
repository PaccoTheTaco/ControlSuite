package com.paccothetaco.controlsuite.Invsee;

import com.paccothetaco.controlsuite.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class InvseeListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;

        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof InvseeHolder) {
            InvseeHolder invseeHolder = (InvseeHolder) holder;
            Player target = invseeHolder.getTarget();
            int slot = event.getSlot();
            ItemStack cursorItem = event.getCursor();
            ItemStack currentItem = event.getCurrentItem();

            if (clickedInventory.equals(event.getView().getTopInventory())) {
                if (slot >= 41 && slot < 45) {
                    event.setCancelled(true);
                    return;
                }

                event.setCancelled(true);
                if (slot < 36) {
                    target.getInventory().setItem(slot, cursorItem);
                    event.getView().getTopInventory().setItem(slot, cursorItem);
                } else {
                    switch (slot) {
                        case 36:
                            target.getInventory().setHelmet(cursorItem);
                            event.getView().getTopInventory().setItem(slot, cursorItem);
                            break;
                        case 37:
                            target.getInventory().setChestplate(cursorItem);
                            event.getView().getTopInventory().setItem(slot, cursorItem);
                            break;
                        case 38:
                            target.getInventory().setLeggings(cursorItem);
                            event.getView().getTopInventory().setItem(slot, cursorItem);
                            break;
                        case 39:
                            target.getInventory().setBoots(cursorItem);
                            event.getView().getTopInventory().setItem(slot, cursorItem);
                            break;
                        case 40:
                            target.getInventory().setItemInOffHand(cursorItem);
                            event.getView().getTopInventory().setItem(slot, cursorItem);
                            break;
                        default:
                            break;
                    }
                }

                target.updateInventory();
                event.setCursor(currentItem);
            } else {
                event.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof InvseeHolder) {
            InvseeHolder invseeHolder = (InvseeHolder) holder;
            invseeHolder.getTarget().updateInventory();
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof InvseeHolder) {
            InvseeHolder invseeHolder = (InvseeHolder) holder;
            Player target = invseeHolder.getTarget();

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (target.getOpenInventory().getTopInventory().getHolder() instanceof InvseeHolder) {
                        Inventory invseeInventory = target.getOpenInventory().getTopInventory();
                        for (int i = 0; i < 36; i++) {
                            invseeInventory.setItem(i, target.getInventory().getItem(i));
                        }

                        invseeInventory.setItem(36, target.getInventory().getHelmet());
                        invseeInventory.setItem(37, target.getInventory().getChestplate());
                        invseeInventory.setItem(38, target.getInventory().getLeggings());
                        invseeInventory.setItem(39, target.getInventory().getBoots());
                        invseeInventory.setItem(40, target.getInventory().getItemInOffHand());

                        ItemStack barrier = new ItemStack(Material.BARRIER);
                        for (int i = 41; i < 45; i++) {
                            invseeInventory.setItem(i, barrier);
                        }
                    } else {
                        this.cancel();
                    }
                }
            }.runTaskTimer(Main.getPlugin(Main.class), 0L, 10L);
        }
    }
}
