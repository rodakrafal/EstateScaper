package com.EstateCrawler.app;

import com.EstateCrawler.app.profiles.Profile;
import com.EstateCrawler.app.shared.Location;
import com.EstateCrawler.app.shared.Offer;
import com.EstateCrawler.app.shared.Property;

import static com.EstateCrawler.app.shared.Constant.*;

public record OtodomUrlBuilder(
        String uniqueId,
        Offer offer,
        Property property,
        Location location
) {
    public OtodomUrlBuilder(String uniqueId, Profile profile) {
        this(
                uniqueId,
                profile.offer(),
                profile.property(),
                profile.location()
        );
    }

    private String buildEndpoint() {
        return String.format(
                "%s/pl/wyniki/%s/%s/%s.json",
                this.uniqueId,
                this.offer.getValue(),
                this.property.getValue(),
                this.location.getFullLocation()
        );
    }

    private String buildQuery(int page) {
        return QUERY_PARAM +  page;
    }

    public String buildUrl(int page) {
        return BASE_URL_OLX + URL_API_SUFFIX + buildEndpoint() + buildQuery(page);
    }
}
