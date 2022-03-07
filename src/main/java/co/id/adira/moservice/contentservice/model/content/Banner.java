package co.id.adira.moservice.contentservice.model.content;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="mst_banner")
public class Banner implements Serializable {
    private static final long serialVersionUID = 3142969324276815045L;

    @Id
	private Long id;

    @Column(name = "name")
	private String name;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "desc")
	private String desc;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "image_path_mobile")
	private String imagePathMobile;

	@Column(name = "cloudinary_path")
	private String cloudinaryPath;
	
	@Column(name = "is_active")
	private boolean active;

    @Column(name = "is_deleted")
	private boolean deleted;
	
	@Column(name = "url")
	private String url;

	@Column(name = "position")
	private Long position;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

    public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getCloudinaryPath() {
		return cloudinaryPath;
	}

	public void setCloudinaryPath(String cloudinaryPath) {
		this.cloudinaryPath = cloudinaryPath;
	}
}
