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
import javax.persistence.Transient;

@Entity
@Table(name = "mst_qrcode")
public class QRCode implements Serializable {
	
	private static final long serialVersionUID = -7577099200761310993L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "qrcode_path")
	private String qrcodePath;

	@Column(name = "qrcode_path_2")
	private String qrcodePath2;

	@Column(name = "cust_acction")
	private String custAcction; // 0 = promo , 1 = booking
	
	@Column(name = "user_id")
	private Long userId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "data")
	private String data;
	
	@Column(name = "booking_id")
	private Long bookingId;
	
	@Column(name = "promo_id")
	private Long promoId;
	
	@Transient
	private Long bengkelId;

	@Transient
	private String base64QRCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQrcodePath() {
		return qrcodePath;
	}

	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}

	public String getCustAcction() {
		return custAcction;
	}

	public void setCustAcction(String custAcction) {
		this.custAcction = custAcction;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getBase64QRCode() {
		return base64QRCode;
	}

	public void setBase64QRCode(String base64qrCode) {
		base64QRCode = base64qrCode;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Long getPromoId() {
		return promoId;
	}

	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}

	public Long getBengkelId() {
		return bengkelId;
	}

	public void setBengkelId(Long bengkelId) {
		this.bengkelId = bengkelId;
	}

	public String getQrcodePath2() {
		return qrcodePath2;
	}

	public void setQrcodePath2(String qrcodePath2) {
		this.qrcodePath2 = qrcodePath2;
	}
}
