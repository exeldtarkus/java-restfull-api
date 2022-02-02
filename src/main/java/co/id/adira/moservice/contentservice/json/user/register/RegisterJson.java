package co.id.adira.moservice.contentservice.json.user.register;

import lombok.Data;

@Data
public class RegisterJson {
	
	private String firstName;
	private String lastName;
	private Integer emailVerified;
	private String provider;
	private RegisterCredentialJson credential;
	
}
