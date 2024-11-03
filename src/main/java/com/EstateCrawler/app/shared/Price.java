package com.EstateCrawler.app.shared;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;


@Embeddable
@Data
@AllArgsConstructor(staticName = "of")
public class Price {
    Float value;
    String currency;
}
