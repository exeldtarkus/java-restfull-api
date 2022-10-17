package co.id.adira.moservice.contentservice.model.content;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tr_promo_user")
public class VoucherPlain implements Serializable {
	
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

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "adiraku_account_id")
	private String adirakuAccountId;

	@ManyToOne
	@JoinColumn(name = "qrcode_id")
	private QRCode qr;
}
