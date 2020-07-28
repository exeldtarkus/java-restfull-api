package co.id.adira.moservice.contentservice.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import co.id.adira.moservice.contentservice.model.PilihanLain;

public interface PilihanLainRepository extends CrudRepository<PilihanLain, Long> {
	List<PilihanLain> findTop2ByActiveOrderByIdAsc(boolean active);
}