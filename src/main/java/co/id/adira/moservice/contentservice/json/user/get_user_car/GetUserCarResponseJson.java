package co.id.adira.moservice.contentservice.json.user.get_user_car;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
public class GetUserCarResponseJson {
	
	private List<GetUserCarDataResponseJson> data;
	private String message;
	private String timestamp;
	private DateTime status;
	private Boolean isSuccess;
	
}
