package co.id.adira.moservice.contentservice.model.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "map_promo_bengkel", schema = "db_content")
public class PromoBengkelMapping {
  @Id
  private Long id;

  @Column(name = "promo_id")
  private Long promoId;

  @Column(name = "bengkel_id") 
  private Long bengkelId;

  public Long getBengkelId() {
    return bengkelId;
  }
}