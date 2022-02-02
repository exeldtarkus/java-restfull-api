package co.id.adira.moservice.contentservice.json.auth.get_token;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GetTokenByPhoneNumberDataResponseJson {

	private String access_token;
	private String token_type;
	private String refresh_token;
	private String expires_in;
	private String scope;
	private Long cred_id;
	private Long user_id;
	private ArrayList<Long> car_id;
	private String jti;

}
