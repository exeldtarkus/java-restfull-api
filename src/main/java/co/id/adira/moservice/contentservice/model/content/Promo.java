package co.id.adira.moservice.contentservice.model.content;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "m_promo")
public class Promo {
	
	@Id
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "title")
	private String title;

	@Column(name = "desc", columnDefinition = "TEXT")
	private String description;

	@Column(name = "tnc", columnDefinition = "TEXT")
	private String tnc;

	@Column(name = "url_path")
	private String urlPath;

	@Column(name = "image_path")
	private String imagePath;

	@Column(name = "image_path_mobile")
	private String imagePathMobile;

	@Column(name = "available_until")
	private Date availableUntil;

	@Column(name = "available_from")
	private Date availableFrom;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "created")
	private Date created;

	@Column(name = "updated")
	private Date updated;

	@Column(name = "counter_redeemed")
	private Integer counterRedeemed;

	@Column(name = "counter_used")
	private Integer counterUsed;

	@Column(name = "total")
	private Integer total;

	@Column(name = "limit")
	private Integer limit;

	@Column(name = "is_national")
	private Boolean isNational;

	@Column(name = "zone_id")
	private Long zoneId;

	@Column(name = "target_id")
	private Long targetId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = PromoBengkelMapping.class)
	@JoinColumn(name="promo_id", insertable = false, updatable = false)
	//@JsonManagedReference
	private List<PromoBengkelMapping> bengkels;
	
	public List<Long> getBengkels() {
    List<Long> bengkelIds;
    try{
      bengkelIds = bengkels.stream().map(PromoBengkelMapping::getBengkelId).collect(Collectors.toList());
    }catch(Exception e){
      bengkelIds = null;
    }
    return bengkelIds;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getImagePathMobile() {
		return imagePathMobile;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public Date getAvailableFrom() {
		return availableFrom;
	}

	public Date getAvailableUntil() {
		return availableUntil;
	}

	public String getTnc() {
		return tnc;
	}

	public String getDescription() {
		return description;
	}

	public String getUrlPath() {
		return urlPath;
	}
}