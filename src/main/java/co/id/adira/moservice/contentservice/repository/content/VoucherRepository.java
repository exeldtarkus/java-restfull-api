package co.id.adira.moservice.contentservice.repository.content;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.id.adira.moservice.contentservice.model.content.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	@Query(value = "SELECT * FROM tr_promo_user v WHERE v.user_id = :userId AND v.use_date IS NULL AND v.bengkel_id IS NULL ORDER BY :#{#pageable}", nativeQuery = true)
	List<Voucher> findAllUnusedVoucherAndMore(@Param("userId") Long userId, @Param("pageable") Pageable pageable);
}