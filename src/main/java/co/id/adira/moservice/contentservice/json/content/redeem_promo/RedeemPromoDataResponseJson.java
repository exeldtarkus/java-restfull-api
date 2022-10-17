package co.id.adira.moservice.contentservice.json.content.redeem_promo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedeemPromoDataResponseJson {

    private Long id;
    private String qrcodePath;
    private String qrcodePath2;
    private String custAcction;
    private Long userId;
    private String createdAt;
    private String updatedAt;
    private String data;
    private Long bookingId;
    private Long promoId;
    private Long bengkelId;
    private String paymentId;
    private String base64QRCode;
    private RedeemPromoPromoJson promo;

}
