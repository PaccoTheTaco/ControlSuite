package com.paccothetaco.controlsuite;

import com.paccothetaco.controlsuite.Invsee.InvseeCommand;
import com.paccothetaco.controlsuite.clan.ClanCommand;
import com.paccothetaco.controlsuite.clan.ClanListener;
import com.paccothetaco.controlsuite.clan.ClanManager;
import com.paccothetaco.controlsuite.commands.AddFlyAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.commands.PurgeCommand;
import com.paccothetaco.controlsuite.commands.RemoveFlyAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.enderchest.AddAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.enderchest.EnderchestCommand;
import com.paccothetaco.controlsuite.enderchest.RemoveAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.fly.FlyCommand;
import com.paccothetaco.controlsuite.home.AddHomeAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.home.HomeCommand;
import com.paccothetaco.controlsuite.home.RemoveHomeAuthorizedPlayerCommand;
import com.paccothetaco.controlsuite.home.SetHomeCommand;
import com.paccothetaco.controlsuite.listeners.ChatListener;
import com.paccothetaco.controlsuite.listeners.PvPListener;
import com.paccothetaco.controlsuite.mute.MuteCommand;
import com.paccothetaco.controlsuite.mute.MuteManager;
import com.paccothetaco.controlsuite.settings.ConfigManager;
import com.paccothetaco.controlsuite.settings.SettingsCommand;
import com.paccothetaco.controlsuite.settings.SettingsListener;
import com.paccothetaco.controlsuite.tpa.TpaAcceptCommand;
import com.paccothetaco.controlsuite.tpa.TpaCommand;
import com.paccothetaco.controlsuite.tpa.TpaDenyCommand;
import com.paccothetaco.controlsuite.tpa.TpaManager;
import com.paccothetaco.controlsuite.warp.WarpCommand;
import com.paccothetaco.controlsuite.warp.SetWarpCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private ConfigManager configManager;
    private ClanManager clanManager;
    private ClanCommand clanCommand;
    private ClanListener clanListener;
    private MuteManager muteManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.clanManager = new ClanManager(this);
        this.clanCommand = new ClanCommand();
        this.clanListener = new ClanListener(this);
        this.muteManager = new MuteManager();

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
        this.getCommand("fly").setExecutor(new FlyCommand(configManager));
        this.getCommand("addfly").setExecutor(new AddFlyAuthorizedPlayerCommand(configManager));
        this.getCommand("removefly").setExecutor(new RemoveFlyAuthorizedPlayerCommand(configManager));
        this.getCommand("purge").setExecutor(new PurgeCommand());
        this.getCommand("mute").setExecutor(new MuteCommand(this));
        this.getCommand("unmute").setExecutor(new MuteCommand(this));

        getServer().getPluginManager().registerEvents(new ChatListener(this, muteManager), this);
        getServer().getPluginManager().registerEvents(new SettingsListener(configManager, this), this);

        if (configManager.isClanSystemEnabled()) {
            registerClanFeatures();
        }

        getServer().getPluginManager().registerEvents(new PvPListener(configManager), this);

        TpaManager tpaManager = new TpaManager();
        this.getCommand("tpa").setExecutor(new TpaCommand(tpaManager));
        this.getCommand("tpaaccept").setExecutor(new TpaAcceptCommand(tpaManager));
        this.getCommand("tpadeny").setExecutor(new TpaDenyCommand(tpaManager));
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

    public MuteManager getMuteManager() {
        return muteManager;
    }

    public void registerClanFeatures() {
        if (getCommand("clan") != null) {
            getCommand("clan").setExecutor(this.clanCommand);
        }
        getServer().getPluginManager().registerEvents(this.clanListener, this);
    }

    public void unregisterClanFeatures() {
        if (getCommand("clan") != null) {
            getCommand("clan").setExecutor(null);
        }
    }
}
