package co.id.adira.moservice.contentservice.model.content;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "tr_promo_user")
public class Voucher implements Serializable {
	
	private static final long serialVersionUID = -7517599541689056088L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "promo_id")
	private Long promoId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "bengkel_id")
	private Long bengkelId;
	
	@Column(name = "booking_id")
	private Long bookingId;
	
	@Column(name = "car_id")
	private Long carId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "redeem_date")
	private Date redeemDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "use_date")
	private Date useDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated")
	private Date updated;

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public Date getRedeemDate() {
		return redeemDate;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setRedeemDate(Date redeemDate) {
		this.redeemDate = redeemDate;
	}

	public Long getBengkelId() {
		return bengkelId;
	}

	public void setBengkelId(Long bengkelId) {
		this.bengkelId = bengkelId;
	}

	public Long getPromoId() {
		return promoId;
	}

	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}
	
}
