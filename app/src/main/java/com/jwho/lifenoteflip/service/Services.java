package com.jwho.lifenoteflip.service;

public enum Services {
    EVERNOTE("Evernote"),
    FACEBOOK("Facebook"),
    LASTFM("LastFm"),
    RUNKEEPER("RunKeeper"),
    UNKNOWN("Unknown");

    private String name;

    Services(String name) {
        this.name = name;
    }

    public boolean isEqual(String name) {
        return this.name.equals(name);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
