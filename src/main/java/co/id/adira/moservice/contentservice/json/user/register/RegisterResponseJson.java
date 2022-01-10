package co.id.adira.moservice.contentservice.json.user.register;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class RegisterResponseJson {
	
	private Long data;
	private String message;
	private String timestamp;
	private DateTime status;
	private Boolean isSuccess;
	
}
