package co.id.adira.moservice.contentservice.model.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import co.id.adira.moservice.model.BaseEntity;

@Entity
@Table(name="mst_bank")
public class Bank extends BaseEntity {

	private static final long serialVersionUID = 2209524556206322824L;

	@Column(name = "name")
	private String name;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "image_path_mobile")
	private String imagePathMobile;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "is_active")
	private boolean active;
	
	@Column(name = "position")
	private Integer position;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImagePathMobile() {
		return imagePathMobile;
	}

	public void setImagePathMobile(String imagePathMobile) {
		this.imagePathMobile = imagePathMobile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
}
