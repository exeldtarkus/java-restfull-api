package co.id.adira.moservice.contentservice.repository.bengkel;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.id.adira.moservice.contentservice.model.bengkel.Bengkel;

public interface BengkelRepository extends JpaRepository <Bengkel, Long> {
  

    @Query(value= "SELECT a.*, a.bengkel_id as bengkel_id, a.km as km FROM ( "
    + " SELECT *, "
    + " MIN( "
	+ "			   CASE "
	+ "			   WHEN (mb.bengkel_location IS NULL) THEN 999 "
	+ "			   WHEN :longitude IS NULL OR :latitude IS NULL THEN 999 "
	+ "			   ELSE (ROUND((ST_Distance_Sphere(mb.bengkel_location, POINT(:longitude, :latitude))) / 1000, 2)) "
	+ "			   END "
	+ "		   ) AS km "
    + "FROM mst_bengkel mb "
    + "WHERE  mb.bengkel_id IN (:bengkelIds) "
    + "GROUP BY mb.bengkel_id "
    + ") AS a "
    + " ORDER BY :#{#pageable}"
    , nativeQuery = true)
    List<Bengkel>findAllBengkelsByBengkelId(@Param("bengkelIds") List<Long> bengkelIds,
        @Param("pageable") Pageable pageable, 
        @Param("latitude") Double latitude, 
        @Param("longitude") Double longitude);
}
