package com.EstateCrawler.app.shared;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@Data
@NoArgsConstructor
@Embeddable
public class Location {

  @Column(nullable = false)
  String province;

  @Column(nullable = false)
  String city;

  @Column(nullable = false)
  String street;

  String streetNumber;

  @Column(nullable = false)
  String district;

  @Column(nullable = false)
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
