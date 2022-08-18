package co.id.adira.moservice.contentservice.model.content;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import co.id.adira.moservice.contentservice.dto.bengkel.ProvinceCityDTO;
import co.id.adira.moservice.contentservice.model.bengkel.Bengkel;

@Entity
@Table(name = "mst_promo")
public class Promo implements Serializable {

	private static final long serialVersionUID = 5529823839874339202L;

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

	@Column(name = "image_path_2")
	private String imagePath2;

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

	@Column(name= "tag_promo")
	private String tagPromo;

	@Column(name = "zone_id")
	private Long zoneId;

	@Column(name = "target_id")
	private Long targetId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = PromoBengkelMapping.class)
	@JoinColumn(name = "promo_id", insertable = false, updatable = false)
	// @JsonManagedReference
	private List<PromoBengkelMapping> bengkels;
	
	@Transient
	List<Bengkel> lstBengkel;

	@Transient
	List<ProvinceCityDTO> provinceCities;

	@Transient
	private Double km;
	
	@Column(name = "original_price")
	private BigDecimal originalPrice;
	
	@Transient
	private BigDecimal totalPrice;
	
	@Column(name = "disc_percentage")
	private int discPercentage;
	
	@Column(name = "disc_amount")
	private BigDecimal discAmount;
	
	@Column(name = "service_fee")
	private BigDecimal serviceFee;

	@Column(name = "price")
	private BigDecimal price;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTnc() {
		return tnc;
	}

	public void setTnc(String tnc) {
		this.tnc = tnc;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
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

	public Date getAvailableUntil() {
		return availableUntil;
	}

	public void setAvailableUntil(Date availableUntil) {
		this.availableUntil = availableUntil;
	}

	public Date getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(Date availableFrom) {
		this.availableFrom = availableFrom;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Integer getCounterRedeemed() {
		return counterRedeemed;
	}

	public void setCounterRedeemed(Integer counterRedeemed) {
		this.counterRedeemed = counterRedeemed;
	}

	public Integer getCounterUsed() {
		return counterUsed;
	}

	public void setCounterUsed(Integer counterUsed) {
		this.counterUsed = counterUsed;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Boolean getIsNational() {
		return isNational;
	}

	public void setIsNational(Boolean isNational) {
		this.isNational = isNational;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public List<PromoBengkelMapping> getBengkels() {
		return bengkels;
	}

	public void setBengkels(List<PromoBengkelMapping> bengkels) {
		this.bengkels = bengkels;
	}

	public List<Bengkel> getLstBengkel() {
		return lstBengkel;
	}

	public void setLstBengkel(List<Bengkel> lstBengkel) {
		this.lstBengkel = lstBengkel;
	}

	public List<ProvinceCityDTO> getProvinceCities() {
		return provinceCities;
	}

	public void setProvinceCities(List<ProvinceCityDTO> provinceCities) {
		this.provinceCities = provinceCities;
	}

	public Double getKm() {
		return km;
	}

	public void setKm(Double km) {
		this.km = km;
	}
	public String getTagPromo() {
		return tagPromo;
	}

	public void setTagPromo(String tagPromo) {
		this.tagPromo = tagPromo;
	}
	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getDiscPercentage() {
		return discPercentage;
	}

	public void setDiscPercentage(int discPercentage) {
		this.discPercentage = discPercentage;
	}

	public BigDecimal getDiscAmount() {
		return discAmount;
	}

	public void setDiscAmount(BigDecimal discAmount) {
		this.discAmount = discAmount;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getImagePath2() {
		return imagePath2;
	}

	public void setImagePath2(String imagePath2) {
		this.imagePath2 = imagePath2;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
