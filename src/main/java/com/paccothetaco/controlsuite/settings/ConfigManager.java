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

    public void saveSetting(String setting, Object value) {
        config.set(setting, value);
        saveConfig();
        System.out.println("Setting saved: " + setting + " = " + value);
    }

    public void loadConfig() {
        try {
            config.load(configFile);
            System.out.println("Config loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSetting(String setting) {
        return config.getString(setting, "noone");
    }

    public boolean isPvpEnabled() {
        loadConfig();
        boolean pvpEnabled = config.getBoolean("pvp-enabled", false);
        System.out.println("PvP enabled: " + pvpEnabled);
        return pvpEnabled;
    }

    public boolean isClanSystemEnabled() {
        loadConfig();
        return config.getBoolean("clan-system-enabled", true);
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

    public void addHomeAuthorizedPlayer(UUID playerUUID) {
        List<String> authorizedPlayers = config.getStringList("homeAuthorizedPlayers");
        authorizedPlayers.add(playerUUID.toString());
        config.set("homeAuthorizedPlayers", authorizedPlayers);
        saveConfig();
    }

    public void removeHomeAuthorizedPlayer(UUID playerUUID) {
        List<String> authorizedPlayers = config.getStringList("homeAuthorizedPlayers");
        authorizedPlayers.remove(playerUUID.toString());
        config.set("homeAuthorizedPlayers", authorizedPlayers);
        saveConfig();
    }

    public boolean isHomePlayerAuthorized(UUID playerUUID) {
        List<String> authorizedPlayers = config.getStringList("homeAuthorizedPlayers");
        return authorizedPlayers.contains(playerUUID.toString());
    }

    public void addFlyAuthorizedPlayer(UUID playerUUID) {
        List<String> authorizedPlayers = config.getStringList("flyAuthorizedPlayers");
        authorizedPlayers.add(playerUUID.toString());
        config.set("flyAuthorizedPlayers", authorizedPlayers);
        saveConfig();
    }

    public void removeFlyAuthorizedPlayer(UUID playerUUID) {
        List<String> authorizedPlayers = config.getStringList("flyAuthorizedPlayers");
        authorizedPlayers.remove(playerUUID.toString());
        config.set("flyAuthorizedPlayers", authorizedPlayers);
        saveConfig();
    }

    public boolean isFlyPlayerAuthorized(UUID playerUUID) {
        List<String> authorizedPlayers = config.getStringList("flyAuthorizedPlayers");
        return authorizedPlayers.contains(playerUUID.toString());
    }

    private void saveConfig() {
        try {
            config.save(configFile);
            System.out.println("Config saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
