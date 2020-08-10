package co.id.adira.moservice.contentservice.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import co.id.adira.moservice.model.User;

@Entity
@Table(name = "tr_promo_user")
public class Voucher {
  @Id
  private Long id;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "promo_id", referencedColumnName = "id", insertable = false, updatable = false)
  @JsonManagedReference
  private Promo promo;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "redeem_date")
  private Date redeemDate;

  public Long getId() {
    return id;
  }

  public Promo getPromo() {
    return promo;
  }

  public Long getUserId() {
    return userId;
  }

  public Date getRedeemDate() {
    return redeemDate;
  }
}