package com.EstateCrawler.app.estates;

import com.EstateCrawler.app.shared.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Estate {
  private String id;
  private String offerUrl;
  private String title;
  private Property estate;
  private String developmentUrl;
  private Location location;
  private Price totalPrice;
  private Price rentPrice;
  private Float area;
  private Floor floor;
  private Room room;
  private LocalDateTime dateCreated;
  private String description;

  public EstateDTO toDTO() {
    return EstateDTO.of(
        this.id,
        this.offerUrl,
        this.title,
        this.estate,
        this.developmentUrl,
        this.location,
        this.totalPrice,
        this.rentPrice,
        this.area,
        this.floor,
        this.room,
        this.dateCreated,
        this.description);
  }

  public String toCSV() {
    return String.format(
        "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
        this.id,
        this.offerUrl,
        this.title,
        this.estate,
        this.developmentUrl,
        this.location,
        this.totalPrice,
        this.rentPrice,
        this.area,
        this.floor,
        this.room,
        this.dateCreated,
        this.description,
        System.lineSeparator());
  }
}
