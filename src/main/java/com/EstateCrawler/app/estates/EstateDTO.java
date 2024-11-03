package com.EstateCrawler.app.estates;

import com.EstateCrawler.app.shared.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(
    name = "Estates",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class EstateDTO {
  @Id private String id;
  private String offerUrl;
  private String title;
  private Property estate;
  private String developmentUrl;
  private Location location;
  private Price totalPrice;
  private Price rentPrice;
  private Float area;

  @Enumerated(EnumType.STRING)
  private Floor floor;

  @Enumerated(EnumType.STRING)
  private Room room;

  private LocalDateTime dateCreated;

  @JdbcTypeCode(Types.LONGVARCHAR)
  private String description;
}
