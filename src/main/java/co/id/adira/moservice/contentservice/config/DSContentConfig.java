package co.id.adira.moservice.contentservice.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "co.id.adira.moservice.contentservice.repository.content", 
		entityManagerFactoryRef = "contentEntityManagerFactory", 
		transactionManagerRef = "contentTransactionManager")
public class DSContentConfig {
	
	@Bean
	@Primary
    @ConfigurationProperties(prefix = "spring.datasource.content")
    public DataSource contentDataSource() {
        return DataSourceBuilder.create().build();
    }
	
	@Primary
    @Bean(name = "contentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean contentEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(contentDataSource())
                .packages("co.id.adira.moservice.contentservice.model.content")
                .persistenceUnit("dsContent")
                .build();
    }

	@Primary
    @Bean("contentTransactionManager")
    public PlatformTransactionManager contentTransactionManager(
            final @Qualifier("contentEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    
}