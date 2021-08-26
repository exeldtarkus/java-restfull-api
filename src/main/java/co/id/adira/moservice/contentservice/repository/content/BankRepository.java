/**
 * 
 */
package co.id.adira.moservice.contentservice.repository.content;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import co.id.adira.moservice.contentservice.model.content.Bank;

/**
 * @author fatchurrachman
 *
 */
public interface BankRepository extends CrudRepository<Bank, Long> {
	
	@Cacheable(cacheNames = "BankCache", key = "#root.targetClass.simpleName+'.'+#root.methodName")
	List<Bank> findTop20ByActiveOrderByPositionAsc(boolean active);

}
