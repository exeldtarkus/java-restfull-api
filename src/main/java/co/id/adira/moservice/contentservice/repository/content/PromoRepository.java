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
	@Query(value = "SELECT *, GROUP_CONCAT(DISTINCT e.city_name) as cities, null AS bengkelNames " + "FROM mst_promo p "
			+ "JOIN content.map_promo_area d ON p.id = d.promo_id "
			+ "JOIN bengkel.ref_city e ON d.city_id = e.city_id "
			+ "WHERE p.zone_id = :zoneId AND p.is_active = true AND p.is_deleted = false "
			+ "AND p.available_until > :currentDate " + "AND p.available_from < :currentDate "
			+ "GROUP BY p.id ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findAllByZoneIdAndMore(@Param("zoneId") Long zoneId, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);

	@Query(value = "SELECT * FROM content.mst_promo a "
			+ "JOIN content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN bengkel.ref_city e ON d.city_id = e.city_id "
			+ "JOIN content.map_promo_bengkel f ON a.id = f.promo_id "
			+ "JOIN bengkel.mst_bengkel g ON f.bengkel_id = g.bengkel_id " + "WHERE a.name LIKE %:q% "
			+ "AND c.tipe_servis_id IN :serviceIdsList " + "AND a.special IN :promoTypeList "
			+ "AND a.is_active = true " + "AND a.is_deleted = false " + "AND a.available_until > :currentDate "
			+ "AND a.available_from < :currentDate GROUP BY a.id ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findAllAndMore(@Param("q") String q, @Param("serviceIdsList") List<Long> serviceIdsList,
			@Param("promoTypeList") List<Integer> promoTypeList, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);

	@Query(value = "SELECT * FROM content.mst_promo a "
			+ "JOIN content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN bengkel.ref_city e ON d.city_id = e.city_id " + "WHERE a.id = :id " + "AND a.is_active = true "
			+ "AND a.is_deleted = false " + "AND a.available_until > :currentDate "
			+ "AND a.available_from < :currentDate GROUP BY a.id", nativeQuery = true)
	Optional<Promo> findByIdAndMore(@Param("id") Long id, @Param("currentDate") Date currentDate);

	@Query(value = "SELECT * FROM content.mst_promo a "
			+ "JOIN content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN bengkel.ref_city e ON d.city_id = e.city_id "
			+ "WHERE c.tipe_servis_id = :servisId AND a.is_active = true AND a.is_deleted = false AND a.available_until > :currentDate "
			+ "AND a.available_from < :currentDate GROUP BY a.id ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findByServisIdAndMore(@Param("servisId") Long servisId, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);

	@Query(value = "SELECT * FROM content.mst_promo a "
			+ "JOIN content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN bengkel.ref_city e ON d.city_id = e.city_id "
			+ "JOIN content.map_promo_bengkel f ON a.id = f.promo_id "
			+ "JOIN bengkel.mst_bengkel g ON f.bengkel_id = g.bengkel_id " + "WHERE g.bengkel_id = :bengkelId "
			+ "AND a.is_active = true "
			+ "AND a.available_until > :currentDate "
			+ "AND a.available_from < :currentDate GROUP BY a.id ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findByBengkelIdAndMore(@Param("bengkelId") Long bengkelId, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);
}