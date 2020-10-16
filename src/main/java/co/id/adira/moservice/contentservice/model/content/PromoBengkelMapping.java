package co.id.adira.moservice.contentservice.model.content;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "map_promo_bengkel")
public class PromoBengkelMapping implements Serializable {

	private static final long serialVersionUID = -201326472718839669L;

	@Id
	private Long id;

	@Column(name = "promo_id")
	private Long promoId;

	@Column(name = "bengkel_id")
	private Long bengkelId;
	
	@Column(name = "group_bengkel_id")
	private Long groupBengkelId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPromoId() {
		return promoId;
	}

	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}

	public Long getBengkelId() {
		return bengkelId;
	}

	public void setBengkelId(Long bengkelId) {
		this.bengkelId = bengkelId;
	}

	public Long getGroupBengkelId() {
		return groupBengkelId;
	}

	public void setGroupBengkelId(Long groupBengkelId) {
		this.groupBengkelId = groupBengkelId;
	}
	
}
