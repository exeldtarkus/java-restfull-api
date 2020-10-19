package co.id.adira.moservice.contentservice.model.bengkel;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ref_provinsi")
public class Province implements Serializable {

	private static final long serialVersionUID = 9102167042940624947L;

	@Id
	@Column(name = "provinsi_id")
	private Long id;

	@Column(name = "provinsi_name")
	private String name;

	@OneToMany(targetEntity = City.class)
	@JoinColumn(name = "provinsi_id", referencedColumnName = "provinsi_id", insertable = false, updatable = false)
	@JsonManagedReference
	private List<City> cities;

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

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

}