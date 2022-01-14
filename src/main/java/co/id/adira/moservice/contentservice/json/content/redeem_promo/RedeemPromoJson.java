package co.id.adira.moservice.contentservice.json.content.redeem_promo;

import lombok.Data;

@Data
public class RedeemPromoJson {

    private Long bengkelId;
    private String bengkel_name;
    private Long userId;
    private Long carId;
    private String utm;
    private RedeemPromoPromoJson promo;

}
