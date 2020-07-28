package co.id.adira.moservice.contentservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import co.id.adira.moservice.contentservice.model.PilihanLain;
import co.id.adira.moservice.contentservice.repository.PilihanLainRepository;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@RunWith(SpringRunner.class)
@DataJpaTest
public class PilihanLainRepositoryTest {
	
	@Autowired
	private PilihanLainRepository pilihanLainRepository;
	
	@Test
    public void savePilihanLainTest() throws Exception {
		PilihanLain pilihanLain = new PilihanLain();
		pilihanLain.setName("TEST");
		pilihanLain.setTitle("TEST");
		pilihanLainRepository.save(pilihanLain);
    }

}
