package com.EstateCrawler.app.shared;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class PropertyConverter implements AttributeConverter<Property, String> {
  @Override
  public String convertToDatabaseColumn(Property attribute) {
    return attribute.getValue();
  }

  @Override
  public Property convertToEntityAttribute(String dbData) {
    for (Property property : Property.values()) {
      if (property.getValue().equals(dbData)) {
        return property;
      }
    }
    return Property.UNKNOWN;
  }
}
