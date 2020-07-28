/**
 * 
 */
package co.id.adira.moservice.contentservice.config;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author fatchurrachman
 *
 */
@EnableResourceServer
@Configuration
public class ResourcesServerConfig extends ResourceServerConfigurerAdapter {

	@Value("classpath:public-key.txt")
	private Resource pem;
	@Value("${oauth2.jwt.resource-id}")
	private String resourceId;

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/promo").permitAll()
			.antMatchers(HttpMethod.GET, "/api/promo/**").permitAll()
			.antMatchers(HttpMethod.GET, "/api/promo/**").permitAll()
			.antMatchers("/api/**").authenticated();
		// @formatter:on
	}

	@Override
	public void configure(final ResourceServerSecurityConfigurer security) {
		security.tokenServices(tokenServices()).resourceId(resourceId);
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@SuppressWarnings("deprecation")
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		String publicKey = null;
		try {
			publicKey = IOUtils.toString(pem.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		converter.setVerifierKey(publicKey);
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}

}
