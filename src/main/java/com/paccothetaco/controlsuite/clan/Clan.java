package com.paccothetaco.controlsuite.clan;

import java.util.ArrayList;
import java.util.List;

public class Clan {
    private String name;
    private String owner;
    private List<String> members;

    public Clan(String name, String owner) {
        this.name = name;
        this.owner = owner;
        this.members = new ArrayList<>();
        this.members.add(owner);
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getMembers() {
        return members;
    }

    public void addMember(String playerName) {
        if (!members.contains(playerName)) {
            members.add(playerName);
        }
    }

    public void removeMember(String playerName) {
        members.remove(playerName);
    }
}
