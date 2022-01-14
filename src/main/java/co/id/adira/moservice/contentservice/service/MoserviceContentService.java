/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoJson;
import co.id.adira.moservice.contentservice.json.user.create_user_car.CreateUserCarJson;
import co.id.adira.moservice.contentservice.json.user.create_user_car.CreateUserCarResponseJson;
import co.id.adira.moservice.contentservice.json.user.get_user_car.GetUserCarResponseJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterResponseJson;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @author fatchurrachman
 *
 */
public interface MoserviceContentService {

    @POST("/api/vouchers/redeem")
    Call<Object> redeemPromo(
            @Header("Authorization") String authorization,
            @Body RedeemPromoJson redeemPromoJson
    );

}
