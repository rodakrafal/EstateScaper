package com.EstateCrawler.app.shared;

import com.EstateCrawler.app.generics.EnumConverter;

public class PropertyConverter extends EnumConverter<Property, String> {
  public PropertyConverter() {
    super(Property.class);
  }
}
