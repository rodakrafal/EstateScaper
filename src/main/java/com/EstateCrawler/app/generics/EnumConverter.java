package com.EstateCrawler.app.generics;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class EnumConverter<T extends EnumConvertible<V>, V> implements AttributeConverter<T, V> {

  private final Class<T> enumType;

  public EnumConverter(Class<T> enumType) {
    this.enumType = enumType;
  }

  @Override
  public V convertToDatabaseColumn(T attribute) {
    return attribute.getValue();
  }

  @Override
  public T convertToEntityAttribute(V dbData) {
    for (T enumValue : enumType.getEnumConstants()) {
      if (enumValue.getValue().equals(dbData)) {
        return enumValue;
      }
    }
    return null;
  }
}
