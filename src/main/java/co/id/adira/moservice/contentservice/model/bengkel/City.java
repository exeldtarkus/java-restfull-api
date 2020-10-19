package co.id.adira.moservice.contentservice.model.bengkel;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ref_city")
public class City implements Serializable {

	private static final long serialVersionUID = -9111671699559398531L;

	@Id
	@Column(name = "city_id")
	private Long id;

	@Column(name = "city_name")
	private String name;

	@Column(name = "sort_1")
	private String sort;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "provinsi_id", referencedColumnName = "provinsi_id", insertable = false, updatable = false)
	@JsonBackReference
	private Province province;

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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

}
