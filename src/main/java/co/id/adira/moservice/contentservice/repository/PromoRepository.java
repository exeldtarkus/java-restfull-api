package co.id.adira.moservice.contentservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.id.adira.moservice.contentservice.model.Promo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PromoRepository extends JpaRepository<Promo, Long> {
	@Query(value = "SELECT * FROM m_promo p WHERE p.zone_id = :zoneId AND p.is_active = true AND p.is_deleted = false AND p.available_until > :currentDate AND p.available_from < :currentDate ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findAllByZoneIdAndMore(@Param("zoneId") Long zoneId, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);

	@Query(value = "SELECT * FROM m_promo p WHERE p.is_active = true AND p.is_deleted = false AND p.available_until > :currentDate AND p.available_from < :currentDate ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findAllAndMore(@Param("currentDate") Date currentDate, @Param("pageable") Pageable pageable);

	@Query(value = "SELECT * FROM m_promo p WHERE p.id = :id AND p.is_active = true AND p.is_deleted = false AND p.available_until > :currentDate AND p.available_from < :currentDate ", nativeQuery = true)
	Optional<Promo> findByIdAndMore(@Param("id") Long id, @Param("currentDate") Date currentDate);
}