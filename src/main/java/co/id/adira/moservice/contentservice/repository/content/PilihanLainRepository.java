package co.id.adira.moservice.contentservice.repository.content;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import co.id.adira.moservice.contentservice.model.content.PilihanLain;

public interface PilihanLainRepository extends CrudRepository<PilihanLain, Long> {
	
	@Cacheable(cacheNames = "PilihanLainCache", key = "#root.targetClass.simpleName+'.'+#root.methodName")
	List<PilihanLain> findTop4ByActiveOrderByIdAsc(boolean active);
}