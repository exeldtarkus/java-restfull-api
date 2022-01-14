package co.id.adira.moservice.contentservice.json.user.create_user_car;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserCarDataResponseJson {

	private Long id;

}
