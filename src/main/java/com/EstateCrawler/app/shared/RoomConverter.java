package com.EstateCrawler.app.shared;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class RoomConverter implements AttributeConverter<Room, Integer> {
  @Override
  public Integer convertToDatabaseColumn(Room attribute) {
    return attribute.getValue();
  }

  @Override
  public Room convertToEntityAttribute(Integer dbData) {
    for (Room room : Room.values()) {
      if (room.getValue() == dbData) {
        return room;
      }
    }
    return Room.UNKNOWN;
  }
}
