package com.paccothetaco.controlsuite;

import com.paccothetaco.controlsuite.Invsee.InvseeListener;
import com.paccothetaco.controlsuite.clan.*;
import com.paccothetaco.controlsuite.enderchest.EnderchestCommand;
import com.paccothetaco.controlsuite.enderchest.AddAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.enderchest.RemoveAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.Invsee.InvseeCommand;
import com.paccothetaco.controlsuite.settings.SettingsCommand;
import com.paccothetaco.controlsuite.settings.ConfigManager;
import com.paccothetaco.controlsuite.home.HomeCommand;
import com.paccothetaco.controlsuite.home.SetHomeCommand;
import com.paccothetaco.controlsuite.home.AddHomeAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.home.RemoveHomeAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.settings.SettingsListener;
import com.paccothetaco.controlsuite.warp.WarpCommand;
import com.paccothetaco.controlsuite.warp.SetWarpCommand;
import com.paccothetaco.controlsuite.commands.GiveAllPermsCommand;
import com.paccothetaco.controlsuite.commands.GamemodeShort;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private ConfigManager configManager;
    private ClanManager clanManager;
    private ClanCommand clanCommand;
    private ClanListener clanListener;
    private ChatListener chatListener;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.clanManager = new ClanManager(this);
        this.clanCommand = new ClanCommand();
        this.clanListener = new ClanListener(this);
        this.chatListener = new ChatListener(this);

        this.clanManager.loadClans();

        this.getCommand("invsee").setExecutor(new InvseeCommand());
        this.getCommand("ec").setExecutor(new EnderchestCommand(configManager));
        this.getCommand("settings").setExecutor(new SettingsCommand());
        this.getCommand("addec").setExecutor(new AddAuthorizedPlayerCommand(configManager));
        this.getCommand("removeec").setExecutor(new RemoveAuthorizedPlayerCommand(configManager));
        this.getCommand("home").setExecutor(new HomeCommand(getConfig(), configManager));
        this.getCommand("sethome").setExecutor(new SetHomeCommand(getConfig(), configManager));
        this.getCommand("addhome").setExecutor(new AddHomeAuthorizedPlayerCommand(configManager));
        this.getCommand("removehome").setExecutor(new RemoveHomeAuthorizedPlayerCommand(configManager));
        this.getCommand("warp").setExecutor(new WarpCommand(getConfig()));
        this.getCommand("setwarp").setExecutor(new SetWarpCommand(getConfig()));
        this.getCommand("giveallperms").setExecutor(new GiveAllPermsCommand(configManager));
        this.getCommand("gm").setExecutor(new GamemodeShort());

        getServer().getPluginManager().registerEvents(new InvseeListener(), this);
        getServer().getPluginManager().registerEvents(new SettingsListener(configManager, this), this);

        if (configManager.isClanSystemEnabled()) {
            registerClanFeatures();
        }
    }

    @Override
    public void onDisable() {
        this.clanManager.saveClans();
        unregisterClanFeatures();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ClanManager getClanManager() {
        return clanManager;
    }

    public void registerClanFeatures() {
        PluginCommand clanCommand = getCommand("clan");
        if (clanCommand != null) {
            clanCommand.setExecutor(this.clanCommand);
        }
        getServer().getPluginManager().registerEvents(this.clanListener, this);
        getServer().getPluginManager().registerEvents(this.chatListener, this);
    }

    public void unregisterClanFeatures() {
        PluginCommand clanCommand = getCommand("clan");
        if (clanCommand != null) {
            clanCommand.setExecutor(null);
        }
        AsyncPlayerChatEvent.getHandlerList().unregister(this.chatListener);
        InventoryClickEvent.getHandlerList().unregister(this.clanListener);
    }
}
