package co.id.adira.moservice.contentservice.repository.content;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.id.adira.moservice.contentservice.model.content.Promo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PromoRepository extends JpaRepository<Promo, Long> {
	@Query(value = "SELECT *, GROUP_CONCAT(e.city_name) as cities " + "FROM mst_promo p WHERE p.zone_id = :zoneId "
			+ "JOIN db_content.map_promo_area d ON p.id = d.promo_id "
			+ "JOIN db_bengkel.ref_city e ON d.city_id = e.city_id " + "AND p.is_active = true AND p.is_deleted = false "
			+ "AND p.available_until > :currentDate " + "AND p.available_from < :currentDate "
			+ "ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findAllByZoneIdAndMore(@Param("zoneId") Long zoneId, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);

	@Query(value = "SELECT *, GROUP_CONCAT(e.city_name) as cities FROM db_content.mst_promo a "
			+ "JOIN db_content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN db_servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN db_content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN db_bengkel.ref_city e ON d.city_id = e.city_id " + "WHERE a.name LIKE %:q% "
			+ "AND c.tipe_servis_id IN :serviceIdsList " + "AND a.special IN :promoTypeList " + "AND a.is_active = true "
			+ "AND a.is_deleted = false " + "AND a.available_until > :currentDate "
			+ "AND a.available_from < :currentDate GROUP BY a.id ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findAllAndMore(@Param("q") String q, @Param("serviceIdsList") List<Long> serviceIdsList,
			@Param("promoTypeList") List<Integer> promoTypeList, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);

	@Query(value = "SELECT *, GROUP_CONCAT(e.city_name) as cities FROM db_content.mst_promo a "
			+ "JOIN db_content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN db_servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN db_content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN db_bengkel.ref_city e ON d.city_id = e.city_id " + "WHERE a.id = :id " + "AND a.is_active = true "
			+ "AND a.is_deleted = false " + "AND a.available_until > :currentDate "
			+ "AND a.available_from < :currentDate GROUP BY a.id", nativeQuery = true)
	Optional<Promo> findByIdAndMore(@Param("id") Long id, @Param("currentDate") Date currentDate);

	@Query(value = "SELECT *, GROUP_CONCAT(e.city_name) as cities  FROM db_content.mst_promo a "
			+ "JOIN db_content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN db_servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN db_content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN db_bengkel.ref_city e ON d.city_id = e.city_id "
			+ "WHERE c.tipe_servis_id = :servisId AND a.is_active = true AND a.is_deleted = false AND a.available_until > :currentDate "
			+ "AND a.available_from < :currentDate GROUP BY a.id ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findByServisIdAndMore(@Param("servisId") Long servisId, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);
}