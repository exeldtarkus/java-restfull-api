package co.id.adira.moservice.contentservice.json.user.create_user_car;

import lombok.Data;

@Data
public class CreateUserCarModelJson {
	
	private Long modelId;
	private Long modelName;
	private Boolean isDeleted;
	private CreateUserCarModelBrandJson brand;

}
