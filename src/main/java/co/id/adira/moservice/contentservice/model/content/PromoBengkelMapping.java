package co.id.adira.moservice.contentservice.model.content;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "map_promo_bengkel")
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

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "promo_id", insertable = false, updatable = false)
  private Promo promo;
}