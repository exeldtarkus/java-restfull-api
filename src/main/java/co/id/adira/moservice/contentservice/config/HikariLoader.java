package co.id.adira.moservice.contentservice.config;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class HikariLoader {

	private final HikariDataSource hikariDataSource;

	@Autowired
	public HikariLoader(HikariDataSource hikariDataSource) {
		this.hikariDataSource = hikariDataSource;
	}

	@Autowired
	public void run(ApplicationArguments args) throws SQLException {
		hikariDataSource.getConnection();
	}

}
