package com.EstateCrawler.app.shared;

import com.EstateCrawler.app.generics.EnumConverter;

public class FloorConverter extends EnumConverter<Floor, String> {
  public FloorConverter() {
    super(Floor.class);
  }
}
