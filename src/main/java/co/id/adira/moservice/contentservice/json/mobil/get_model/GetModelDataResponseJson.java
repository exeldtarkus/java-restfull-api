package co.id.adira.moservice.contentservice.json.mobil.get_model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetModelDataResponseJson {

	private Long modelId;
	private String modelName;

}
