package co.id.adira.moservice.contentservice.repository.bengkel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.id.adira.moservice.contentservice.model.bengkel.GrupBengkel;
import io.lettuce.core.dynamic.annotation.Param;

public interface GrupBengkelRepository extends JpaRepository <GrupBengkel, Long> {
    

    @Query(value= "SELECT " 
            + "DISTINCT * " 
            + "FROM mst_group_bengkel mst " 
            + "JOIN map_group_bengkel map ON map.group_bengkel_id = mst.id " 
            + "WHERE map.bengkel_id IN (:bengkel_ids) "
            + "GROUP BY mst.id;", nativeQuery = true)
    List<GrupBengkel> findAllByBengkelIds(@Param("bengkel_ids") List<Long> bengkel_ids);
}
