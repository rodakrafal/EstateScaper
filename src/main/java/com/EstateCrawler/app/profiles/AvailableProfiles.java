package com.EstateCrawler.app.profiles;

import lombok.Getter;

@Getter
public enum AvailableProfiles {
    WARSAW("Warszawa"),
    LUBLIN("Lublin"),
    PULAWY("Pulawy");

    private final String profileName;

    AvailableProfiles(String profileName) {
        this.profileName = profileName;
    }
}
