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
	
	@Query(value = "SELECT *, b.bengkel_name, b.bengkel_address FROM db_content.tr_promo_user v, db_bengkel.mst_bengkel b "
			+ "WHERE v.user_id = :userId AND v.use_date IS NULL "
			+ "AND v.bengkel_id = b.bengkel_id "
			+ "ORDER BY :#{#pageable}", nativeQuery = true)
	List<Voucher> findAllUnusedVoucherAndMore(@Param("userId") Long userId, @Param("pageable") Pageable pageable);
	
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
    		+ "user_id, bengkel_name) "
    		+ "values "
    		+ "(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10) "  
    		, nativeQuery = true)
	void insertVoucher(Long bengkelId, 
			Long bookingId, Long carId, Date created, Promo promo, 
			QRCode qr, Date redeemDate, Date updated, Date useDate, Long userId);
}