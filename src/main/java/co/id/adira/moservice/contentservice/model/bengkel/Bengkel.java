package co.id.adira.moservice.contentservice.model.bengkel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "mst_bengkel")
public class Bengkel implements Serializable {

	private static final long serialVersionUID = 4L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bengkel_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "bengkel_name")
	private String name;

	@Column(name = "bengkel_address")
	private String address;

	@Column(name = "bengkel_location")
	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(contentUsing = GeometryDeserializer.class)
	private Point location;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id", referencedColumnName = "city_id", insertable = false, updatable = false)
	@JsonManagedReference
	private City city;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date created;

	@Column(name = "created_by")
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated")
	private Date updated;

	@Column(name = "updated_by")
	private Integer updatedBy;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public Point getLocation() {
		return location;
	}

	public City getCity() {
		return city;
	}

}
