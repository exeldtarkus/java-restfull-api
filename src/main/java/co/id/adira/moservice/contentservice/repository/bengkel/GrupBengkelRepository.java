package co.id.adira.moservice.contentservice.repository.bengkel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.id.adira.moservice.contentservice.model.bengkel.GrupBengkel;
import org.springframework.data.repository.query.Param;

public interface GrupBengkelRepository extends JpaRepository <GrupBengkel, Long> {
    
    @Query(value= "SELECT " 
            + "DISTINCT c.*"
            + "FROM mst_bengkel a " 
            + "LEFT JOIN map_group_bengkel b ON b.bengkel_id = a.bengkel_id " 
            + "LEFT JOIN mst_group_bengkel c ON c.id = b.group_bengkel_id " 
            + "WHERE a.bengkel_id IN (:bengkel_ids) "
            + "AND (c.is_active = 1 OR c.is_active IS NULL) AND (c.is_deleted = 0 OR c.is_deleted IS NULL); "
            , nativeQuery = true)
    List<GrupBengkel> findAllByBengkelIds(@Param("bengkel_ids") List<Long> bengkel_ids);
}
