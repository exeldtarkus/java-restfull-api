package co.id.adira.moservice.contentservice.model.content;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

	@Column(name = "user_unique")
	private Long userUnique;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="tr_blast_promo_id")
	@OrderColumn(name="id")
	private List<BlastPromoDetail> details = new ArrayList<>();

}
