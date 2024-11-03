package com.EstateCrawler.app.shared;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@Data
@NoArgsConstructor
@Embeddable
public class Price {
  Float value;
  String currency;
}
