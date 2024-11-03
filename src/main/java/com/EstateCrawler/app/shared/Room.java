package com.EstateCrawler.app.shared;

import com.EstateCrawler.app.generics.EnumConvertible;
import lombok.Getter;

@Getter
public enum Room implements EnumConvertible<Integer> {
  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  TEN(10),
  UNKNOWN(-1);

  private final Integer value;

  Room(Integer value) {
    this.value = value;
  }
}
