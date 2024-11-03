package com.EstateCrawler.app.shared;

import com.EstateCrawler.app.generics.EnumConvertible;
import lombok.Getter;

@Getter
public enum Offer implements EnumConvertible<String> {
  SALE("sprzedaz"),
  RENT("wynajem");

  private final String value;

  Offer(String value) {
    this.value = value;
  }
}
