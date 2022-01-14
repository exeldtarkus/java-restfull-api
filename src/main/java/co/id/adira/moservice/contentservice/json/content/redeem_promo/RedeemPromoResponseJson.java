package co.id.adira.moservice.contentservice.json.content.redeem_promo;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class RedeemPromoResponseJson {

    private String message;
    private String timestamp;
    private DateTime status;
    private Boolean isSuccess;
    private RedeemPromoDataResponseJson data;

}
