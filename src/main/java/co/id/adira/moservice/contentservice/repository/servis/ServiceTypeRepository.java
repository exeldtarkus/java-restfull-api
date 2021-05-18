package co.id.adira.moservice.contentservice.repository.servis;

import org.springframework.data.jpa.repository.JpaRepository;
import co.id.adira.moservice.contentservice.model.servis.ServiceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long>  {
    @Query(value = "select a.* from servis.ref_tipe_servis a \n" +
            "	where (:search is null or a.tipe_servis_name LIKE %:search%)", nativeQuery = true)
    List<ServiceType> getServiceTypeByQuery(@Param("search") String search);
}
