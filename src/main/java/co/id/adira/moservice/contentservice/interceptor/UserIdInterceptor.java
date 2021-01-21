package co.id.adira.moservice.contentservice.interceptor;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

@Component
public class UserIdInterceptor {
	
	@Autowired
	private TokenStore tokenStore;
	
	public boolean isValidUserId(String id) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object details = authentication.getDetails();        
		
		if (details instanceof OAuth2AuthenticationDetails) {
			
		    OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) details;
		    OAuth2AccessToken accessToken = tokenStore.readAccessToken(oAuth2AuthenticationDetails.getTokenValue());
		    
		    Map<String, Object> additionalInfo = accessToken.getAdditionalInformation();
		    Integer userId = (Integer) additionalInfo.get("user_id");
		    if (!userId.equals(Integer.parseInt(id.trim()))) {
		    	return false;
		    }
		    
		}
		
		return true;
	}

}
