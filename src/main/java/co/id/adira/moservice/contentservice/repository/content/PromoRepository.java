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
	
	@Query(value = "SELECT * FROM mst_promo p "
			+ "WHERE p.zone_id IN (0,1,5,6) AND p.is_active = true AND p.is_deleted = false "
			+ "AND p.available_until >= DATE(:currentDate) " + "AND p.available_from <= DATE(:currentDate) "
			+ "GROUP BY p.id ORDER BY p.id desc "
			+ "limit 8", nativeQuery = true)
	List<Promo> findAllByZoneIdAndMore(@Param("currentDate") Date currentDate);
	
	@Query(value = "SELECT *, GROUP_CONCAT(DISTINCT e.city_name) as cities, null AS bengkelNames " + "FROM mst_promo p "
			+ "JOIN content.map_promo_area d ON p.id = d.promo_id "
			+ "JOIN bengkel.ref_city e ON d.city_id = e.city_id "
			+ "WHERE p.zone_id = :zoneId AND p.is_active = true AND p.is_deleted = false "
			+ "AND p.available_until >= DATE(:currentDate) " + "AND p.available_from <= DATE(:currentDate) "
			+ "GROUP BY p.id ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findAllByZoneIdAndMore(@Param("zoneId") Long zoneId, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);

	@Query(value = "SELECT a.id, "
			+ "a.name, "
			+ "a.title, "
			+ "a.desc, "
			+ "a.tnc, "
			+ "a.url_path, "
			+ "a.image_path, "
			+ "a.image_path_mobile, "
			+ "a.available_until, "
			+ "a.available_from, "
			+ "a.is_active, "
			+ "a.is_deleted, "
			+ "a.created, "
			+ "a.updated, "
			+ "a.counter_redeemed, "
			+ "a.counter_used, "
			+ "a.total, "
			+ "a.limit, "
			+ "a.is_national, "
			+ "a.tnc_id, "
			+ "a.zone_id, "
			+ "a.target_id, "
			+ "a.special, "
			+ "a.type, "
			+ "a.original_price, "
			+ "a.disc_percentage, "
			+ "a.service_fee, "
			+ "a.disc_amount, "
			+ "f.id, "
			+ "f.promo_id, "
			+ "f.bengkel_id, "
			+ "f.group_bengkel_id, "
			+ "g.city_id "
			+ "FROM content.mst_promo a "
			+ "JOIN content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN bengkel.ref_city e ON d.city_id = e.city_id "
			+ "JOIN content.map_promo_bengkel f ON a.id = f.promo_id "
			+ "JOIN ( "
			+ "	SELECT mb.bengkel_id bengkel_id, "
			+ "		   mpb.promo_id promo_id, "
			+ "		   mb.city_id, "
			+ "		   MIN( "
			+ "			   CASE "
			+ "			   WHEN (mb.bengkel_location IS NULL) THEN 999 "
			+ "			   WHEN :longitude IS NULL OR :latitude IS NULL THEN 999 "
			+ "			   ELSE (ROUND((ST_Distance_Sphere(mb.bengkel_location, POINT(:longitude, :latitude))) / 1000, 2)) "
			+ "			   END "
			+ "		   ) AS km, "
			+ "		   count(mb.bengkel_id) AS count_bengkel "
			+ "		FROM content.map_promo_bengkel mpb "
			+ "		JOIN bengkel.mst_bengkel mb ON mb.bengkel_id = mpb.bengkel_id "
			+ "		GROUP BY mpb.promo_id"
			+ ") AS g ON a.id = g.promo_id " 
			+ "WHERE ("
			+ "		a.title LIKE %:q% OR "
			+ "		e.city_name LIKE %:q% OR"
			+ "		c.tipe_servis_id IN (:searchServiceTypeIds)"
			+ ") "
			+ "AND (:serviceIdsList is null OR (c.tipe_servis_id IN :service_type)) " 
			+ "AND a.special IN :promoTypeList "
			+ "AND (:cid is null or e.city_id = :cid) "
			+ "AND a.is_active = TRUE " 
			+ "AND a.is_deleted = FALSE " 
			+ "AND a.available_until >= DATE(:currentDate) "
			+ "AND a.available_from <= DATE(:currentDate) GROUP BY a.id ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findAllAndMore(
			@Param("q") String q,
			@Param("service_type") List<Long> service_type,
			@Param("promoTypeList") List<Integer> promoTypeList,
			@Param("currentDate") Date currentDate,
			@Param("serviceIdsList") String serviceIdsList,
			@Param("searchServiceTypeIds") List<Long> searchServiceTypeIds,
			@Param("cid") Long cid,
			@Param("pageable") Pageable pageable,
			@Param("latitude") Double latitude,
			@Param("longitude") Double longitude
	);

	@Query(value = "SELECT * "
			+ "FROM content.mst_promo a "
			+ "JOIN content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN bengkel.ref_city e ON d.city_id = e.city_id " + "WHERE a.id = :id " + "AND a.is_active = true "
			+ "AND a.is_deleted = false " + "AND a.available_until >= DATE(:currentDate) "
			+ "AND a.available_from <= DATE(:currentDate) GROUP BY a.id", nativeQuery = true)
	Optional<Promo> findByIdAndMore(@Param("id") Long id, @Param("currentDate") Date currentDate);

	@Query(value = "SELECT * "
			+ "FROM content.mst_promo a "
			+ "JOIN content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN bengkel.ref_city e ON d.city_id = e.city_id "
			+ "WHERE c.tipe_servis_id = :servisId AND a.is_active = true AND a.is_deleted = false AND a.available_until >= DATE(:currentDate) "
			+ "AND a.available_from <= DATE(:currentDate) GROUP BY a.id ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findByServisIdAndMore(@Param("servisId") Long servisId, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);

	@Query(value = "SELECT * "
			+ "FROM content.mst_promo a "
			+ "JOIN content.map_promo_service b on a.id = b.promo_id "
			+ "JOIN servis.ref_tipe_servis c on b.service_umum_id = c.tipe_servis_id "
			+ "JOIN content.map_promo_area d ON a.id = d.promo_id "
			+ "JOIN bengkel.ref_city e ON d.city_id = e.city_id "
			+ "JOIN content.map_promo_bengkel f ON a.id = f.promo_id "
			+ "JOIN bengkel.mst_bengkel g ON f.bengkel_id = g.bengkel_id " + "WHERE g.bengkel_id = :bengkelId "
			+ "AND a.is_active = true "
			+ "AND a.available_until >= DATE(:currentDate) "
			+ "AND a.available_from <= DATE(:currentDate) GROUP BY a.id ORDER BY :#{#pageable}", nativeQuery = true)
	List<Promo> findByBengkelIdAndMore(@Param("bengkelId") Long bengkelId, @Param("currentDate") Date currentDate,
			@Param("pageable") Pageable pageable);
}