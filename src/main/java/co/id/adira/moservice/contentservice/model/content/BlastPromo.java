package co.id.adira.moservice.contentservice.model.content;

import co.id.adira.moservice.contentservice.dto.bengkel.ProvinceCityDTO;
import co.id.adira.moservice.contentservice.model.bengkel.Bengkel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tr_blast_promo")
public class BlastPromo implements Serializable {

	private static final long serialVersionUID = 1529823839874339202L;

	@Id
	private Long id;

	@Column(name = "promo_id")
	private Long promoId;

	@Column(name = "bengkel_id")
	private Long bengkelId;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="tr_blast_promo_id")
	@OrderColumn(name="id")
	private List<BlastPromoDetail> details = new ArrayList<>();

}
