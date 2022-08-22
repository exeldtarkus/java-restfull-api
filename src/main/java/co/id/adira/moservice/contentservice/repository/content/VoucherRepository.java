package co.id.adira.moservice.contentservice.repository.content;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.id.adira.moservice.contentservice.model.content.Promo;
import co.id.adira.moservice.contentservice.model.content.QRCode;
import co.id.adira.moservice.contentservice.model.content.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	
	@Query(value = 
        " SELECT *, b.bengkel_name, b.bengkel_address FROM content.tr_promo_user v,        "
			+ " bengkel.mst_bengkel b, content.mst_promo c, bengkel.mst_contact d                "
			+ " WHERE v.user_id = :userId AND v.use_date IS NULL                                 "
			+ " AND v.bengkel_id = b.bengkel_id                                                  "
			+ " AND v.bengkel_id = d.bengkel_id                                                  "
			+ " AND v.promo_id = c.id                                                            "
			+ " AND (:utm is null or v.utm IN (:utmIn) )                                         "
			+ " AND (:utmNotIn is null or v.utm NOT IN (:utmNotIn) )                             "
			+ " AND CASE                                                                         "
			+ "      WHEN :utm = 'adiraku-utm' THEN v.promo_id IS NOT NULL                       "
			+ "      ELSE c.available_until > :currentDate AND c.available_from < :currentDate   "
			+ "     END                                                                          "
			+ " ORDER BY :#{#pageable}                                                           "
  , nativeQuery = true)
	List<Voucher> findAllUnusedVoucherAndMore(
			@Param("userId") Long userId,
			@Param("currentDate") Date currentDate,
			@Param("utm") String utm,
			@Param("utmIn") List<String> utmIn,
			@Param("utmNotIn") List<String> utmNotIn,
			@Param("pageable") Pageable pageable);

	@Modifying
	@Query(value =
			"insert into "
					+ "tr_promo_user "
					+ "(bengkel_id, "
					+ "booking_id, "
					+ "car_id, "
					+ "created, "
					+ "promo_id, "
					+ "qrcode_id, "
					+ "redeem_date, "
					+ "updated, "
					+ "use_date, "
					+ "user_id, "
					+ "utm, "
					+ "transaction_status_id, "
					+ "payment_status, "
					+ "payment_id, "
					+ "payment_expired_at) "
					+ "values "
					+ "(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15) "
			, nativeQuery = true)
	void insertVoucher(
			Long bengkelId,
			Long bookingId,
			Long carId,
			Date created,
			Promo promo,
			QRCode qr,
			Date redeemDate,
			Date updated,
			Date useDate,
			Long userId,
			String Utm,
			Long transactionStatusId,
			String paymentStatus,
			String paymentId,
			Date paymentExpiredAt
	);
	
}