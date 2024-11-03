package com.EstateCrawler.app.shared;

import com.EstateCrawler.app.generics.EnumConvertible;
import lombok.Getter;

@Getter
public enum Floor implements EnumConvertible<String> {
  CELLAR("Suterena"),
  GROUND("Parter"),
  FIRST("1"),
  SECOND("2"),
  THIRD("3"),
  FOURTH("4"),
  FIFTH("5"),
  SIXTH("6"),
  SEVENTH("7"),
  EIGHTH("8"),
  NINTH("9"),
  TENTH("10"),
  ABOVE_TENTH("10+"),
  GARRET("Poddasze"),
  UNKNOWN("brak");

  private final String value;

  Floor(String value) {
    this.value = value;
  }
}
