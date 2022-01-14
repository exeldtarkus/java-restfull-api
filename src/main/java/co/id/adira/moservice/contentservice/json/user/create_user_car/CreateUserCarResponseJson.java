package co.id.adira.moservice.contentservice.json.user.create_user_car;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserCarResponseJson {

	private String message;
	private String timestamp;
	private DateTime status;
	private Boolean isSuccess;
	private CreateUserCarDataResponseJson data;

}
