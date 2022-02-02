package co.id.adira.moservice.contentservice.json.user.register;

import lombok.Data;

@Data
public class RegisterCredentialJson {
	
	private RegisterCredentialRoleJson role;
	private Integer enabled;
	private String email;
	private String mobileNo;
	private String password;
	private Integer accountNonLocked;
	
}
