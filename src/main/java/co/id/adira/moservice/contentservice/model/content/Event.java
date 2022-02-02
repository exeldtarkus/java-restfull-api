package co.id.adira.moservice.contentservice.model.content;

import co.id.adira.moservice.model.BaseEntity;
import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name="mst_event")
public class Event implements Serializable {

	private static final long serialVersionUID = 2142969324276815045L;

	@Id
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "created")
	private Date created;

	@Column(name = "updated")
	private Date updated;

}
