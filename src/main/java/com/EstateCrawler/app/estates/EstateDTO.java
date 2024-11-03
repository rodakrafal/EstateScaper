package com.EstateCrawler.app.estates;

import com.EstateCrawler.app.shared.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;

@AllArgsConstructor(staticName = "of")
@Data
@NoArgsConstructor
@Entity
@Table(name = "Estates")
public class EstateDTO {
  @Id private String id;

  @Column(name = "offer_url", unique = true, nullable = false)
  private String offerUrl;

  @Column(nullable = false)
  private String title;

  @Convert(converter = PropertyConverter.class)
  @Column(nullable = false)
  private Property property;

  @Column(name = "development_url")
  private String developmentUrl;

  private Location location;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "total_price_value")),
    @AttributeOverride(name = "currency", column = @Column(name = "total_price_currency"))
  })
  private Price totalPrice;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "rent_price_value")),
    @AttributeOverride(name = "currency", column = @Column(name = "rent_price_currency"))
  })
  private Price rentPrice;

  @Column(nullable = false)
  private Float area;

  @Convert(converter = FloorConverter.class)
  @Column(name = "floor_number", nullable = false)
  private Floor floor;

  @Convert(converter = RoomConverter.class)
  @Column(name = "rooms_number", nullable = false)
  private Room room;

  @Column(nullable = false)
  private LocalDateTime dateCreated;

  @JdbcTypeCode(Types.LONGVARCHAR)
  private String description;
}
