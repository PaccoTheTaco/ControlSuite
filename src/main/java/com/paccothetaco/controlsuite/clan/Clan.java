package com.paccothetaco.controlsuite.clan;

import java.util.ArrayList;
import java.util.List;

public class Clan {
    private String name;
    private String owner;
    private List<String> members;
    private List<String> invitations;

    public Clan(String name, String owner) {
        this.name = name;
        this.owner = owner;
        this.members = new ArrayList<>();
        this.invitations = new ArrayList<>();
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

    public List<String> getInvitations() {
        return invitations;
    }

    public void addMember(String playerName) {
        if (!members.contains(playerName)) {
            members.add(playerName);
        }
    }

    public void removeMember(String playerName) {
        members.remove(playerName);
    }

    public void addInvitation(String playerName) {
        if (!invitations.contains(playerName)) {
            invitations.add(playerName);
        }
    }

    public void removeInvitation(String playerName) {
        invitations.remove(playerName);
    }
}
