package com.paccothetaco.controlsuite.tpa;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TpaManager {
    private final Map<Player, Player> tpaRequests = new HashMap<>();

    public void addRequest(Player sender, Player target) {
        tpaRequests.put(target, sender);
    }

    public Player getRequester(Player target) {
        return tpaRequests.get(target);
    }

    public void removeRequest(Player target) {
        tpaRequests.remove(target);
    }

    public boolean hasRequest(Player target) {
        return tpaRequests.containsKey(target);
    }
}
