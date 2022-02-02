package co.id.adira.moservice.contentservice.json.user.get_user_car;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserCarDataResponseJson {

	private Long id;
	private String platNo;

}
