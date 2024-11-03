package com.EstateCrawler.app.shared;

import lombok.Getter;

@Getter
public enum Floor {
    CELLAR("Suterena"),
    GROUND("Parter"),
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    FIFTH("5"),
    SIXTH("6"),
    SEVENTH("7"),
    EIGHTH("8"),
    NINTH("9"),
    TENTH("10"),
    ABOVE_TENTH("10+"),
    GARRET("Poddasze"),
    UNKNOWN("brak");

    private final String floor;

    Floor(String floor) {
        this.floor = floor;
    }
}
