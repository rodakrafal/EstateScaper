package com.EstateCrawler.app.shared;

import com.EstateCrawler.app.generics.EnumConverter;

public class RoomConverter extends EnumConverter<Room, Integer> {
  public RoomConverter() {
    super(Room.class);
  }
}
