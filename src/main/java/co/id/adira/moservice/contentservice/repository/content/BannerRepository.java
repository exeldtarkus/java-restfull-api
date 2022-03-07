package co.id.adira.moservice.contentservice.repository.content;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import co.id.adira.moservice.contentservice.model.content.Banner;

public interface BannerRepository extends CrudRepository<Banner, Long> {
	
	List<Banner> findTop8ByActiveAndPositionNotNullAndDeletedFalseOrderByPositionAsc(boolean is_active);
}