package co.id.adira.moservice.contentservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import co.id.adira.moservice.model.BaseEntity;

@Entity
@Table(name="m_pilihan_lain")
public class PilihanLain extends BaseEntity {

	private static final long serialVersionUID = 3142969324276815044L;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "image_path_mobile")
	private String imagePathMobile;
	
	@Column(name = "is_active")
	private boolean active;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
