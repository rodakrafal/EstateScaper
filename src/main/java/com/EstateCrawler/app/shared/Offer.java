package com.EstateCrawler.app.shared;

public enum Offer {
    SALE("sprzedaz"),
    RENT("wynajem");

    private final String value;

    Offer(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}