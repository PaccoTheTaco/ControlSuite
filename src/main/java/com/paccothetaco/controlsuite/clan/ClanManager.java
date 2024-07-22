package com.paccothetaco.controlsuite.clan;

import com.paccothetaco.controlsuite.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClanManager {
    private final Main plugin;
    private File clansFile;
    private FileConfiguration clansConfig;

    public ClanManager(Main plugin) {
        this.plugin = plugin;
        this.clansFile = new File(plugin.getDataFolder(), "clans.yml");
        if (!clansFile.exists()) {
            clansFile.getParentFile().mkdirs();
            try {
                clansFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.clansConfig = YamlConfiguration.loadConfiguration(clansFile);
    }

    public void saveClans() {
        for (Clan clan : ClanCommand.clans) {
            clansConfig.set(clan.getName() + ".owner", clan.getOwner());
            clansConfig.set(clan.getName() + ".members", clan.getMembers());
            clansConfig.set(clan.getName() + ".invitations", clan.getInvitations());
        }
        try {
            clansConfig.save(clansFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadClans() {
        ClanCommand.clans.clear();
        for (String clanName : clansConfig.getKeys(false)) {
            String owner = clansConfig.getString(clanName + ".owner");
            List<String> members = clansConfig.getStringList(clanName + ".members");
            List<String> invitations = clansConfig.getStringList(clanName + ".invitations");

            Clan clan = new Clan(clanName, owner);
            for (String member : members) {
                if (!clan.getMembers().contains(member)) {
                    clan.addMember(member);
                }
            }
            for (String invitation : invitations) {
                if (!clan.getInvitations().contains(invitation)) {
                    clan.addInvitation(invitation);
                }
            }
            ClanCommand.clans.add(clan);
        }
    }
}
