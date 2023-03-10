package co.id.adira.moservice.contentservice.model.content;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "tr_promo_user")
public class Voucher implements Serializable {
	
	private static final long serialVersionUID = -7517599541689056088L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "promo_id")
	private Promo promo;
	
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
	
	@ManyToOne
	@JoinColumn(name = "qrcode_id")
	private QRCode qr;
	
	@Column
	private String bengkel_name;
	
	@Column
	private String bengkel_address;
	
	@Column(name ="bengkel_phone")
	private String bengkel_phone;
	
	@Column(name = "utm")
	private String utm;

	@Column(name = "transaction_status_id")
	private Long transactionStatusId;

	@Column(name = "payment_status")
	private String paymentStatus;

	@Column(name = "payment_id")
	private String paymentId;

	@Column(name = "payment_expired_at")
	private Date paymentExpiredAt;

	@Transient
	private String statusVoucherPayment;

	@Transient
	private String cityName;

	public String getUtm() {
		return utm;
	}

	public void setUtm(String utm) {
		this.utm = utm;
	}

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

	public Promo getPromo() {
		return promo;
	}

	public void setPromo(Promo promo) {
		this.promo = promo;
	}

	public QRCode getQr() {
		return qr;
	}

	public void setQr(QRCode qr) {
		this.qr = qr;
	}

	public String getBengkel_name() {
		return bengkel_name;
	}

	public void setBengkel_name(String bengkel_name) {
		this.bengkel_name = bengkel_name;
	}

	public String getBengkel_address() {
		return bengkel_address;
	}

	public void setBengkel_address(String bengkel_address) {
		this.bengkel_address = bengkel_address;
	}

	public Long getTransactionStatusId(){
		return transactionStatusId;
	}

	public void setTransactionStatusId(Long transactionStatusId){
		this.transactionStatusId = transactionStatusId;
	}
	
	public String getBengkel_phone() {
		return bengkel_phone;
	}

	public void setBengkel_phone(String bengkel_phone) {
		this.bengkel_phone = bengkel_phone;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return "Voucher [id=" + id + ", promo=" + promo + ", userId=" + userId + ", bengkelId=" + bengkelId
				+ ", bookingId=" + bookingId + ", carId=" + carId + ", redeemDate=" + redeemDate + ", useDate="
				+ useDate + ", created=" + created + ", updated=" + updated + ", qr=" + qr + ", bengkel_name="
				+ bengkel_name + ", bengkel_address=" + bengkel_address + ", cityName=" + cityName + "]";
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Date getPaymentExpiredAt() {
		return paymentExpiredAt;
	}

	public void setPaymentExpiredAt(Date paymentExpiredAt) {
		this.paymentExpiredAt = paymentExpiredAt;
	}

	public String getStatusVoucherPayment() {
		return statusVoucherPayment;
	}

	public void setStatusVoucherPayment(String statusVoucherPayment) {
		this.statusVoucherPayment = statusVoucherPayment;
	}
}
