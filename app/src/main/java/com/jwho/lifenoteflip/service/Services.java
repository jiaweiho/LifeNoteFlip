package com.jwho.lifenoteflip.service;

public enum Services {
    EVERNOTE,
    FACEBOOK,
    LASTFM,
    RUNKEEPER,
    UNKNOWN;

    @Override
    public String toString() {
        return name();
    }
}
