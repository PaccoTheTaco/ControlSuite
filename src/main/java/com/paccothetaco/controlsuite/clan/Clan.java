package com.paccothetaco.controlsuite.clan;

public class Clan {
    private String name;
    private String owner;

    public Clan(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
