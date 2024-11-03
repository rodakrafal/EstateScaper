package com.EstateCrawler.app.shared;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class FloorConverter implements AttributeConverter<Floor, String> {
  @Override
  public String convertToDatabaseColumn(Floor attribute) {
    return attribute.getFloor();
  }

  @Override
  public Floor convertToEntityAttribute(String dbData) {
    for (Floor floor : Floor.values()) {
      if (floor.getFloor().equals(dbData)) {
        return floor;
      }
    }
    return Floor.UNKNOWN;
  }
}
