package co.id.adira.moservice.contentservice.model.servis;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ref_tipe_servis", schema = "servis")
public class ServiceType implements Serializable {

	private static final long serialVersionUID = -2033287648482957062L;

	@Id
	@Column(name = "tipe_servis_id")
	private Long id;

	@Column(name = "tipe_servis_name")
	private String name;

	@Column(name = "icon_path")
	private String logoUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

}
