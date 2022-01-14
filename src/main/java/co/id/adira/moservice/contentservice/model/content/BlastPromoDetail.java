package co.id.adira.moservice.contentservice.model.content;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "tr_blast_promo_detail")
public class BlastPromoDetail implements Serializable {

	private static final long serialVersionUID = 1529823839874339202L;

	@Id
	private Long id;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "phonenumber")
	private String phoneNumber;

	@Column(name = "mobil_brand")
	private String mobilBrand;

	@Column(name = "mobil_model")
	private String mobilModel;

	@Column(name = "mobil_plate_no")
	private String mobilPlateNo;

	@Column(name = "tr_blast_promo_id")
	private Long trBlastPromoId;

	@Column(name = "tr_promo_user_id")
	private Long trPromoUserId;
	
}
