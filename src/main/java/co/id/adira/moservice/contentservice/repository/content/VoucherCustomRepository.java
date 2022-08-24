package co.id.adira.moservice.contentservice.repository.content;

import co.id.adira.moservice.contentservice.model.content.Promo;
import co.id.adira.moservice.contentservice.model.content.QRCode;
import co.id.adira.moservice.contentservice.model.content.Voucher;
import co.id.adira.moservice.contentservice.model.content.VoucherPlain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface VoucherCustomRepository extends JpaRepository<VoucherPlain, Long> {

    @Modifying
    @Query(value =
            "UPDATE tr_promo_user t SET "
                    + "bengkel_id = ?1, "
                    + "booking_id = ?2, "
                    + "car_id = ?3, "
                    + "created = ?4, "
                    + "promo_id = ?5, "
                    + "qrcode_id = ?6, "
                    + "redeem_date = ?7, "
                    + "updated = ?8, "
                    + "use_date = ?9, "
                    + "user_id = ?10, "
                    + "utm = ?11, "
                    + "transaction_status_id = ?12, "
                    + "payment_status = ?13, "
                    + "payment_id = ?14, "
                    + "payment_expired_at = ?15 "
                    + "WHERE id = ?16"
            , nativeQuery = true)
    void updateVoucher(
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
            Date paymentExpiredAt,
            Long id
    );
    Optional<VoucherPlain> findByIdAndUserId(Long id, Long userId);

}