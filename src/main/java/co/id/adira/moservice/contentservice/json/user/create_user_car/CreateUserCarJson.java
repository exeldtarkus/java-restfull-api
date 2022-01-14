package co.id.adira.moservice.contentservice.json.user.create_user_car;

import co.id.adira.moservice.contentservice.json.user.register.RegisterCredentialJson;
import lombok.Data;

@Data
public class CreateUserCarJson {

    private String platNo;
    private Boolean primary;
    private String year;
    private Long mobilKm;
    private CreateUserCarModelJson model;

}
