/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoJson;
import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoResponseJson;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @author fatchurrachman
 *
 */
public interface MoserviceContentService {

    @POST("/api/vouchers/redeem")
    Call<RedeemPromoResponseJson> redeemPromo(
            @Header("Authorization") String authorization,
            @Body RedeemPromoJson redeemPromoJson
    );

}
