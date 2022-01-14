package co.id.adira.moservice.contentservice.json.content.redeem_promo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedeemPromoDataResponseJson {

    private Long id;
    private String qrcodePath;
    private Long promoId;
    private Long bengkelId;
    private RedeemPromoPromoJson promo;

}
