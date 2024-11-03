package com.EstateCrawler.app.shared;

import lombok.Getter;

@Getter
public enum Property {
    FLAT("mieszkanie"),
    STUDIO("kawalerka"),
    HOUSE("dom"),
    INVESTMENT("inwestycja"),
    ROOM("pokoj"),
    PLOT("dzialka"),
    LOCAL("lokal"),
    WAREHOUSE("haleimagazyny"),
    GARAGE("garaz"),
    UNKNOWN("brak");

    private final String value;

    Property(String value) {
        this.value = value;
    }
}
