package co.id.adira.moservice.contentservice.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "co.id.adira.moservice.contentservice.repository.bengkel", entityManagerFactoryRef = "bengkelEntityManagerFactory", transactionManagerRef = "bengkelTransactionManager")
public class DSBengkelConfig {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.bengkel")
  public DataSource bengkelDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "bengkelEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean bengkelEntityManagerFactory(EntityManagerFactoryBuilder builder) {
    return builder.dataSource(bengkelDataSource()).packages("co.id.adira.moservice.contentservice.model.bengkel")
        .persistenceUnit("dsBengkel").build();
  }

  @Bean("bengkelTransactionManager")
  public PlatformTransactionManager bengkelTransactionManager(
      final @Qualifier("bengkelEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

}