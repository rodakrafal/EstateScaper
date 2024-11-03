package com.EstateCrawler.app.profiles;

import com.EstateCrawler.app.shared.Location;
import com.EstateCrawler.app.shared.Offer;
import com.EstateCrawler.app.shared.Property;

public record Profile(
    String name,
    Offer offer,
    Property property,
    Location location
) {
}