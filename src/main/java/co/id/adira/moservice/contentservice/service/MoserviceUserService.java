/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.user.create_user_car.CreateUserCarJson;
import co.id.adira.moservice.contentservice.json.user.create_user_car.CreateUserCarResponseJson;
import co.id.adira.moservice.contentservice.json.user.get_user_car.GetUserCarResponseJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterResponseJson;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.*;

/**
 * @author fatchurrachman
 *
 */
public interface MoserviceUserService {

    @POST("/api/users")
    Call<RegisterResponseJson> register(@Body RegisterJson registerJson);

    @GET("/api/users/{user_id}/cars")
    Call<GetUserCarResponseJson> getUserCars(
            @Header("Authorization") String authorization,
            @Path(value = "user_id") Long userId,
            @Query("plate_no") String plateNo,
            @Query("brand_id") Long brandId,
            @Query("model_id") Long modelId
    );

    @POST("/api/users/{user_id}/car")
    Call<CreateUserCarResponseJson> createUserCar(
            @Header("Authorization") String authorization,
            @Path(value = "user_id") Long userId,
            @Body CreateUserCarJson createUserCarJson
    );

}
