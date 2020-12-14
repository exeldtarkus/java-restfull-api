package co.id.adira.moservice.contentservice.repository.content;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import co.id.adira.moservice.contentservice.model.content.Kategori;
import co.id.adira.moservice.contentservice.dto.content.EligibilityDTO;

import java.util.List;

public interface KategoriRepository extends JpaRepository<Kategori, Long> {

	@Query(value = "SELECT "
			+ "CASE WHEN COUNT(*) > 0 THEN "
			+ "NULL "
			+ "ELSE "
			+ "(SELECT a.alert "
			+ "FROM mst_category a "
			+ "JOIN map_category_promo b ON a.id = b.category_id "
			+ "WHERE b.promo_id = :promoId "
			+  "LIMIT 1) "
			+ "END AS alert, "
			+ "COUNT(*) > 0 AS isEligible "
			+ "FROM mst_category a "
			+ "JOIN map_category_promo b ON b.category_id = a.id "
			+ "JOIN map_category_user c ON c.category_id = a.id "
			+ "WHERE b.promo_id = :promoId "
			+ "AND c.email = :email "
			+ "AND a.is_deleted = 0 "
			+ "AND a.is_active = 1 "
			+ "LIMIT 1;", nativeQuery = true)
	  EligibilityDTO checkifEligible(@Param("promoId") Long promoId, @Param("email") String email);
}