package com.EstateCrawler.app.shared;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Embeddable
public class Location {

  String province;
  String city;
  String street;
  String streetNumber;
  String district;
  String neighborhood;

  private String getOrEmpty(String value) {
    return value == null ? "" : value;
  }

  public String[] getParts() {
    return new String[] {
      this.province,
      this.city,
      this.street,
      this.streetNumber,
      getOrEmpty(this.district),
      getOrEmpty(this.neighborhood)
    };
  }

  public String getFullLocation() {
    return String.join("/", getParts()).replaceAll("/+", "/").replaceAll("/+$", "");
  }
}
