package com.paccothetaco.controlsuite;

import com.paccothetaco.controlsuite.enderchest.EnderchestCommand;
import com.paccothetaco.controlsuite.Invsee.InvseeCommand;
import com.paccothetaco.controlsuite.Invsee.InvseeListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("invsee").setExecutor(new InvseeCommand());
        this.getCommand("ec").setExecutor(new EnderchestCommand());
        getServer().getPluginManager().registerEvents(new InvseeListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
