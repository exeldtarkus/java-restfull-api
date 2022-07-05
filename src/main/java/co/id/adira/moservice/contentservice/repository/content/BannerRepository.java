package co.id.adira.moservice.contentservice.repository.content;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import co.id.adira.moservice.contentservice.model.content.Banner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BannerRepository extends CrudRepository<Banner, Long> {
  @Query(value = 
        " SELECT * FROM mst_banner a "
			+ " WHERE  a.using_on = 1 AND a.is_active = 1 AND a.is_deleted = 0 "
			+ " GROUP  BY a.id ORDER BY a.position Asc "
			+ " LIMIT  8"
    , nativeQuery = true)
	List<Banner> findBanner();
}