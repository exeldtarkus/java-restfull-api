package co.id.adira.moservice.contentservice.json.auth.get_token;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class GetTokenByPhoneNumberResponseJson {

	private GetTokenByPhoneNumberDataResponseJson data;
	private String message;
	private String timestamp;
	private DateTime status;
	private Boolean isSuccess;
	
}
