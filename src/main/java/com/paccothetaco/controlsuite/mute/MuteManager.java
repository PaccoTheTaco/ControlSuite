package com.paccothetaco.controlsuite.mute;

import java.util.HashSet;
import java.util.Set;

public class MuteManager {
    private final Set<String> mutedPlayers;

    public MuteManager() {
        this.mutedPlayers = new HashSet<>();
    }

    public void mutePlayer(String playerName) {
        mutedPlayers.add(playerName);
    }

    public void unmutePlayer(String playerName) {
        mutedPlayers.remove(playerName);
    }

    public boolean isPlayerMuted(String playerName) {
        return mutedPlayers.contains(playerName);
    }
}
