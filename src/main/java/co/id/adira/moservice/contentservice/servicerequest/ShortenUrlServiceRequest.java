package co.id.adira.moservice.contentservice.servicerequest;

public class ShortenUrlServiceRequest {

	private String long_url;
	private String domain;

	public String getLong_url() {
		return long_url;
	}

	public void setLong_url(String long_url) {
		this.long_url = long_url;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
