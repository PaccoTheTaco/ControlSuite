package com.paccothetaco.controlsuite;

import com.paccothetaco.controlsuite.enderchest.EnderchestCommand;
import com.paccothetaco.controlsuite.enderchest.AddAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.enderchest.RemoveAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.Invsee.InvseeCommand;
import com.paccothetaco.controlsuite.Invsee.InvseeListener;
import com.paccothetaco.controlsuite.settings.SettingsCommand;
import com.paccothetaco.controlsuite.settings.SettingsListener;
import com.paccothetaco.controlsuite.settings.ConfigManager;
import com.paccothetaco.controlsuite.home.HomeCommand;
import com.paccothetaco.controlsuite.home.SetHomeCommand;
import com.paccothetaco.controlsuite.warp.WarpCommand;
import com.paccothetaco.controlsuite.warp.SetWarpCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.getCommand("invsee").setExecutor(new InvseeCommand());
        this.getCommand("ec").setExecutor(new EnderchestCommand(configManager));
        this.getCommand("settings").setExecutor(new SettingsCommand());
        this.getCommand("addec").setExecutor(new AddAuthorizedPlayerCommand(configManager));
        this.getCommand("removeec").setExecutor(new RemoveAuthorizedPlayerCommand(configManager));
        this.getCommand("home").setExecutor(new HomeCommand(getConfig()));
        this.getCommand("sethome").setExecutor(new SetHomeCommand(getConfig()));
        this.getCommand("warp").setExecutor(new WarpCommand(getConfig()));
        this.getCommand("setwarp").setExecutor(new SetWarpCommand(getConfig()));
        getServer().getPluginManager().registerEvents(new InvseeListener(), this);
        getServer().getPluginManager().registerEvents(new SettingsListener(configManager), this);
    }

    @Override
    public void onDisable() {
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
