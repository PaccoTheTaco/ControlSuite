package com.paccothetaco.controlsuite.settings;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ConfigManager {

    private final File configFile;
    private final FileConfiguration config;

    public ConfigManager(JavaPlugin plugin) {
        configFile = new File(plugin.getDataFolder(), "settings.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveSetting(String setting, String value) {
        config.set(setting, value);
        saveConfig();
    }

    public String getSetting(String setting) {
        return config.getString(setting, "noone");
    }

    public void addAuthorizedPlayer(UUID playerUUID) {
        List<String> authorizedPlayers = config.getStringList("authorizedPlayers");
        authorizedPlayers.add(playerUUID.toString());
        config.set("authorizedPlayers", authorizedPlayers);
        saveConfig();
    }

    public void removeAuthorizedPlayer(UUID playerUUID) {
        List<String> authorizedPlayers = config.getStringList("authorizedPlayers");
        authorizedPlayers.remove(playerUUID.toString());
        config.set("authorizedPlayers", authorizedPlayers);
        saveConfig();
    }

    public boolean isPlayerAuthorized(UUID playerUUID) {
        List<String> authorizedPlayers = config.getStringList("authorizedPlayers");
        return authorizedPlayers.contains(playerUUID.toString());
    }

    private void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
